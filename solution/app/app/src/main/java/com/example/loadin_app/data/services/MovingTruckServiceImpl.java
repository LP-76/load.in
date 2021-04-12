package com.example.loadin_app.data.services;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.MovingTruck;


public class MovingTruckServiceImpl extends LoadInServiceWithAuthImpl {
    RetroMovingTruckService retroService;

    public MovingTruckServiceImpl(String username, String password) {
        this(BaseServiceUrlProvider.Config.LOCAL, username, password);

    }

    public MovingTruckServiceImpl(BaseServiceUrlProvider.Config config, String username, String password) {
        super(config, username, password);
        retroService = retrofitContext.create(RetroMovingTruckService.class);
    }

    public List<MovingTruck> getTrucks() throws ExecutionException, InterruptedException {

        CompletableFuture<DataWrapper<List<MovingTruck>>> sizesPromise = retroService.getTruckSizes();
        DataWrapper<List<MovingTruck>> trucks = sizesPromise.get();
        return trucks.Data;

    }


}
