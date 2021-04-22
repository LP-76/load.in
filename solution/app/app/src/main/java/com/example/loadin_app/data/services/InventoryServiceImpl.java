package com.example.loadin_app.data.services;


import com.example.loadin_app.ui.opengl.Box;

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


public class InventoryServiceImpl extends LoadInServiceWithAuthImpl{
    RetroInventoryService retroService;

    public InventoryServiceImpl(String username, String password){
        this(BaseServiceUrlProvider.Config.LOCAL, username, password);

    }
    public InventoryServiceImpl(BaseServiceUrlProvider.Config config, String username, String password){
        super(config, username, password);
        retroService = retrofitContext.create(RetroInventoryService.class);
    }

    public List<Inventory> getInventory(int loginID) throws ExecutionException, InterruptedException
    {
        CompletableFuture<DataWrapper<List<Inventory>>> newInv = retroService.getInventory(loginID);
        return newInv.get().Data;
    }

    public ArrayList<Box> getInventoryAsBoxes(int loginID) throws ExecutionException, InterruptedException
    {
        List<Inventory> inventory = getInventory(loginID);

        ArrayList<Box> inventoryAsBoxes = new ArrayList<Box>();

        for(Inventory item : inventory)
        {
            inventoryAsBoxes.add(new Box(item.getId(), item.getBoxID(), item.getWidth(), item.getHeight(), item.getLength(), (float) item.getWeight(), item.getFragility(),
                    item.getDescription(), item.getUserID(), item.getStatus(), item.getItemList(), item.getRoom()));
        }

        return inventoryAsBoxes;
    }
    public Inventory addInventory(Inventory inventory) throws ExecutionException, InterruptedException {
        DataWrapper<Inventory> wrapper = new  DataWrapper<Inventory>();
        wrapper.Data  = inventory;
        CompletableFuture<DataWrapper<Inventory>> addPromise = retroService.addInventory(wrapper);
        Inventory result = addPromise.get().Data;
        return result;
    }

    public ArrayList<Inventory> addBulkInventory(ArrayList<Inventory> items) throws ExecutionException, InterruptedException {
        DataWrapper<ArrayList<Inventory>> wrapper = new  DataWrapper<ArrayList<Inventory>>();
        wrapper.Data  = items;
        CompletableFuture<DataWrapper<ArrayList<Inventory>>> addPromise = retroService.addBulkInventory(wrapper);
        ArrayList<Inventory> result = addPromise.get().Data;
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


    public Inventory deleteAllItem(int USER_ID) throws ExecutionException, InterruptedException{
        //CompletableFuture<DataWrapper<>>
        retroService.deleteAllItem(USER_ID);
        return null;
    }

    public Inventory insertRandomItem(int USER_ID, int numOfBoxes) throws ExecutionException, InterruptedException{
        //CompletableFuture<DataWrapper<>>
        retroService.insertRandomItem(USER_ID, numOfBoxes);
        return null;
    }

    public void setUserInventoryStatus(int USER_ID) throws ExecutionException, InterruptedException{
        //CompletableFuture<DataWrapper<>>
        retroService.setUserInventoryStatus(USER_ID);
    }
}
