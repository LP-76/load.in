package com.example.loadin_app.data.services;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import odu.edu.loadin.common.LoadPlanBox;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroLoadPlanBoxService
{

    @GET("/loadplan/{loginID}")
    @Headers( "accept: application/json" )
    CompletableFuture<LoadPlanBoxWrapper> getLoadPlan(@Path("loginID") int id);

    @POST("/loadplan/{loginID}")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<LoadPlanBoxWrapper> addLoadPlan(@Path("loginID") int id, @Body LoadPlanBoxWrapper loadPlan);
}

