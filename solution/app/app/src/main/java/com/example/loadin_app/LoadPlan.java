package com.example.loadin_app;
import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.Truck;
import java.util.*;

//container of boxes, their translations, and loading order
public class LoadPlan
{
    //might need this? private int numberOfLoads = 0;
    private Truck movingTruck;

    private ArrayList<Load> loads = new ArrayList<Load>();
    private int curLoadIndex = 0;

    public LoadPlan(Truck input_Truck)
    {
        movingTruck = input_Truck;
        AddLoad(); //every load plan must have at least one load.
    }

    public void AddLoad()
    {
        loads.add(new Load(new EmptySpace(movingTruck.getLengthInches(), movingTruck.getWidthInches(), movingTruck.getHeightInches(), new Vector(0,0,0))));
    }

    public ArrayList<Load> GetLoads()
    {
        return loads;
    }

    public void AddBox(Box input_Box)
    {
        loads.get(curLoadIndex).AddBox(input_Box);
    }

    public Load GetCurrentLoad()
    {
        if(curLoadIndex < loads.size() && curLoadIndex >= 0)
            return loads.get(curLoadIndex);
        else
            return null;
    }

    public boolean HasNextLoad()
    {
        return ( curLoadIndex + 1 )  < loads.size();
    }

    public Load GetNextLoad()
    {
        if(HasNextLoad())
        {
            curLoadIndex++;
            return loads.get(curLoadIndex);
        }
        else
            return null;
    }

    public boolean HasPreviousLoad()
    {
        return ( curLoadIndex - 1 )  >= 0;
    }

    public Load GetPreviousLoad()
    {
        if(HasPreviousLoad())
        {
            curLoadIndex--;
            return loads.get(curLoadIndex);
        }
        else
            return null;
    }

    public Truck GetTruck()
    {
        return movingTruck;
    }
}