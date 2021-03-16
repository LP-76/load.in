package com.example.loadin_app.data.services;




import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.User;
import odu.edu.loadin.common.UserLoginRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroUserService {

    @GET("/users/user/{id}")
    @Headers( "accept: application/json" )
    CompletableFuture<DataWrapper<User>> getUser(@Path("id") int id);

    @POST("/users/user")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    Call<DataWrapper<User>> login(@Body DataWrapper<UserLoginRequest> loginRequest);

    @POST("/users/user/add")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<DataWrapper<User>> addUser(@Body DataWrapper<User> user);

}