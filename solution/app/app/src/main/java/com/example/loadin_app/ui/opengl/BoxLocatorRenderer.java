package com.example.loadin_app.ui.opengl;

import android.content.Context;

import com.example.loadin_app.LoadPlan;
import com.example.loadin_app.OpenGlBoxLocatorActivity;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.ui.opengl.programs.IMoveable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.microedition.khronos.opengles.GL10;

import odu.edu.loadin.common.Inventory;

/*
In this class, we want to be able to display the load plan
 */
public class BoxLocatorRenderer extends BaseGLRenderer {

    private LoadPlan theLoadPlan;

    private LoadPlanStepIterator lpiter;

    private Object signalLock = new Object();


    private Truck theTruck;
    private Vector boxStagingArea;
    private LoadPlanStep lastRenderCheckStep;
    private Box toLocate;

    private LoadPlanStep located;

    private LoadPlanDisplayerState state;
    private InventoryServiceImpl inventoryService;
    ArrayList<LoadPlanStep> betweenCurrentStepAndLocatedBox;


    public BoxLocatorRenderer(Context ctx, LoadPlanBoxServiceImpl boxService, InventoryServiceImpl inventoryService, Box toLocate) {
        super(ctx, boxService, false);
        this.inventoryService = inventoryService;
        // testBitmap = source;
        state = LoadPlanDisplayerState.Initial;

        this.toLocate = toLocate;
        betweenCurrentStepAndLocatedBox = new ArrayList<LoadPlanStep>();
    }

    @Override
    protected void adjustCameraPlacement() {
        //camera controlled by other means
        if (located != null) {

            Vector boxCenter = located.currentBox.getCenter();
            theCamera.lookAt(boxCenter);  //always look at the current box
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        super.onSurfaceChanged(unused, width, height);
        try {
            theLoadPlan = new LoadPlan(boxService.getLoadPlan(OpenGlBoxLocatorActivity.sp.getInt("loginID", 0)));
        } catch (ExecutionException e) {
            //e.printStackTrace();
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }

        lpiter = new LoadPlanStepIterator(theLoadPlan);

        theTruck = theLoadPlan.GetTruck();
        theTruck.setMyWorld(theWorld);

        boxStagingArea = new Vector(0f, 0f, 0f);
        theHud.setDisplayButtons(false);

    }

    private boolean isCurrentStep(LoadPlanStep step) {
        if (step.loading) {
            switch (step.currentBox.getStatus()) {
                case Inventory.AT_DESTINATION:
                case Inventory.ON_TRUCK:
                    return false;
                case Inventory.AT_SOURCE:  //first step where we are at the source and it isn't on truck yet
                    return true;
            }
        } else {
            switch (step.currentBox.getStatus()) {
                case Inventory.AT_DESTINATION:
                    return false;
                case Inventory.ON_TRUCK:  //firs step where we are unloading and not at destination
                    return true;
            }
        }
        return false;
    }

    private boolean isLocateBoxStep(Box box) {
        return box.getBoxId() == toLocate.getBoxId();
    }

    private void initialize() {
        //we want to through the entire load and put all the boxes that are currently on the truck on the truck at their destination

        boolean foundCurrentStep = false;
        boolean foundToLocateBox = false;

        LoadPlanStep nextStep = null;
        located = null;


        while (lpiter.hasNext() && !(foundCurrentStep && foundToLocateBox)) {

            LoadPlanStep currentStep = lpiter.next();
            Box current = currentStep.currentBox;

            if (current != null && current.getMyWorld() == null) {
                current.setMyWorld(theWorld);
            }

            current.setVisible(true);

            foundCurrentStep = foundCurrentStep ? foundCurrentStep : isCurrentStep(currentStep);
            foundToLocateBox = foundToLocateBox ? foundToLocateBox : isLocateBoxStep(current);

            if (foundCurrentStep && nextStep == null) {
                nextStep = currentStep;
            }
            if (foundToLocateBox && located == null) {
                located = currentStep;
                toLocate = current;  //overwrite the toLocate box
            }

            if (foundCurrentStep || foundToLocateBox) {
                if (currentStep != nextStep && currentStep != located && current.getStatus().equalsIgnoreCase(Inventory.ON_TRUCK))  //we want all boxes inbetween the current step and the located box
                    betweenCurrentStepAndLocatedBox.add(currentStep); //add it inbetween
            }

            //figure out state of box in relation to world

            switch (current.getStatus()) {
                case Inventory.AT_DESTINATION:
                case Inventory.AT_SOURCE:
                    current.place(boxStagingArea);
                    if (current != toLocate)
                        current.setVisible(false);
                    break;
                case Inventory.ON_TRUCK:
                    Vector destination = theTruck.getWorldOffset().add(current.getDestination());
                    current.place(destination);
                    break;

            }


        }

    }

    private void calculateDisplayState() {

        if (located == null){
            state = LoadPlanDisplayerState.BoxNotFound;
            return;
        }


        switch (located.currentBox.getStatus()) {
            case Inventory.AT_DESTINATION:
            case Inventory.AT_SOURCE:
                state = LoadPlanDisplayerState.BoxStaged;
                located.currentBox.place(boxStagingArea);
                located.currentBox.setVisible(true);
                Vector boxCenter2 = located.currentBox.getCenter();
                Vector pointOfView = boxCenter2.add(new Vector(0.1f, 0f, -6f * 12f));  //set the camera so we're looking at it
                pointOfView.setY(6f * 12f); //fixed at eye height,
                theCamera.place(pointOfView);

                break;
            case Inventory.ON_TRUCK:
                state = LoadPlanDisplayerState.Start;
                break;
        }


    }


    private void updateState() {
        if (toLocate == null) {
            state = LoadPlanDisplayerState.BoxNotFound;
            return;
        }

        switch (state) {
            case Initial:
                initialize();
                state = LoadPlanDisplayerState.CalculateDisplayPlan;

                break;
            case CalculateDisplayPlan:
                calculateDisplayState();
                break;
            case Start:
                float middleTruck = theTruck.getWidthInches() / 2f;
                Vector cameraInit = theTruck.getWorldOffset().add(new Vector(middleTruck, 6f * 12f, -6f * 12f));
                theCamera.place(cameraInit);

                int index = 0;
                for (LoadPlanStep b : betweenCurrentStepAndLocatedBox) {

                    Vector move = new Vector(-theTruck.getWidthInches() - (1f * 12f), 0f, 0f);
                    Vector newDestination = b.currentBox.getWorldOffset().add(move);
                    animateMovable(b.currentBox, newDestination, 150 + ((betweenCurrentStepAndLocatedBox.size() - index++) * 75));
                    // b.currentBox.setVisible(false);


                }
                state = LoadPlanDisplayerState.AnimatingBoxes;
                break;

            case AnimatingBoxes:
                if (theWorld.getAnimiations().toArray().length == 0)
                    state = LoadPlanDisplayerState.ZoomIn;
                break;
            case ZoomIn:
                state = LoadPlanDisplayerState.ZoomingIn;
                Vector boxCenter2 = located.currentBox.getCenter();
                Vector pointOfView = boxCenter2.add(new Vector(0.1f, 0f, -3f * 12f));  //we're going to walk up to it
                pointOfView.setY(6f * 12f); //fixed at eye height,

                TransposeAnimation towardBox = new TransposeAnimation(
                        theCamera,
                        Duration.ofMillis(3 * 1000),
                        theWorld.getTick(),
                        pointOfView,
                        i -> {
                            state = LoadPlanDisplayerState.Done;
                        }
                );
                theWorld.addAnimation(towardBox); //add this so it gets processed


                break;
            case Done:
                state = LoadPlanDisplayerState.Resetting;
                float middleTruck2 = theTruck.getWidthInches() / 2f;
                Vector cameraInit2 = theTruck.getWorldOffset().add(new Vector(middleTruck2, 6f * 12f, -6f * 12f));
                TransposeAnimation towardStaging = new TransposeAnimation(
                        theCamera,
                        Duration.ofMillis(3 * 1000),
                        theWorld.getTick(),
                        cameraInit2,
                        i -> {

                            for (LoadPlanStep b : betweenCurrentStepAndLocatedBox) {
                                Vector newDestination = b.currentBox.getDestination().add(theTruck.getWorldOffset());
                                b.currentBox.place(newDestination);
                            }
                            state = LoadPlanDisplayerState.Start;
                        }
                );
                theWorld.addAnimation(towardStaging); //add this so it gets processed

                break;

        }


    }


    private void animateMovable(IMoveable target, Vector to, int animationMilliseconds) {

        TransposeAnimation animation = new TransposeAnimation(
                target,
                Duration.ofMillis(animationMilliseconds),
                theWorld.getTick(),
                to,
                i -> {
                    //do nothing

                }
        );
        theWorld.addAnimation(animation); //add this so it gets processed
    }


    @Override
    protected void renderWorld() {
        updateState(); //update state of load plan before render
        super.renderWorld();
    }

    @Override
    protected void renderHud() {
        LoadPlanStep current = located;


        if (current != lastRenderCheckStep) //don't re-render textures unless we detect a change
            onStepChanged();
        lastRenderCheckStep = current;
        //then render the HUD
        super.renderHud();
    }

    private void onStepChanged() {
        LoadPlanStep current = located;
        Box currentBox = current != null ? current.currentBox : null;

        String stepMessage = "";
        String loadMessage = "";
        String boxMessage = "";
        if (current != null) {
            if (current.loading) {
                stepMessage = "Loading " + (current.stepNumber + 1) + " of " + current.steps;
            } else {
                stepMessage = "Unloading " + (current.stepNumber + 1) + " of " + current.steps;
            }

            loadMessage = "Load " + (current.loadNumber + 1) + " of " + current.totalLoads;
        }


        if (currentBox != null) {
            boxMessage = currentBox.getBoxMessage();
        }

        theHud.getStepDisplay().setMessage(stepMessage);
        theHud.getLoadDisplay().setMessage(loadMessage);
        theHud.getBoxDisplay().setMessage(boxMessage);
    }


    private enum LoadPlanDisplayerState {
        Initial,
        CalculateDisplayPlan,
        Start,
        AnimatingBoxes,
        ZoomIn,
        ZoomingIn,
        Resetting,
        Done,
        BoxNotFound,
        BoxStaged
    }


}
