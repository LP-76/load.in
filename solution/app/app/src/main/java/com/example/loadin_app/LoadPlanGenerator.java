package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;

import java.util.*;

public class LoadPlanGenerator
{
    private Truck movingTruck;

    private ArrayList<Box> moveInventory = new ArrayList<Box>();

    private List<EmptySpace> emptySpace = new ArrayList<EmptySpace>();

    public LoadPlanGenerator()
    {
        GetMoveInventory();
        GetTruckSize();
        GenerateLoadPlan();
    }

    private void GetMoveInventory()
    {
        //TODO: Get the full Move Inventory

//      for(int x = 0; x < 20; x++)
//            moveInventory.add(new Box(15f, 25f, 30f));

      //TODO: more dummy data
    }

    private void GetTruckSize()
    {
        //TODO: Get the size of the truck
        //movingTruck = new Truck();
    }

    private void GenerateLoadPlan()
    {
        //To start, the truck is entirely empty, so we'll construct an empty space representing the total dimensions of the truck.
        emptySpace.add(new EmptySpace(movingTruck.getLengthInches(), movingTruck.getWidthInches(), movingTruck.getHeightInches()));


        //TODO: some crazy math



        //LoadPlan plan = new LoadPlan(movingTruck, moveInventory);
    }
}
