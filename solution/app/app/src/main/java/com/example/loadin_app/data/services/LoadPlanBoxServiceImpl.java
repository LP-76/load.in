package com.example.loadin_app.data.services;


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


public class LoadPlanBoxServiceImpl extends LoadInServiceWithAuthImpl
{
    RetroLoadPlanBoxService retroService;

    public LoadPlanBoxServiceImpl(String username, String password)
    {
        this(BaseServiceUrlProvider.Config.LOCAL, username, password);

    }
    public LoadPlanBoxServiceImpl(BaseServiceUrlProvider.Config config, String username, String password)
    {
        super(config, username, password);
        retroService = retrofitContext.create(RetroLoadPlanBoxService.class);
    }

    public ArrayList<LoadPlanBox> getLoadPlan(int id) throws ExecutionException, InterruptedException
    {
        CompletableFuture<LoadPlanBoxWrapper> promise = retroService.getLoadPlan(id);
        return promise.get().LPB;
    }

    public ArrayList<LoadPlanBox> addLoadPlan(int id, ArrayList<LoadPlanBox> loadPlan) throws ExecutionException, InterruptedException
    {
        LoadPlanBoxWrapper wrapper = new LoadPlanBoxWrapper();
        wrapper.LPB  = loadPlan;
        CompletableFuture<LoadPlanBoxWrapper> addPromise = retroService.addLoadPlan(id,wrapper);
        ArrayList<LoadPlanBox> result = addPromise.get().LPB;
        return result;
    }
}
