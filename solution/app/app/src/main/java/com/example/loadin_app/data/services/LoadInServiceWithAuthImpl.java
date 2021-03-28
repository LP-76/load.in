package com.example.loadin_app.data.services;

import com.example.loadin_app.LoadInApplication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class LoadInServiceWithAuthImpl  extends  LoadInBaseServiceImplementation{
    private String username;
    private String password;

    public LoadInServiceWithAuthImpl(BaseServiceUrlProvider.Config config, String username, String password) {
        super(config);
        this.username = username;
        this.password = password;

        retrofitContext = createRetrofitBuilder().build();  //rebuild for authentication

    }

    @Override
    protected OkHttpClient.Builder createRetrofitClient() {
        OkHttpClient.Builder b = super.createRetrofitClient();
        b.interceptors().add(new RetroAuthInterceptor(username, password));
        return b;
    }
}
