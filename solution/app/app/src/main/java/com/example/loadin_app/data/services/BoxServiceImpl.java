package com.example.loadin_app.data.services;

import com.example.loadin_app.data.RetroBoxService;

import java.sql.Array;
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


public class BoxServiceImpl {
    RetroBoxService retroService;

    public BoxServiceImpl(){
        this("http://localhost:9000/");

    }
    public BoxServiceImpl(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retroService = retrofit.create(RetroBoxService.class);
    }

    public List<BoxSize> getBoxSizes() throws ExecutionException, InterruptedException {

            CompletableFuture<RetroBoxService.BoxSizeResult<List<BoxSize>>> sizesPromise = retroService.getBoxSizes();
            RetroBoxService.BoxSizeResult<List<BoxSize>> sizes = sizesPromise.get();
            return sizes.Data;

    }

   public BoxSize addBoxSize(BoxSize boxSize) throws ExecutionException, InterruptedException {
       RetroBoxService.BoxSizeResult<BoxSize> wrapper = new  RetroBoxService.BoxSizeResult<BoxSize>();
       wrapper.Data  = boxSize;
       CompletableFuture<RetroBoxService.BoxSizeResult<BoxSize>> addPromise = retroService.addBoxSize(wrapper);
       BoxSize result = addPromise.get().Data;
       return result;
   }
}
