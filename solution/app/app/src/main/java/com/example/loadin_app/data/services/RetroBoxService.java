package com.example.loadin_app.data;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import odu.edu.loadin.common.BoxSize;
import retrofit2.Call;
import retrofit2.http.*;



public interface RetroBoxService {

    @GET("/boxservice/boxSizes")
    @Headers( "accept: application/json" )
    CompletableFuture<BoxSizeResult<List<BoxSize>>> getBoxSizes();

    @POST("/boxservice/boxSizes")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<BoxSizeResult<BoxSize>> addBoxSize(@Body BoxSizeResult<BoxSize> boxSize);

    public class BoxSizeResult<T>{
        public T Data;
    }
}

