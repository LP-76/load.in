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
    private EmptySpace spaceDimensions;

    private float emptyArea;

    public Load(EmptySpace input_Space)
    {
        spaceDimensions = input_Space;
        emptySpaces.add(new EmptySpace(input_Space.GetLength(), input_Space.GetWidth(), input_Space.GetHeight(), new Vector(0,0,0)));

        emptyArea = input_Space.GetLength() * input_Space.GetHeight() * input_Space.GetWidth();
    }

    public void AddBox(Box input_box)
    {
        boxes.add(input_box);
        emptyArea -= input_box.getVolume();
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
    }

    public float GetEmptyArea()
    {
        return emptyArea;
    }

    public void AddSpace(EmptySpace input_Space)
    {
        if(input_Space.GetHeight() > 0 && input_Space.GetWidth() > 0 && input_Space.GetLength() > 0)
        {
            emptySpaces.add(input_Space);
            emptySpaces = new EmptySpaceDefragmenter().Defragment(emptySpaces);
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

       if(o2.isInFrontOf(o1) || o2.isAbove(o1))
           return -1;
       else if(o1.isInFrontOf(o2) || o1.isAbove(o2))
           return 1;

       return 0;


    }
}

