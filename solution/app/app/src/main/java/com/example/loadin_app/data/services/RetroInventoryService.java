package com.example.loadin_app.data.services;


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

    @POST("/inventoryservice/inventory/addBulk")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<DataWrapper<ArrayList<Inventory>>> addBulkInventory(@Body DataWrapper<ArrayList<Inventory>> items);

    @POST("/inventoryservice/inventory/edit")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<DataWrapper<Inventory>> editInventory(@Body DataWrapper<Inventory> inventory);

    @POST("/inventoryservice/inventory/delete/{ID}")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<DataWrapper<List<Inventory>>> deleteItem(@Path("ID") int ID);

    @POST("/inventoryservice/inventory/delete/{USER_ID}")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<DataWrapper<List<Inventory>>> deleteAllItem(@Path("ID") int ID, int USER_ID);
}

