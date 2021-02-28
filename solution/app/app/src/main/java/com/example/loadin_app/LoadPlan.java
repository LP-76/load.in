package com.example.loadin_app;
import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;

import java.util.*;

//container of boxes, their translations, and loading order
public class LoadPlan
{
    //might need this? private int numberOfLoads = 0;
    private Truck movingTruck;

    private ArrayList<Load> loads = new ArrayList<Load>();
    private int curLoad = 0;

    public LoadPlan(Truck input_Truck)
    {
        movingTruck = input_Truck;
        AddLoad(); //every load plan must have at least one load.
    }

    public void AddLoad()
    {
        loads.add(new Load(new EmptySpace(movingTruck.getLengthInches(), movingTruck.getWidthInches(), movingTruck.getHeightInches())));
    }

    public ArrayList<Load> GetLoads()
    {
        return loads;
    }

    public void AddBox(Box input_Box)
    {
        loads.get(curLoad).AddBox(input_Box);
    }

    public Box GetNextBox()
    {
        Box nextBox = loads.get(curLoad).GetNextBox();

        if(nextBox != null)
        {
            return nextBox;
        }
        else
        {
            return null;
        }
    }
}