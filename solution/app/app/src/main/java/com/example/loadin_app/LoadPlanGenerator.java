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
        LoadPlan plan = new LoadPlan(movingTruck); //make an empty load plan based on the dimensions of the truck

        for (Box box : moveInventory)//for each box..
        {
            for(Load currentLoad : plan.GetLoads()) //look in each truck...
            {
                for (EmptySpace currentSpace : currentLoad.GetEmptySpaces()) //through all the empty spaces in that truck...
                {
                    //TODO: Actual load plan figuring out

                }
            }
        }
    }
}
