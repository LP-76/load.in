package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;

import java.util.ArrayList;
import java.util.List;

public class Load
{
    private int curBoxIndex = 0;

    private ArrayList<Box> boxes = new ArrayList<Box>();
    private ArrayList<EmptySpace> emptySpaces = new ArrayList<EmptySpace>();
    private EmptySpace spaceDimensions;

    public Load(EmptySpace input_Space)
    {
        spaceDimensions = input_Space;
        emptySpaces.add(new EmptySpace(input_Space.GetLength(), input_Space.GetWidth(), input_Space.GetHeight()));
    }

    public void AddBox(Box input_box)
    {
        boxes.add(input_box);
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
        return (curBoxIndex + 1) < boxes.size();
    }

    public Box GetNextBox()
    {
        if(HasNextBox())
        {
            curBoxIndex++;
            return boxes.get(curBoxIndex);
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
}