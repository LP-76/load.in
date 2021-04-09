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
                    //if they are the same space do nothing
                }
            }
        }

        if (spaces.size() > 1 && neighboringSpacesFoundAndDeFragmented)
            return Defragment(spaces);

        else
            return spaces;
    }
}
