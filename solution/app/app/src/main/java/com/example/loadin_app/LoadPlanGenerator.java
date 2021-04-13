package com.example.loadin_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.LoadPlanBox;
import odu.edu.loadin.common.MovingTruck;

import com.example.loadin_app.extensions.IExtendedIterator;
import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.World;
import com.example.loadin_app.ui.opengl.Truck;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class LoadPlanGenerator
{
    private MovingTruck movingTruck;

    private ArrayList<Box> moveInventory = new ArrayList<Box>();

    private LoadPlan plan;

    private boolean useRandomBoxes = false;

   int userId;
    protected  InventoryServiceImpl inventoryService;
    protected LoadPlanBoxServiceImpl boxService;

    public LoadPlanGenerator(int userId, InventoryServiceImpl inventoryService, LoadPlanBoxServiceImpl boxService, MovingTruck movingTruck, ArrayList<Box> moveInventory)
    {
        this.moveInventory = moveInventory;
        this.inventoryService   = inventoryService;
        this.boxService = boxService;
        this.userId = userId;
        this.movingTruck = movingTruck;
    }

//    public LoadPlan StartLoadPlan()
//    {
//        GetTruckSize();
//        plan = new LoadPlan(movingTruck); //make an empty load plan based on the dimensions of the truck
//
//        if(useRandomBoxes){
//            GenerateRandomBoxes();
//            PersistBoxesToUserMoveInventory();
//        }
//
//        GetMoveInventory();
//
//        SortMoveInventory();
//
//        GenerateLoadPlan();
//
//        sendLoadPlanToDatabase();
//
//        return plan;
//    }

//    private void SortMoveInventory()
//    {
//        moveInventory.sort(new Comparator<Box>()
//        {
//            @Override
//            public int compare(Box o1, Box o2)
//            {
//                return (int) (o2.getXZArea() - o1.getXZArea());
//            }
//        });
//    }

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
            item.setUserID(userId);
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

        //IF YOU REALLY WANT TO STRESS IT, USE THIS FOR LOOP
        for(int i = 0; i < 1000; i++){  //simple generation for now
            Box b = GenerateNewRandomBox();
            moveInventory.add(b);
        }

//        float totalVolumeGenerated = 0;
//        int numberOfBoxesGenerated = 1;
//
//
//        while(totalVolumeGenerated <= movingTruck.GetVolumeOfTruckInches())
//        {
//            Box newRandomBox = GenerateNewRandomBox();
//            newRandomBox.setDescription("Randomly Generated Box " + numberOfBoxesGenerated);
//            numberOfBoxesGenerated++;
//
//            if(totalVolumeGenerated + newRandomBox.getVolume() > movingTruck.GetVolumeOfTruckInches())
//                return;
//
//            else
//            {
//                totalVolumeGenerated += newRandomBox.getVolume();
//
//
//                moveInventory.add(newRandomBox);
//            }
//        }
    }

    private Box GenerateNewRandomBox()
    {
////        //home dpot box sizes
        Box[] sizes =
                {
                        new Box(22,15,16),
                        new Box(28,16,15),
                        new Box(16,12,12),
                        new Box(22,21,22)
                };

        Random rand = new Random();
        Box b =  sizes[rand.nextInt(sizes.length - 1)];

//        int minimumSize = 3;
//        int maximumSize = 7;
//
//        int length, width, height;
//
//        Random rand = new Random();
//
//        length = minimumSize + rand.nextInt(maximumSize - minimumSize);
//        width = minimumSize + rand.nextInt(maximumSize - minimumSize);
//        height = minimumSize + rand.nextInt(maximumSize - minimumSize);
//
//        Box b = new Box(width * 6, height * 6, length * 6);

        b.setFragility( 1 + rand.nextInt(4));
        //b.setFragility(1);
        b.setWeight(1 + rand.nextInt(9));

        return b;
    }

//    private int getUserId(){
//        return sp.getInt("loginID", 0);
//    }

//    private void GetMoveInventory()
//    {
//        try
//        {
//            moveInventory = inventoryService.getInventoryAsBoxes(getUserId());
//        }
//        catch(Exception ex)
//        {
//            System.out.println(ex);
//        }
//
//        System.out.println("Got move inventory! size = " + moveInventory.size());
//    }

//    private void GetTruckSize()
//    {
//        //System.out.println("Started Getting Truck Size!");
//        movingTruck = new Truck();
//        //System.out.println("Finished Getting Truck Size!");
//    }

//    private void GenerateLoadPlan()
//    {
//        //System.out.println("Started GenerateLoadPlan!");
//        for (Box currentBox : moveInventory)//for each box..
//        {
//            if(new EmptySpace(movingTruck.getLengthInches(),movingTruck.getWidthInches(), movingTruck.getHeightInches(), new Vector(0,0,0)).canFit((currentBox)))
//                PlaceBox(FindPlaceForBox(currentBox), currentBox);
//        }
//        //System.out.println("Finished GenerateLoadPlan!");
//    }

    public LoadPlan GenerateLoadPlan(){



        ArrayList<LoadPlan> availablePlans = processTrees();

        plan = availablePlans.size() > 0 ? availablePlans.get(0) : null;

        System.out.println("Finished");

        return plan;

    }

//    private void PlaceBox(LoadStatistics input_Info, Box input_Box)
//    {
//        EmptySpace idealSpace = plan.GetLoads().get(input_Info.GetLoadIndex()).GetEmptySpaces().get(input_Info.GetEmptySpaceIndex());
//
//        input_Box.setDestination(new Vector(idealSpace.GetOffset().getX() + (idealSpace.GetWidth() - input_Box.getWidth()), idealSpace.GetOffset().getY(), idealSpace.GetOffset().getZ() + (idealSpace.GetLength() - input_Box.getLength())));
//        plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
//        plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);
//
//        plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpaces(idealSpace.split(input_Box));
//
//        /*
//        switch(input_Info.GetNumberOfMatchingDimensions())
//        {
//            case 3:
//                //System.out.println("Box " + input_Box.getBoxId() + " is being placed under case 3");
//                //the box is the exact same size as the space
//                //completely delete the space and put the box in its place
//                //          x                      x                       z
//                //  |---------------|      |---------------|      |---------------|
//                //  |               |      |               |      |               |
//                // z|      box      |     y|      box      |     y|      box      |
//                //  |               |      |               |      |               |
//                //  |---------------o      |---------------o      |---------------o
//
//                input_Box.setDestination(idealSpace.GetOffset());
//                plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
//                plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);
//
//                break;
//            case 2:
//                //System.out.println("Box " + input_Box.getBoxId() + " is being placed under case 2");
//                //the box matches the space in 2 dimensions
//                //divide the space in 2 in the dimension that doesn't max, replace
//                //the filled space with a box
//                //          x                      x                       z
//                //  |---------------|      |---------------|      |-------|-------|
//                //  |      box      |      |               |      |       |       |
//                // z|_______________o     y|      box      |     y|  box  | space |
//                //  |     space     |      |               |      |       |       |
//                //  |---------------o      |---------------o      |-------o-------o
//
//                input_Box.setDestination(new Vector(idealSpace.GetOffset().getX(), idealSpace.GetOffset().getY(), idealSpace.GetOffset().getZ() + (idealSpace.GetLength() - input_Box.getLength())));
//                plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
//                plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);
//
//                plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(idealSpace.GetWidth(), idealSpace.GetHeight(), idealSpace.GetLength() - input_Box.getLength(), idealSpace.GetOffset()));
//
//                break;
//            case 1:
//                //System.out.println("Box " + input_Box.getBoxId() + " is being placed under case 1");
//                //the box matches the width, height, or length of the space and nothing else.
//                //the space will need to be divided in to 3 pieces
//                //          x                      x                      z
//                //  |-------|-------|      |-------|-------|      |-------|-------|
//                //  |  box  | space |      |       |       |      |       |       |
//                // z|_______o_______o     y|  box  | space |     y|  box  | space |
//                //  |     space     |      |       |       |      |       |       |
//                //  |---------------o      |-------o-------o      |-------o-------o
//                input_Box.setDestination(new Vector(idealSpace.GetOffset().getX() + (idealSpace.GetWidth() - input_Box.getWidth()), idealSpace.GetOffset().getY(), idealSpace.GetOffset().getZ() + (idealSpace.GetLength() - input_Box.getLength())));
//                plan.GetLoads().get(input_Info.GetLoadIndex()).AddBox(input_Box);
//                plan.GetLoads().get(input_Info.GetLoadIndex()).RemoveSpace(idealSpace);
//
//                plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(idealSpace.GetLength() - input_Box.getLength(), idealSpace.GetWidth(), idealSpace.GetHeight(), idealSpace.GetOffset() ));
//                plan.GetLoads().get(input_Info.GetLoadIndex()).AddSpace(new EmptySpace(input_Box.getLength(), idealSpace.GetWidth() - input_Box.getWidth(), idealSpace.GetHeight(), new Vector(idealSpace.GetOffset().getX(),idealSpace.GetOffset().getY(),idealSpace.GetOffset().getZ() + (idealSpace.GetLength()-input_Box.getLength()) )));
//
//                break;
//            case 0:
//                //System.out.println("Box " + input_Box.getBoxId() + " is being placed under case 0");
//                //          x                      x                       z
//                //  |-------|-------|      |-------|-------|      |-------|-------|
//                //  |  box  | space |      | space |       |      | space |       |
//                // z|_______o_ _____|     y|-------o space |     y|-------o space |
//                //  |     space     |      |  box  |       |      |  box  |       |
//                //  |---------------o      |-------o-------o      |-------o-------o
//
//            default:
//                //we should never hit this code.
//                break;
//        }
//        //System.out.println("Finished PlaceBox!");
//
//         */
//    }
//

//    private LoadStatistics FindPlaceForBox(Box input_Box)
//    {
//        boolean placeForBoxExists = true;
//
//        ArrayList<LoadStatistics> options = new ArrayList<LoadStatistics>();
//
//        for (int loadIndex = 0; loadIndex < plan.GetLoads().size(); loadIndex++) //look in each truck...
//        {
//            LoadStatistics currentOption = FindCandidateSpotForBoxInLoad(input_Box, plan.GetLoads().get(loadIndex), loadIndex);
//
//            if(currentOption != null)
//            {
//                options.add(currentOption);
//            }
//        }
//
//        if(options.isEmpty())
//        {
//            plan.AddLoad();
//            return FindPlaceForBox(input_Box); // this better only recurse once.......
//        }
//        else
//        {
//            return DetermineBestBoxPlacementOption(options);
//        }
//    }

//     private LoadStatistics FindCandidateSpotForBoxInLoad(Box input_Box, Load input_Load, int input_LoadIndex)
//     {
//
//         int numberOfMatchingDimensionsInBestFit = -1;
//         int bestFitSpaceIndex = -1;
//         float bestDistanceFromStart = -1f;
//
//         if (input_Load.GetEmptySpaces().size() > 0)
//         {
//             boolean sizeAtLeastAsLargeAsBoxFound = false;
//
//             for(int spaceIndex = 0 ; spaceIndex < input_Load.GetEmptySpaces().size() ; spaceIndex++)
//             {
//                 EmptySpace currentSpace = input_Load.GetEmptySpaces().get(spaceIndex);
//
//                 if( currentSpace.canFit((input_Box) ) )
//                 {
//                     sizeAtLeastAsLargeAsBoxFound = true;
//
//                     //int numberOfMatchingDimensions = CheckIfEmptySpaceMatchesBoxDimensions(input_Box, currentSpace);  //MOVED TO EMPTY SPACE
//                     int numberOfMatchingDimensions = currentSpace.rankFit(input_Box);
//                     float distanceFromStart = currentSpace.GetOffset().GetDistanceFromOrigin();
//
//                     if(numberOfMatchingDimensions > numberOfMatchingDimensionsInBestFit)
//                     {
//                         numberOfMatchingDimensionsInBestFit = numberOfMatchingDimensions;
//                         bestFitSpaceIndex = spaceIndex;
//                         bestDistanceFromStart = distanceFromStart;
//                     }
//                     else if(numberOfMatchingDimensions == numberOfMatchingDimensionsInBestFit && bestDistanceFromStart < distanceFromStart)
//                     {
//                         bestFitSpaceIndex = spaceIndex;
//                         bestDistanceFromStart = distanceFromStart;
//                         numberOfMatchingDimensionsInBestFit = numberOfMatchingDimensions;
//                     }
//                 }
//             }
//
//             if(sizeAtLeastAsLargeAsBoxFound)
//             {
//                 return new LoadStatistics(input_LoadIndex, bestFitSpaceIndex, numberOfMatchingDimensionsInBestFit, input_Load.GetEmptyVolume(),bestDistanceFromStart );
//             }
//         }
//
//         return null;
//     }

//    private LoadStatistics DetermineBestBoxPlacementOption(ArrayList<LoadStatistics> input_Options)
//    {
//        LoadStatistics answer = input_Options.get(0);
//
//        for(LoadStatistics currentOption : input_Options)
//        {
//            if(currentOption.GetNumberOfMatchingDimensions() >= answer.GetNumberOfMatchingDimensions() && currentOption.GetEmptyArea() <= answer.GetEmptyArea())
//            {
//                answer = currentOption;
//            }
//        }
//
//        return answer;
//    }

    //MOVED TO SPACE CLASS
//    private int CheckIfEmptySpaceMatchesBoxDimensions(Box input_Box, EmptySpace input_Space)
//    {
//        int numberOfMatchingDimensions = 0;
//
//        if(input_Box.getWidth() == input_Space.GetWidth())
//            numberOfMatchingDimensions++;
//
//        if(input_Box.getHeight() == input_Space.GetHeight())
//            numberOfMatchingDimensions++;
//
//        if(input_Box.getLength() == input_Space.GetLength())
//            numberOfMatchingDimensions++;
//
//        return numberOfMatchingDimensions;
//    }



    public void setUseRandomBoxes(boolean input)
    {
        useRandomBoxes = input;
    }

    public void sendLoadPlanToDatabase()
    {

        ArrayList<LoadPlanBox> data = generateDBDataModel();

        try
        {
            boxService.addLoadPlan(userId, data);
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
                LoadPlanBox lpd =new LoadPlanBox(b.getId(), b.getLength(), b.getWidth(), b.getHeight(), b.getDestination().getX(), b.getDestination().getY(), b.getDestination().getZ(), b.getWeight(), b.getFragility(), b.getDescription(), loadIndex, boxIndex, b.getBoxId());
                lpd.setTruck(movingTruck);
                dataModel.add(lpd);
                boxIndex++;
            }

            boxIndex = 0;
            loadIndex++;
        }

        return dataModel;
    }

    private  boolean violatesConstraints(Box currentBox, Load loadForBox)
    {
        //if we have a box that is above any other box and it is either heavier or less fragile, then it's a failure

        IExtendedIterator<Box> it = loadForBox.iterator();
        while(it.hasNext()){
            Box b = it.next();

            if(b.getId() == currentBox.getId())
                continue;

            if(currentBox.isAbove(b) && (currentBox.getWeight() > b.getWeight() || currentBox.getFragility() < b.getFragility() )){
                return true;
            }
        }

        return  false;
    }


    private BoxPlacementPossibility attemptBoxPlacement(Box aBox, EmptySpace aSpace, Load theLoadForSpace){
        BoxPlacementPossibility result = null;
        if(aSpace.canFit(aBox)){
            int rank = aSpace.rankFit(aBox);
            //we place the box into the space to evaluate
            //BUT, do not assume that box will always be there until we clone it and make it a decision
            //it can move around as we test other spaces
            aSpace.setBoxInSpace(aBox);

            if(!violatesConstraints(aBox, theLoadForSpace)){  //does any of the load plans i've just generated violate a rule
               result = new BoxPlacementPossibility();
               result.box = aBox;
               result.load = theLoadForSpace;
               result.priority = rank;
               result.space = aSpace;

            }
        }
        return result;
    }

    private ArrayList<BoxPlacementPossibility> calculateAllPossibilities( Box box, Load load){
        ArrayList<BoxPlacementPossibility> results = new ArrayList<BoxPlacementPossibility>();

        ArrayList<EmptySpace> spaces = load.GetEmptySpaces();

        for(int i = 0; i < spaces.size(); i++){
            EmptySpace space = spaces.get(i);

            BoxPlacementPossibility option = attemptBoxPlacement(box, space, load);
            if(option != null)
                results.add(option);

        }

       return results;

    }





    private void populatePossibleBoxPlacements(DecisionFrame currentFrame){
        //we haven't created possibilities for a box yet
        if(currentFrame.possibleBoxPlacements == null)
            currentFrame.possibleBoxPlacements = new LinkedList<BoxPlacementPossibility>();
        Box next = currentFrame.remainingBoxes.remove(); //get the next box

        for(Load l : currentFrame.currentLoadPlan.GetLoads()){
            ArrayList<BoxPlacementPossibility> p = calculateAllPossibilities(next, l);
            for(BoxPlacementPossibility bp: p){
                currentFrame.possibleBoxPlacements.add(bp); //add it as a possibility
            }
        }

        if(currentFrame.possibleBoxPlacements.size() == 0){
            //we're going to add a load to the plan
            LoadPlan thePlan = currentFrame.currentLoadPlan;
            Load newLoad = thePlan.AddLoad();
            ArrayList<BoxPlacementPossibility> p = calculateAllPossibilities(next, newLoad);
            for(BoxPlacementPossibility bp: p){
                currentFrame.possibleBoxPlacements.add(bp); //add it as a possibility
            }

            //TODO:  if the box STILL can't fit, it's an error with too big a box or too small a truck

        }
        currentFrame.possibleBoxPlacements.sort((p1, p2) -> p1.priority == p2.priority ? 0 : p1.priority > p2.priority ? -1 : 1);
    }

    private ArrayList<DecisionFrame> processFrame(DecisionFrame currentFrame, ArrayList<LoadPlan> finishedLoadPlans){
        ArrayList<DecisionFrame> toProcess = new ArrayList<DecisionFrame>();
        //now for a frame, we want to exhaust all box possiblities against all spaces, but we don't want to act on them until we really need to

        if(currentFrame.remainingBoxes.size() == 0 && (currentFrame.possibleBoxPlacements == null || currentFrame.possibleBoxPlacements.size() == 0)){
            finishedLoadPlans.add(currentFrame.currentLoadPlan); //there are no more boxes and no more possibiltiies to look at
            return toProcess;
        }


        if(currentFrame.possibleBoxPlacements == null || currentFrame.possibleBoxPlacements.size() == 0){
            populatePossibleBoxPlacements(currentFrame);
        }

        if(currentFrame.possibleBoxPlacements.size() == 0){  //the last box has some more options left to look at
            //if we've gotten to here, then it means that simply we cannot place the box anywhere
            //this would indicate that it can't go on a truck
            //we are going to just 'throw' the box away at this point
            return processFrame(currentFrame, finishedLoadPlans); //this will trigger the process to try again
        }

        //if we have gotten here, we have some possibilities to explore
        BoxPlacementPossibility nextPossibility = currentFrame.possibleBoxPlacements.remove(); //the assumption is we have the best option first

        toProcess.add(currentFrame);  //we haven't thrown the frame away yet
        DecisionFrame newDirection = placeBoxIntoLoad(currentFrame, nextPossibility);
        toProcess.add(newDirection);  //add this to be processed next

        return toProcess;
    }

    private DecisionFrame placeBoxIntoLoad(DecisionFrame previous,  BoxPlacementPossibility possibility){
        //we need to clone the load and put it in the load in the desired space

        DecisionFrame next = new DecisionFrame();
        next.remainingBoxes = new LinkedList<>(previous.remainingBoxes);

        LoadPlan newLoadPlan = new LoadPlan(previous.currentLoadPlan);
        Box clonedBox = new Box(possibility.box);

        int loadIndex = previous.currentLoadPlan.GetLoads().indexOf(possibility.load);
        int spaceIndex = possibility.load.GetEmptySpaces().indexOf(possibility.space);

        Load newTargetLoad = newLoadPlan.GetLoads().get(loadIndex);
        EmptySpace newTargetSpace = newTargetLoad.GetEmptySpaces().get(spaceIndex);
        newTargetLoad.GetEmptySpaces().remove(newTargetSpace);
        newTargetSpace.setBoxInSpace(clonedBox);

        ArrayList<EmptySpace> newSpaces = newTargetSpace.split(clonedBox);
        for(EmptySpace e: newSpaces)
            newTargetLoad.AddSpace(e);

        newTargetLoad.AddBox(clonedBox);

        next.currentLoadPlan = newLoadPlan; //the next frame gets the new loadplan
        return next;

    }


    public ArrayList<LoadPlan> processTrees(){


        ArrayList<LoadPlan> finishedLoadPlans = new ArrayList<LoadPlan>();
        Stack<DecisionFrame> toProcess = new Stack<DecisionFrame>();  //we push on to this for processing

        DecisionFrame initial = new DecisionFrame();
        moveInventory.sort( (o1, o2) -> {  //sort by weight first, then fragility, then area

            if(o1.getWeight() == o2.getWeight()){
                if(o1.getFragility() == o2.getFragility()) {
                    if (o1.calcArea() == o2.calcArea()) {
                        return 0;
                    }
                    else if(o1.calcArea() > o2.calcArea()){
                        return -1;
                    }
                    else
                        return 1;
                }
                else if(o1.getFragility() < o2.getFragility()){
                    return -1;
                }else{
                    return 1;
                }

            }else if(o1.getWeight() > o2.getWeight()){
                return -1;
            }else{
                return 1;
            }

        });
        initial.remainingBoxes = new LinkedList<Box>(moveInventory);
        initial.currentLoadPlan = new LoadPlan(new Truck(movingTruck));  //create the initial plan

        toProcess.push(initial);

        while(!toProcess.isEmpty() && finishedLoadPlans.size() < 1){
            DecisionFrame current = toProcess.pop(); //get the next frame to check on

            ArrayList<DecisionFrame> results = processFrame(current, finishedLoadPlans);

            for(DecisionFrame f: results)
                toProcess.push(f);

        }



        return finishedLoadPlans;

    }

    private class BoxPlacementPossibility{
        EmptySpace space;
        Box box;
        Load load;
        int priority;
    }

    private class DecisionFrame{
        LinkedList<BoxPlacementPossibility> possibleBoxPlacements;
        Queue<Box> remainingBoxes;
        LoadPlan currentLoadPlan;
    }

}