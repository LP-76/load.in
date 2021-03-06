package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;

import java.util.*;

public class LoadPlanGenerator
{
    private Truck movingTruck;

    private ArrayList<Box> moveInventory = new ArrayList<Box>();

    LoadPlan plan;

    public LoadPlanGenerator()
    {
        GetMoveInventory();
        GetTruckSize();
        GenerateLoadPlan();

        plan = new LoadPlan(movingTruck); //make an empty load plan based on the dimensions of the truck
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
        for (Box currentBox : moveInventory)//for each box..
        {
            PlaceBox(FindPlaceForBox(currentBox), currentBox);
        }
    }

    private void PlaceBox(LoadStatistics input_Info, Box input_Box)
    {
        EmptySpace idealSpace = plan.GetLoads().get(input_Info.GetLoadIndex()).GetEmptySpaces().get(input_Info.GetEmptySpaceIndex());

        switch(input_Info.GetNumberOfMatchingDimensions())
        {

            case 3:
                //the box is the exact same size as the space
                //we need to completely delete the space and put the box in its place

                input_Box.setDestination(idealSpace.GetOffset());
                plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
                plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);
                break;
            case 2:
                //the box matches the space in 2 dimensions
                //divide the space in 2 in the dimension that doesn't max, replace
                //the filled space with a box
                // |---------------|
                // |      box      |
                // |_______________|
                // | updated space |
                // |_______________|

                break;
            case 1:
                //the box matches the width, height, or length of the space and nothing else.
                //the space will need to be divided in to 3 pieces
                // |-------|-----------|
                // |  box  | new space |
                // |_______|___________|
                // | new space         |
                // |___________________|

                break;
            case 0:
                //we will need to create 5 spaces
                // - one that will be replaced with the box
                // - one the width of the box that represents the difference in length
                // - one the length of the box that represents the difference in width
                // - one the height of the box that represents the difference in height
                // - one that fills in the rest of the space diagonal to the box, only sharing a single edge with the box
                // this is essentially a 3D version of the case 1 break-up
                break;
            default:
                //we should never hit this code.
                break;
        }
    }


    private LoadStatistics FindPlaceForBox(Box input_Box)
    {
        boolean placeForBoxExists = true;

        ArrayList<LoadStatistics> options = new ArrayList<LoadStatistics>();

        for (int loadIndex = 0; loadIndex < plan.GetLoads().size(); loadIndex++) //look in each truck...
        {
            LoadStatistics currentOption = FindCandidateSpotForBoxInLoad(input_Box, plan.GetLoads().get(loadIndex), loadIndex);

            if(currentOption != null)
            {
                options.add(currentOption);
            }
        }

        if(options.isEmpty())
        {
            plan.AddLoad();
            return FindPlaceForBox(input_Box); // this better only recurse once.......
        }
        else
        {
            return DetermineBestBoxPlacementOption(options);
        }
    }

     private LoadStatistics FindCandidateSpotForBoxInLoad(Box input_Box, Load input_Load, int input_LoadIndex)
     {

         int numberOfMatchingDimensionsInBestFit = -1;
         int bestFitSpaceIndex = -1;

         if (input_Load.GetEmptySpaces().size() > 0)
         {
             boolean sizeAtLeastAsLargeAsBoxFound = false;

             for(int spaceIndex = 0 ; spaceIndex < input_Load.GetEmptySpaces().size() ; spaceIndex++)
             {
                 EmptySpace currentSpace = input_Load.GetEmptySpaces().get(spaceIndex);
                 if( BoxFitsWithinSpace(currentSpace, input_Box) )
                 {
                     sizeAtLeastAsLargeAsBoxFound = true;

                     int numberOfMatchingDimensions = CheckIfEmptySpaceMatchesBoxDimensions(input_Box, currentSpace);
                     if(numberOfMatchingDimensions > numberOfMatchingDimensionsInBestFit)
                     {
                         numberOfMatchingDimensionsInBestFit = numberOfMatchingDimensions;
                         bestFitSpaceIndex = spaceIndex;
                     }
                 }
             }

             if(sizeAtLeastAsLargeAsBoxFound)
             {
                 return new LoadStatistics(input_LoadIndex, bestFitSpaceIndex, numberOfMatchingDimensionsInBestFit, input_Load.GetEmptyArea() );
             }
         }

         return null;
     }

    private LoadStatistics DetermineBestBoxPlacementOption(ArrayList<LoadStatistics> input_Options)
    {
        LoadStatistics answer = input_Options.get(0);

        for(LoadStatistics currentOption : input_Options)
        {
            if(currentOption.GetNumberOfMatchingDimensions() >= answer.GetNumberOfMatchingDimensions() && currentOption.GetEmptyArea() <= answer.GetEmptyArea())
            {
                answer = currentOption;
            }
        }

        return answer;
    }

    private int CheckIfEmptySpaceMatchesBoxDimensions(Box input_Box, EmptySpace input_Space)
    {
        int numberOfMatchingDimensions = 0;

        if(input_Box.getWidth() == input_Space.GetWidth())
            numberOfMatchingDimensions++;

        if(input_Box.getLength() == input_Space.GetHeight())
            numberOfMatchingDimensions++;

        if(input_Box.getLength() == input_Space.GetLength())
            numberOfMatchingDimensions++;

        return numberOfMatchingDimensions;
    }

    private boolean BoxFitsWithinSpace(EmptySpace input_Space, Box input_Box)
    {
        return (input_Space.GetWidth() >= input_Box.getWidth() ) &&(input_Space.GetLength() >= input_Box.getLength() ) && (input_Space.GetHeight() >= input_Box.getHeight() );
    }


/*
            for (EmptySpace currentSpace : plan.GetLoads().get(loadIndex).GetEmptySpaces()) //through all the empty spaces in that truck...
            {
                //TODO: Actual load plan figuring out

                int dimensionsMatched =  CheckIfEmptySpaceMatchesBoxDimensions(currentBox,currentSpace);


            }
        }
    }
    */
}
