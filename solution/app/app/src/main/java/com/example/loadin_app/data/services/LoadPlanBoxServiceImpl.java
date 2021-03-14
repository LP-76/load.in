package com.example.loadin_app.data.services;

import com.example.loadin_app.data.RetroInventoryService;
import com.example.loadin_app.ui.opengl.Box;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.LoadPlanBox;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public class LoadPlanBoxServiceImpl
{
    RetroLoadPlanBoxService retroService;

    public LoadPlanBoxServiceImpl()
    {
        this("http://localhost:9000/");

    }
    public LoadPlanBoxServiceImpl(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retroService = retrofit.create(RetroLoadPlanBoxService.class);
    }

    public List<LoadPlanBox> getLoadPlan(int id) throws ExecutionException, InterruptedException
    {
        CompletableFuture<DataWrapper<List<LoadPlanBox>>> promise = retroService.getLoadPlan(id);
        return promise.get().Data;
    }

    public List<LoadPlanBox> addLoadPlan(int id, List<LoadPlanBox> loadPlan) throws ExecutionException, InterruptedException
    {
        DataWrapper<List<LoadPlanBox>> wrapper = new  DataWrapper<List<LoadPlanBox>>();
        wrapper.Data  = loadPlan;
        CompletableFuture<DataWrapper<List<LoadPlanBox>>> addPromise = retroService.addLoadPlan(id,wrapper);
        List<LoadPlanBox> result = addPromise.get().Data;
        return result;
    }
}
