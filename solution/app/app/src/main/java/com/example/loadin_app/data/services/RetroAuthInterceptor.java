package com.example.loadin_app.data.services;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetroAuthInterceptor implements Interceptor {

    String authToken;

    public RetroAuthInterceptor(String username, String password){
        authToken = Credentials.basic(username, password);

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder b = original.newBuilder()
                .header("Authorization", authToken);

        Request n = b.build();
        return chain.proceed(n);
    }
}
