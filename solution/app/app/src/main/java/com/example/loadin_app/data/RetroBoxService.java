package com.example.loadin_app.data;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import odu.edu.loadin.common.BoxSize;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;



public interface RetroBoxService {

    @GET("/boxservice/boxSizes")
    @Headers( "accept: application/json" )
    CompletableFuture<BoxSizeCollection> getBoxSizes();


    public class BoxSizeCollection{
        public List<BoxSize> BoxSize;

    }
}

