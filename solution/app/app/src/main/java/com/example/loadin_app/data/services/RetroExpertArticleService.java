package com.example.loadin_app.data.services;


import com.example.loadin_app.data.services.DataWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.ExpertArticle;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroExpertArticleService {

    @GET("/expertservice/expertArticles/{keyword}")
    @Headers( "accept: application/json" )
    CompletableFuture<DataWrapper<ArrayList<ExpertArticle>>> getExpertArticles(@Path("keyword") String keyword);


}