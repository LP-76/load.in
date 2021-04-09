package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Vector;

import java.util.ArrayList;

public class EmptySpaceDefragmenter
{
    public static ArrayList<EmptySpace> Defragment(ArrayList<EmptySpace> spaces)
    {
        boolean neighboringSpacesFoundAndDeFragmented = false;

        outerloop:
        for(EmptySpace space : spaces)
        {
            for(EmptySpace other : spaces)
            {
                if(!space.equals(other))
                {
                    if(space.isNeighborInAnyWay(other))
                    {
                        neighboringSpacesFoundAndDeFragmented = true;
                        space.merge(other);
                        spaces.remove(other);
                        break outerloop;
                    }
                    else
                    {
                        //these spaces are not neighbors in any way, do nothing
                    }
                }
                else
                {
                    //if they are the same space, do nothing
                }
            }
        }

        if (spaces.size() > 1 && neighboringSpacesFoundAndDeFragmented)
            return Defragment(spaces);

        else
            return spaces;
    }
    /*
    public static EmptySpace Merge(EmptySpace space, EmptySpace other)
    {
        EmptySpace mergedSpace = null;
        if(space.isNeighborInXandSameHeightAndLength(other))
        {
            mergedSpace = new EmptySpace(space.GetLength(), space.GetWidth() + other.GetWidth(),space.GetHeight(),
                    new Vector(Math.min(space.GetOffset().getX(),other.GetOffset().getX()),space.GetOffset().getY(),space.GetOffset().getZ() ));
        }
        else if(space.isNeighBorInYAboveAndSameWidthAndLength(other))
        {
            mergedSpace = new EmptySpace(space.GetLength(), space.GetWidth(),space.GetHeight() + other.GetHeight(),
                    new Vector(space.GetOffset().getX(),Math.min(space.GetOffset().getY(),other.GetOffset().getY()), space.GetOffset().getZ()));
        }
        else if(space.isNeighborInZandSameHeightAndWidth(other))
        {
            mergedSpace = new EmptySpace(space.GetLength() + other.GetLength(), space.GetWidth(), space.GetHeight(),
                    new Vector(space.GetOffset().getX(),space.GetOffset().getY(), Math.min(space.GetOffset().getZ(),other.GetOffset().getZ())));
        }

        return mergedSpace;
    }

     */
}
