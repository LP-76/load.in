package com.example.loadin_app.data.services;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.MovingTruck;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface RetroMovingTruckService {

    @GET("/movingtruckservice/trucks")
    @Headers( "accept: application/json" )
    CompletableFuture<DataWrapper<List<MovingTruck>>> getTruckSizes();




}

