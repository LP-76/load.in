package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;

import java.util.ArrayList;
import java.util.List;

public class Load
{
    private int nextBoxIndex = 0;

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

    public Box GetNextBox()
    {
        if(nextBoxIndex < boxes.size())
        {
            Box nextBox = boxes.get(nextBoxIndex);
            nextBoxIndex++;
            return nextBox;
        }
        else
        {
            return null;
        }
    }

    public boolean HasNextBox()
    {
        return (nextBoxIndex + 1) < boxes.size();
    }

    public ArrayList<EmptySpace> GetEmptySpaces()
    {
        return emptySpaces;
    }
}
