package com.example.loadin_app;
import androidx.annotation.NonNull;

import com.example.loadin_app.extensions.ArrayListExtendedIterator;
import com.example.loadin_app.extensions.ExtendedIterable;
import com.example.loadin_app.extensions.IExtendedIterator;
import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.Truck;
import java.util.*;
import java.util.stream.StreamSupport;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.LoadPlanBox;

//container of boxes, their translations, and loading order
public class LoadPlan implements ExtendedIterable<Load>
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

    public LoadPlan(ArrayList<LoadPlanBox> input)
    {
        //movingTruck = new Truck();
        //movingTruck = null;
        //AddLoad();

        //persons.sort(Comparator.comparing(Person::getName).thenComparing(Person::getAge));

        input.sort(Comparator.comparing(LoadPlanBox::getLoadNumber).thenComparing(LoadPlanBox::getStepNumber));

        for(LoadPlanBox b : input)
        {
            if(movingTruck == null){
                movingTruck = new Truck(b.getTruck());  //we only need to set this once from the first box
            }

            if(b.getLoadNumber() >= loads.size())
                AddLoad();

            Inventory i = b.getBox();

            Box newBox = new Box(i.getId(), i.getBoxID(), i.getWidth(), i.getHeight(), i.getLength(), (float) i.getWeight(), i.getFragility(), i.getDescription(), i.getUserID(), i.getStatus(), i.getRoom(), i.getItemList());
            newBox.setDestination(new Vector(b.getxOffset(), b.getyOffset(), b.getzOffset()));
            loads.get(b.getLoadNumber()).AddBox((newBox));
        }
    }

    public LoadPlan(LoadPlan other){

        movingTruck = other.movingTruck;

        for(Load l : other.loads)
            loads.add(new Load(l));



    }

    public Load AddLoad()
    {
        Load l = new Load(new EmptySpace(movingTruck.getLengthInches(), movingTruck.getWidthInches(), movingTruck.getHeightInches(), new Vector(0,0,0)));
        loads.add(l);
        return l;

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
        return ( curLoadIndex )  < loads.size();
    }

    public Load GetNextLoad()
    {
        if(HasNextLoad())
        {
            Load toReturn = loads.get(curLoadIndex);

            curLoadIndex++;

            return toReturn;
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

    public int getLoadCount(){
        return iterator().size();
    }

    public int getItemCount(){
        int count = 0;
        for(IExtendedIterator<Load> l = iterator(); l.hasNext();){
            count += l.next().iterator().size();
        }
        return count;
    }

    public float getSumOfEmptyVolumeInAllLoads()
    {
        float totalEmptyVolume = 0;

        for(Load load : loads)
        {
            totalEmptyVolume += load.GetEmptyVolume();
        }

        return totalEmptyVolume;
    }


    @NonNull
    @Override
    public IExtendedIterator<Load> iterator() {
        return new ArrayListExtendedIterator<Load>(loads);
    }
}