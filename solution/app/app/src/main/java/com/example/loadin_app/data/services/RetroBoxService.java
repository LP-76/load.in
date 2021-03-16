package com.example.loadin_app.data.services;


import com.example.loadin_app.data.services.DataWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import odu.edu.loadin.common.BoxSize;
import retrofit2.Call;
import retrofit2.http.*;



public interface RetroBoxService {

    @GET("/boxservice/boxSizes")
    @Headers( "accept: application/json" )
    CompletableFuture<DataWrapper<List<BoxSize>>> getBoxSizes();

    @POST("/boxservice/boxSizes")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<DataWrapper<BoxSize>> addBoxSize(@Body DataWrapper<BoxSize> boxSize);


}

