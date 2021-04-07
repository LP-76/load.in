package com.example.loadin_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.LoadPlanBox;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.World;
import com.example.loadin_app.ui.opengl.Truck;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class LoadPlanGenerator
{
    private Truck movingTruck;

    private ArrayList<Box> moveInventory = new ArrayList<Box>();

    private LoadPlan plan;

    private boolean useRandomBoxes = false;

    SharedPreferences sp;
    protected  InventoryServiceImpl inventoryService;
    protected LoadPlanBoxServiceImpl boxService;

    public LoadPlanGenerator(SharedPreferences input_sp, InventoryServiceImpl inventoryService, LoadPlanBoxServiceImpl boxService)
    {
        this.inventoryService   = inventoryService;
        this.boxService = boxService;
        sp = input_sp;
    }

    public LoadPlan StartLoadPlan()
    {
        GetTruckSize();
        plan = new LoadPlan(movingTruck); //make an empty load plan based on the dimensions of the truck

        if(useRandomBoxes){
            GenerateRandomBoxes();
            PersistBoxesToUserMoveInventory();
        }

        GetMoveInventory();

        SortMoveInventory();

        GenerateLoadPlan();

        sendLoadPlanToDatabase();

        return plan;
    }

    private void SortMoveInventory()
    {
        moveInventory.sort(new Comparator<Box>()
        {
            @Override
            public int compare(Box o1, Box o2)
            {
                return (int) (o2.getXZArea() - o1.getXZArea());
            }
        });
    }

    private void PersistBoxesToUserMoveInventory(){
        //we need to take the move inventory and send it to the server in bulk

        ArrayList<Inventory> items = new ArrayList<Inventory>();

        for(Box b : moveInventory){
            Inventory item = new Inventory();
            item.setId(0);
            item.setDescription(b.getDescription());
            item.setWidth(b.getWidth());
            item.setHeight(b.getHeight());
            item.setLength(b.getLength());
            item.setFragility(b.getFragility());
            item.setWeight(b.getWeight());
            item.setUserID(getUserId());
            items.add(item);
        }


        System.out.println("Saving to inventory");
        try{
            inventoryService.addBulkInventory(items);
            System.out.println("Complete");
        }catch(Exception e){
            System.out.println("Error: " + e.toString());
        }



    }

    private void GenerateRandomBoxes()
    {

        float totalVolumeGenerated = 0;
        int numberOfBoxesGenerated = 1;


        while(totalVolumeGenerated <= movingTruck.GetVolumeOfTruckInches())
        {
            Box newRandomBox = GenerateNewRandomBox();
            newRandomBox.setDescription("Randomly Generated Box " + numberOfBoxesGenerated);
            numberOfBoxesGenerated++;

            if(totalVolumeGenerated + newRandomBox.getVolume() > movingTruck.GetVolumeOfTruckInches())
                return;

            else
            {
                totalVolumeGenerated += newRandomBox.getVolume();


                moveInventory.add(newRandomBox);
            }
        }
    }

    private Box GenerateNewRandomBox()
    {
//        //home dpot box sizes
//        Box[] sizes =
//                {
//                        new Box(22,15,16),
//                        new Box(28,16,15),
//                        new Box(16,12,12),
//                        new Box(22,21,22)
//                };
//
//        Random rand = new Random();
//        return sizes[rand.nextInt(sizes.length - 1)];

        int minimumSize = 3;
        int maximumSize = 7;

        int length, width, height;

        Random rand = new Random();

        length = minimumSize + rand.nextInt(maximumSize - minimumSize);
        width = minimumSize + rand.nextInt(maximumSize - minimumSize);
        height = minimumSize + rand.nextInt(maximumSize - minimumSize);

        return new Box(width * 6, height * 6, length * 6);
    }

    private int getUserId(){
        return sp.getInt("loginID", 0);
    }

    private void GetMoveInventory()
    {
        try
        {
            moveInventory = inventoryService.getInventoryAsBoxes(getUserId());
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }

        System.out.println("Got move inventory! size = " + moveInventory.size());
    }

    private void GetTruckSize()
    {
        //System.out.println("Started Getting Truck Size!");
        movingTruck = new Truck();
        //System.out.println("Finished Getting Truck Size!");
    }

    private void GenerateLoadPlan()
    {
        //System.out.println("Started GenerateLoadPlan!");
        for (Box currentBox : moveInventory)//for each box..
        {
            if(BoxFitsWithinSpace(new EmptySpace(movingTruck.getLengthInches(),movingTruck.getWidthInches(), movingTruck.getHeightInches(), new Vector(0,0,0)),currentBox))
                PlaceBox(FindPlaceForBox(currentBox), currentBox);
        }
        //System.out.println("Finished GenerateLoadPlan!");
    }

    private void PlaceBox(LoadStatistics input_Info, Box input_Box)
    {
        EmptySpace idealSpace = plan.GetLoads().get(input_Info.GetLoadIndex()).GetEmptySpaces().get(input_Info.GetEmptySpaceIndex());

        input_Box.setDestination(new Vector(idealSpace.GetOffset().getX() + (idealSpace.GetWidth() - input_Box.getWidth()), idealSpace.GetOffset().getY(), idealSpace.GetOffset().getZ() + (idealSpace.GetLength() - input_Box.getLength())));
        plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
        plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);

        plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(idealSpace.GetLength() - input_Box.getLength(), idealSpace.GetWidth(), idealSpace.GetHeight(), idealSpace.GetOffset() ));
        plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(input_Box.getLength(), idealSpace.GetWidth() - input_Box.getWidth(), idealSpace.GetHeight(), new Vector(idealSpace.GetOffset().getX(),idealSpace.GetOffset().getY(),idealSpace.GetOffset().getZ() + (idealSpace.GetLength()-input_Box.getLength()))));
        plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(input_Box.getLength(), input_Box.getWidth(), idealSpace.GetHeight() - input_Box.getHeight(), new Vector(idealSpace.GetOffset().getX() + (idealSpace.GetWidth() - input_Box.getWidth()), idealSpace.GetOffset().getY() + input_Box.getHeight(), idealSpace.GetOffset().getZ() + (idealSpace.GetLength() - input_Box.getLength()))));

        /*
        switch(input_Info.GetNumberOfMatchingDimensions())
        {
            case 3:
                //System.out.println("Box " + input_Box.getBoxId() + " is being placed under case 3");
                //the box is the exact same size as the space
                //completely delete the space and put the box in its place
                //          x                      x                       z
                //  |---------------|      |---------------|      |---------------|
                //  |               |      |               |      |               |
                // z|      box      |     y|      box      |     y|      box      |
                //  |               |      |               |      |               |
                //  |---------------o      |---------------o      |---------------o

                input_Box.setDestination(idealSpace.GetOffset());
                plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
                plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);

                break;
            case 2:
                //System.out.println("Box " + input_Box.getBoxId() + " is being placed under case 2");
                //the box matches the space in 2 dimensions
                //divide the space in 2 in the dimension that doesn't max, replace
                //the filled space with a box
                //          x                      x                       z
                //  |---------------|      |---------------|      |-------|-------|
                //  |      box      |      |               |      |       |       |
                // z|_______________o     y|      box      |     y|  box  | space |
                //  |     space     |      |               |      |       |       |
                //  |---------------o      |---------------o      |-------o-------o

                input_Box.setDestination(new Vector(idealSpace.GetOffset().getX(), idealSpace.GetOffset().getY(), idealSpace.GetOffset().getZ() + (idealSpace.GetLength() - input_Box.getLength())));
                plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
                plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);

                plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(idealSpace.GetWidth(), idealSpace.GetHeight(), idealSpace.GetLength() - input_Box.getLength(), idealSpace.GetOffset()));

                break;
            case 1:
                //System.out.println("Box " + input_Box.getBoxId() + " is being placed under case 1");
                //the box matches the width, height, or length of the space and nothing else.
                //the space will need to be divided in to 3 pieces
                //          x                      x                      z
                //  |-------|-------|      |-------|-------|      |-------|-------|
                //  |  box  | space |      |       |       |      |       |       |
                // z|_______o_______o     y|  box  | space |     y|  box  | space |
                //  |     space     |      |       |       |      |       |       |
                //  |---------------o      |-------o-------o      |-------o-------o
                input_Box.setDestination(new Vector(idealSpace.GetOffset().getX() + (idealSpace.GetWidth() - input_Box.getWidth()), idealSpace.GetOffset().getY(), idealSpace.GetOffset().getZ() + (idealSpace.GetLength() - input_Box.getLength())));
                plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
                plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);

                plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(idealSpace.GetLength() - input_Box.getLength(), idealSpace.GetWidth(), idealSpace.GetHeight(), idealSpace.GetOffset() ));
                plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(input_Box.getLength(), idealSpace.GetWidth() - input_Box.getWidth(), idealSpace.GetHeight(), new Vector(idealSpace.GetOffset().getX(),idealSpace.GetOffset().getY(),idealSpace.GetOffset().getZ() + (idealSpace.GetLength()-input_Box.getLength()) )));

                break;
            case 0:
                //System.out.println("Box " + input_Box.getBoxId() + " is being placed under case 0");
                //          x                      x                       z
                //  |-------|-------|      |-------|-------|      |-------|-------|
                //  |  box  | space |      | space |       |      | space |       |
                // z|_______o_ _____|     y|-------o space |     y|-------o space |
                //  |     space     |      |  box  |       |      |  box  |       |
                //  |---------------o      |-------o-------o      |-------o-------o

            default:
                //we should never hit this code.
                break;
        }
        //System.out.println("Finished PlaceBox!");

         */
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
         float bestDistanceFromStart = -1f;

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
                     float distanceFromStart = currentSpace.GetOffset().GetDistanceFromOrigin();

                     if(numberOfMatchingDimensions > numberOfMatchingDimensionsInBestFit)
                     {
                         numberOfMatchingDimensionsInBestFit = numberOfMatchingDimensions;
                         bestFitSpaceIndex = spaceIndex;
                         bestDistanceFromStart = distanceFromStart;
                     }
                     else if(numberOfMatchingDimensions == numberOfMatchingDimensionsInBestFit && bestDistanceFromStart < distanceFromStart)
                     {
                         bestFitSpaceIndex = spaceIndex;
                         bestDistanceFromStart = distanceFromStart;
                         numberOfMatchingDimensionsInBestFit = numberOfMatchingDimensions;
                     }
                 }
             }

             if(sizeAtLeastAsLargeAsBoxFound)
             {
                 return new LoadStatistics(input_LoadIndex, bestFitSpaceIndex, numberOfMatchingDimensionsInBestFit, input_Load.GetEmptyArea(),bestDistanceFromStart );
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

        if(input_Box.getHeight() == input_Space.GetHeight())
            numberOfMatchingDimensions++;

        if(input_Box.getLength() == input_Space.GetLength())
            numberOfMatchingDimensions++;

        return numberOfMatchingDimensions;
    }

    private boolean BoxFitsWithinSpace(EmptySpace input_Space, Box input_Box)
    {
        return (input_Space.GetWidth() >= input_Box.getWidth() ) &&(input_Space.GetLength() >= input_Box.getLength() ) && (input_Space.GetHeight() >= input_Box.getHeight() );
    }

    public void setUseRandomBoxes(boolean input)
    {
        useRandomBoxes = input;
    }

    private void sendLoadPlanToDatabase()
    {

        ArrayList<LoadPlanBox> data = generateDBDataModel();

        try
        {
            boxService.addLoadPlan(sp.getInt("loginID", 0), data);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
           e.printStackTrace();
        }
    }

    private ArrayList<LoadPlanBox> generateDBDataModel()
    {
        System.out.println("in generateDBDataModel");
        ArrayList<LoadPlanBox> dataModel = new ArrayList<LoadPlanBox>();
        int loadIndex = 0;
        int boxIndex = 0;

        while(plan.HasNextLoad())
        {
            System.out.println("  in hasNextLoad");
            Load curLoad = plan.GetNextLoad();

            while(curLoad.HasNextBox() )
            {
                System.out.println("    in hasNextBox");
                Box b = curLoad.GetNextBox();
                dataModel.add(new LoadPlanBox(b.getId(), b.getLength(), b.getWidth(), b.getHeight(), b.getDestination().getX(), b.getDestination().getY(), b.getDestination().getZ(), b.getWeight(), b.getFragility(), b.getDescription(), loadIndex, boxIndex, b.getBoxId()) );
                boxIndex++;
            }

            boxIndex = 0;
            loadIndex++;
        }

        return dataModel;
    }

    private  boolean violatesConstraints(Box currentBox, LoadPlan currentLoadPlan){
        //TODO: implement
        return  false;
    }

    public int score(LoadPlan plan){
        //TODO: score a load plan
        return 0;
    }

    public LoadPlan determineBestLoadPlan(ArrayList<LoadPlan> allPlans){

        LoadPlan bestScoredPlan = null;
        int bestScore = -1;

        for(LoadPlan l : allPlans){
            int score = score(l);
            if(bestScoredPlan == null || score > bestScore){
                bestScore = score;
                bestScoredPlan = l;
            }
        }
        return bestScoredPlan;

    }


    public ArrayList<LoadPlan> processTrees(){


        ArrayList<LoadPlan> finishedLoadPlans = new ArrayList<LoadPlan>();
        Stack<DecisionFrame> toProcess = new Stack<DecisionFrame>();  //we push on to this for processing

        DecisionFrame initial = new DecisionFrame();
        initial.remainingBoxes = new LinkedList<Box>(moveInventory);
        initial.currentLoadPlan = new LoadPlan(movingTruck);  //create the initial plan

        toProcess.push(initial);

        while(!toProcess.isEmpty()){
            DecisionFrame current = toProcess.pop(); //get the next frame to check on

            if(current.remainingBoxes.size() > 0){  //we have boxes left to process
                //grab the next box

                Box nextBox = current.remainingBoxes.remove();

                //for each space in each load in the current load plan that the box can fit into:

                ArrayList<Load> loads = current.currentLoadPlan.GetLoads();
                for( int loadIndex = 0; loadIndex < loads.size(); loadIndex++){
                    Load nextLoad = loads.get(loadIndex);
                    ArrayList<EmptySpace> spaces = nextLoad.GetEmptySpaces();
                    for(int emptySpaceIndex = 0; emptySpaceIndex < spaces.size(); emptySpaceIndex++){
                        EmptySpace e = spaces.get(emptySpaceIndex);
                        //generate a new load plan from putting that box into every available space
                        if(e.canFit(nextBox)){

                            Box copyOfNextBox = new Box(nextBox); //need to make a copy because it is going to be placed in a different location

                            //duplicate the plan
                            LoadPlan copy = new LoadPlan(current.currentLoadPlan);  //we make a copy so that it's modifiable from the others

                            Load copyLoad = copy.GetLoads().get(loadIndex);
                            EmptySpace targetCopy = copyLoad.GetEmptySpaces().get(emptySpaceIndex);

                            ArrayList<EmptySpace> newSpaces = targetCopy.split(copyOfNextBox);
                            copyLoad.RemoveSpace(targetCopy);

                            for(EmptySpace s :newSpaces)
                                copyLoad.AddSpace(s);  //we add all the new spaces

                            //TODO: does the split function modify the box?  or do we need to do that here?

                            if(!violatesConstraints(copyOfNextBox, copy)){  //does any of the load plans i've just generated violate a rule
                                DecisionFrame n = new DecisionFrame();
                                n.currentLoadPlan = copy;
                                n.remainingBoxes = new LinkedList<Box>(current.remainingBoxes); //copy the queue of the remaining boxes

                                toProcess.push(n); //push the next item to process that we can continue down that decision tree

                            } //if so remove that load plan

                        }

                        //TODO: if we can't fit the box anywhere, we probably need a new load?

                        //process all other load plans while boxes remain
                    }

                }


            }
            else{
                finishedLoadPlans.add(current.currentLoadPlan);  //we have reached the end
            }


        }



        return finishedLoadPlans;

    }


    private class DecisionFrame{
        Queue<Box> remainingBoxes;
        LoadPlan currentLoadPlan;

    }

}