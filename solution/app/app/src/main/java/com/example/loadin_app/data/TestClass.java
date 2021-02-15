package com.example.loadin_app.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxService;
import odu.edu.loadin.common.BoxSize;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestClass {

    public List<BoxSize> TestSpace() throws ExecutionException, InterruptedException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:9000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroBoxService service = retrofit.create(RetroBoxService.class);


        CompletableFuture<RetroBoxService.BoxSizeCollection> sizesPromise = service.getBoxSizes();
        RetroBoxService.BoxSizeCollection sizes = sizesPromise.get();


        return sizes.BoxSize;


    }

}
