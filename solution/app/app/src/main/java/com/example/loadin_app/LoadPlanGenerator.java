package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;

import java.util.*;

public class LoadPlanGenerator
{
    private Truck movingTruck;
    private ArrayList<Box> moveInventory = new ArrayList<Box>();

    public LoadPlanGenerator()
    {
        GetMoveInventory();
        GetTruckSize();
        GenerateLoadPlan();
    }

    private void GetMoveInventory()
    {
        //TODO: Get the full Move Inventory
    }

    private void GetTruckSize()
    {
        //TODO: Get the size of the truck
        movingTruck = new Truck();
    }

    private void GenerateLoadPlan()
    {
        LoadPlan plan = new LoadPlan(movingTruck, moveInventory);
        //TODO: some crazy math
    }
}
