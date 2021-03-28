package com.example.loadin_app.data.services;



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


public class BoxServiceImpl extends LoadInServiceWithAuthImpl {
    RetroBoxService retroService;

    public BoxServiceImpl(String username, String password){
        this(BaseServiceUrlProvider.Config.LOCAL, username, password);

    }
    public BoxServiceImpl(BaseServiceUrlProvider.Config config, String username, String password){
        super(config, username, password);
        retroService = retrofitContext.create(RetroBoxService.class);
    }

    public List<BoxSize> getBoxSizes() throws ExecutionException, InterruptedException {

            CompletableFuture<DataWrapper<List<BoxSize>>> sizesPromise = retroService.getBoxSizes();
        DataWrapper<List<BoxSize>> sizes = sizesPromise.get();
            return sizes.Data;

    }

   public BoxSize addBoxSize(BoxSize boxSize) throws ExecutionException, InterruptedException {
       DataWrapper<BoxSize> wrapper = new  DataWrapper<BoxSize>();
       wrapper.Data  = boxSize;
       CompletableFuture<DataWrapper<BoxSize>> addPromise = retroService.addBoxSize(wrapper);
       BoxSize result = addPromise.get().Data;
       return result;
   }
}
