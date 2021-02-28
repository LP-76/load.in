package com.example.loadin_app.data.services;


import com.example.loadin_app.data.RetroBoxService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.ExpertArticle;
//import odu.edu.loadin.common.ExpertArticleSearch;
import odu.edu.loadin.common.User;
import odu.edu.loadin.common.UserLoginRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetroExpertArticleService {

    @GET("/article/{keyword}")
    @Headers( "accept: application/json" )
    CompletableFuture<DataWrapper<ExpertArticle>> getExpertArticle(String keyword);

}