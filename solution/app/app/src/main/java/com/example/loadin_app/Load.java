package com.example.loadin_app;

import androidx.annotation.NonNull;

import com.example.loadin_app.extensions.ArrayListExtendedIterator;
import com.example.loadin_app.extensions.ExtendedIterable;
import com.example.loadin_app.extensions.IExtendedIterator;
import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Load implements ExtendedIterable<Box> , Comparator<Box>
{
    private int curBoxIndex = 0;

    private ArrayList<Box> boxes = new ArrayList<Box>();
    private ArrayList<EmptySpace> emptySpaces = new ArrayList<EmptySpace>();


    private float emptyVolume;

    public Load(EmptySpace input_Space)
    {
        AddSpace(new EmptySpace(input_Space.GetLength(), input_Space.GetWidth(), input_Space.GetHeight(), new Vector(0,0,0)));
    }

    public Load(Load l){

        for(Box b: l.boxes){
            boxes.add(new Box(b));
        }

        for(EmptySpace es : l.emptySpaces)
            emptySpaces.add(new EmptySpace(es));


    }


    public void AddBox(Box input_box)
    {
        boxes.add(input_box);
        //emptyArea -= input_box.getVolume();
    }

    public Box GetCurrentBox()
    {
        if(curBoxIndex < boxes.size() && curBoxIndex >= 0)
            return boxes.get(curBoxIndex);
        else
            return null;
    }

    public boolean HasNextBox()
    {
        return (curBoxIndex ) < boxes.size();
    }

    public Box GetNextBox()
    {
        if(HasNextBox())
        {
            Box answer = boxes.get(curBoxIndex);
            curBoxIndex++;
            return answer;
        }
        else
            return null;
    }

    public boolean HasPreviousBox()
    {
        return (curBoxIndex - 1) >= 0;
    }

    public Box GetPreviousBox()
    {
        if(HasPreviousBox())
        {
            curBoxIndex--;
            return boxes.get(curBoxIndex);
        }
        else
            return null;
    }

    public ArrayList<EmptySpace> GetEmptySpaces()
    {
        return emptySpaces;
    }

    public void RemoveSpace(EmptySpace input_Space)
    {
        emptySpaces.remove(input_Space);
        emptyVolume -= input_Space.GetVolume();
    }

    public float GetEmptyVolume()
    {
        return emptyVolume;
    }

    public void AddSpace(EmptySpace input_Space)
    {
            emptySpaces.add(input_Space);
            emptyVolume += input_Space.GetVolume();
            emptySpaces = new EmptySpaceDefragmenter().Defragment(emptySpaces);
    }

    public void AddSpaces(ArrayList<EmptySpace> spaces)
    {
        for(EmptySpace space : spaces)
        {
            AddSpace(space);
        }
    }

    @NonNull
    @Override
    public IExtendedIterator<Box> iterator() {
        ArrayList<Box> copy = new ArrayList<Box>(boxes);
        copy.sort(this);


        return new ArrayListExtendedIterator<Box>(copy);
    }


    @Override
    public int compare(Box o1, Box o2)
    {
        //-1 means o1 comes before o2, which means o1 should go on first
        //0 means we don't care
        //1 means o1 comes after o2

        //a box goes on first if it is behind another box
        //below another box

       if(o1.isInSameRowAs(o2)){
           if(o1.isAbove(o2))
               return 1;
           else if(o2.isAbove(o1))
               return -1;

           if(o1.getDestination().getX() > o2.getDestination().getX())  //o1 is to the left of o2
               return -1;
           else
               return 1;  //the assumption is if it's in the same row and it's not to the left, it would have to be to the right

       }
       else if(o1.isInFrontOf(o2))
           return 1;
       else if(o2.isInFrontOf(o1))
           return -1;

       return 0;

    }
}

