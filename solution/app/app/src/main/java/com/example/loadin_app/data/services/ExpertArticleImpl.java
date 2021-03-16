package com.example.loadin_app.data.services;




import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.ExpertArticle;
import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ExpertArticleImpl {
    RetroExpertArticleService retroService;


    public ExpertArticleImpl() {
        this("http://localhost:9000/");

    }

    public ExpertArticleImpl(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retroService = retrofit.create(RetroExpertArticleService.class);
    }

    public ExpertArticle getExpertArticles(String keyword) throws ExecutionException, InterruptedException {
        CompletableFuture<DataWrapper<ExpertArticle>> promise = retroService.getExpertArticles(keyword);
        return promise.get().Data;
    }

}