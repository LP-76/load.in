package com.example.loadin_app.data.services;



import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.User;
import odu.edu.loadin.common.UserLoginRequest;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserServiceImpl extends LoadInBaseServiceImplementation {
    RetroUserService retroService;

    public UserServiceImpl(){
        this(BaseServiceUrlProvider.Config.LOCAL);

    }
    public UserServiceImpl(BaseServiceUrlProvider.Config config){
       super(config);

        retroService = retrofitContext.create(RetroUserService.class);
    }

    @Override
    protected OkHttpClient.Builder createRetrofitClient() {
        return super.createRetrofitClient().callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

    }


    public User getUser(int id, String username, String password) throws ExecutionException, InterruptedException {
        //this one needs the additional interceptor

        OkHttpClient.Builder client = createRetrofitClient();
        client.addInterceptor(new RetroAuthInterceptor(username, password));


        Retrofit b = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build()).build();

        CompletableFuture<DataWrapper<User>> promise = b.create(RetroUserService.class).getUser(id);
        return promise.get().Data;
    }

    public User addUser(User user) throws ExecutionException, InterruptedException {
        DataWrapper<User> wrapper = new  DataWrapper<User>();
        wrapper.Data  = user;
        CompletableFuture<DataWrapper<User>> addPromise = retroService.addUser(wrapper);
        User result = addPromise.get().Data;
        return result;
    }

    public LoginResult login(String email, String password) throws IOException {
        LoginResult result = new LoginResult();

        UserLoginRequest request = new UserLoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        DataWrapper<UserLoginRequest> wrapper = new DataWrapper<UserLoginRequest>();
        wrapper.Data = request;
        Call<DataWrapper<User>> call = retroService.login(wrapper);

        Response<DataWrapper<User>> response =  call.execute();

        if(!response.isSuccessful()){
            //TODO: how do we get the response back to a json object

            switch (response.code()){
                case 404:  //not found
                    result.status = UserLoginStatus.NotFound;
                    break;

                case 403: //invalid password ; forbidden
                    result.status = UserLoginStatus.InvalidPassword;
                    break;

            }
            return result;
        }

        result.status = UserLoginStatus.Ok;
        result.userProfile = response.body().Data;
        return result;

    }
    public enum UserLoginStatus{
        NotFound,
        InvalidPassword,
        Ok
    }

    public class LoginResult{
        public UserLoginStatus status;
        public User userProfile;

    }
}
