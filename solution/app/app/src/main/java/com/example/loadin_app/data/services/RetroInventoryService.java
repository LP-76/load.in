package com.example.loadin_app.data;


import com.example.loadin_app.data.services.DataWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.Inventory;
import retrofit2.Call;
import retrofit2.http.*;



public interface RetroInventoryService {

    @GET("/inventoryservice/inventory/{loginID}")
    @Headers( "accept: application/json" )
    CompletableFuture<DataWrapper<List<Inventory>>> getInventory(@Path("loginID") int loginID);

    @POST("/inventoryservice/inventory")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<DataWrapper<Inventory>> addInventory(@Body DataWrapper<Inventory> inventory);

}

