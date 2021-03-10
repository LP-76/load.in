package com.example.loadin_app.data.services;

import com.example.loadin_app.data.RetroBoxService;
import com.example.loadin_app.data.RetroInventoryService;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import odu.edu.loadin.common.BoxService;
import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.Inventory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InventoryServiceImpl {
    RetroInventoryService retroService;

    public InventoryServiceImpl(){
        this("http://localhost:9000/");

    }
    public InventoryServiceImpl(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retroService = retrofit.create(RetroInventoryService.class);
    }

    public List<Inventory> getInventory(int loginID) throws ExecutionException, InterruptedException {
        CompletableFuture<DataWrapper<List<Inventory>>> newInv = retroService.getInventory(loginID);
        return newInv.get().Data;

    }

    public Inventory addInventory(Inventory inventory) throws ExecutionException, InterruptedException {
        DataWrapper<Inventory> wrapper = new  DataWrapper<Inventory>();
        wrapper.Data  = inventory;
        CompletableFuture<DataWrapper<Inventory>> addPromise = retroService.addInventory(wrapper);
        Inventory result = addPromise.get().Data;
        return result;
    }
    public Inventory editInventory(Inventory inventory) throws ExecutionException, InterruptedException {
        DataWrapper<Inventory> wrapper = new  DataWrapper<Inventory>();
        wrapper.Data  = inventory;
        CompletableFuture<DataWrapper<Inventory>> addPromise = retroService.editInventory(wrapper);
        Inventory result = addPromise.get().Data;
        return result;
    }
    public Inventory deleteItem(int ID) throws ExecutionException, InterruptedException{
        //CompletableFuture<DataWrapper<>>
        retroService.deleteItem(ID);
        return null;
    }
}
