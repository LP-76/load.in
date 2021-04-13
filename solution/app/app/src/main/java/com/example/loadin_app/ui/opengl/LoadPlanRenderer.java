package com.example.loadin_app.ui.opengl;

import android.content.Context;

import com.example.loadin_app.LoadPlan;
import com.example.loadin_app.LoadPlanNavigatorActivity;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

import javax.microedition.khronos.opengles.GL10;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.LoadPlanBox;

/*
In this class, we want to be able to display the load plan
 */
public class LoadPlanRenderer extends BaseGLRenderer {

    private LoadPlan theLoadPlan;
    //private IExtendedIterator<Load> loadIterator;
    //private IExtendedIterator<Box> boxIterator;

    private LoadPlanStepIterator lpiter;

    private Object signalLock = new Object();


    private Truck theTruck;
    private Vector boxStagingArea;
    private LoadPlanStep lastRenderCheckStep;


    private SignalState signal;
    private LoadPlanDisplayerState state;
    private InventoryServiceImpl inventoryService;




    public LoadPlanRenderer( Context ctx, LoadPlanBoxServiceImpl boxService, InventoryServiceImpl inventoryService, boolean colorCodeBoxModeOn) {
        super( ctx, boxService, colorCodeBoxModeOn);
        this.inventoryService = inventoryService;
       // testBitmap = source;
        state = LoadPlanDisplayerState.Initial;
        signal = SignalState.None;

    }

    @Override
    protected void adjustCameraPlacement() {
        if(lpiter.current() != null){
            LoadPlanStep currentStep = lpiter.current();
            Vector boxCenter = currentStep.currentBox.getCenter();
            Vector pointOfView = boxCenter.add(new Vector(-2f*12, 0f, -6f*12f));
            pointOfView.setY(6f*12f); //fixed at eye height

            theCamera.place(pointOfView);
            theCamera.lookAt(boxCenter);  //always look at the current box
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        super.onSurfaceChanged(unused, width, height);
        try
        {
            theLoadPlan = new LoadPlan(boxService.getLoadPlan(LoadPlanNavigatorActivity.sp.getInt("loginID",0)));
        }
        catch (ExecutionException e)
        {
            //e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            //e.printStackTrace();
        }

        lpiter = new LoadPlanStepIterator(theLoadPlan);
//        loadIterator = theLoadPlan.iterator();
//        loadIterator.next(); //advance to the first load
//        boxIterator = loadIterator.current().iterator();  //get the box iterator

        theTruck =theLoadPlan.GetTruck();
        theTruck.setMyWorld(theWorld);


        //theCamera.placeCamera(new Vector(-3f*12f, 8f*12f, -3f*12f));



        boxStagingArea = new Vector(0f, 0f, 0f);


    }

    private  SignalState getSignal() {
        synchronized (signalLock){
            return signal;
        }
    }

    private  void setSignal(SignalState signal) {
        synchronized (signalLock){
            this.signal = signal;
        }
    }



    public void requestFastForward(){
        animationMillisecondsSeconds = 500;
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.FastForward);
                break;
        }
    }
    public void requestFastRewind(){
        animationMillisecondsSeconds = 500;
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.FastReverse);
                break;
        }
    }

    public void requestReverse(){
        animationMillisecondsSeconds = 500;
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.Reverse);
                break;
        }
    }

    public void requestAdvance(){
        animationMillisecondsSeconds = 500;
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.Forward);
                break;
        }

    }
    private boolean shouldSkipStep(LoadPlanStep step){
        Box current = step.currentBox;
        if(step.loading){
            switch(current.getStatus()){
                case Inventory.ON_TRUCK:
                case Inventory.AT_DESTINATION:
                    return true;

            }

        }else{
            switch(current.getStatus()){
                case Inventory.AT_DESTINATION:
                    return true;

            }
        }
        return false;
    }

    private void updateState(){
        LoadPlanStep currentStep = lpiter.current();
        Box current = currentStep != null ? currentStep.currentBox : null;
        if(current != null && current.getMyWorld() == null){
            current.setMyWorld(theWorld);
        }
        switch(state){
            case Initial:

                if(lpiter.hasNext()) {

                    while (lpiter.hasNext() && shouldSkipStep(lpiter.next())) {
                        Box b = lpiter.current().currentBox;
                        if(b.getMyWorld() == null)
                            b.setMyWorld(theWorld);

                        switch (b.getStatus()) {
                            case Inventory.AT_DESTINATION:
                                b.setVisible(false);
                                b.place(boxStagingArea);
                                break;
                            case Inventory.ON_TRUCK:
                                b.setVisible(true);
                                Vector destination = theTruck.getWorldOffset().add(b.getDestination());
                                b.place(destination);
                                break;

                        }
                    }

                    Box b = lpiter.current().currentBox;
                    if(b.getMyWorld() == null)
                        b.setMyWorld(theWorld);
                    b.setVisible(true);
                    switch(b.getStatus()){
                        case Inventory.ON_TRUCK:
                            Vector destination = theTruck.getWorldOffset().add(b.getDestination());
                            b.place(destination);
                            state = LoadPlanDisplayerState.BoxOnTruck;
                            break;
                        case Inventory.AT_SOURCE:
                        case Inventory.AT_DESTINATION:
                            b.place(boxStagingArea);
                            state = LoadPlanDisplayerState.BoxStaged;
                            break;
                    }

                }
                else
                    state = LoadPlanDisplayerState.EndOfLoadPlan;

                break;
            case Advancing:

                if(lpiter.hasNext()){
                    Box previous = current;
                    LoadPlanStep previousStep = currentStep;
                    currentStep = lpiter.next();
                    current = currentStep != null ? currentStep.currentBox : null;
                    current.setVisible(true);

                    if(previousStep != null && previousStep.loading != currentStep.loading && getSignal() == SignalState.FastForward){
                        setSignal(SignalState.None); //clear it on transitioning between loads
                    }


                    if(currentStep.loading){
                        //if we are loading, we want to skip any box that is already at destination




                        if(previousStep != null && currentStep.loading != previousStep.loading)
                            previous.setVisible(false); //hide last member of previous unloading

                        state = LoadPlanDisplayerState.BoxStaged;
                    }else{


                        if(previous != current)
                            previous.setVisible(false);
                        state = LoadPlanDisplayerState.BoxOnTruck;
                    }


                }else{
                    state = LoadPlanDisplayerState.EndOfLoadPlan;
                    setSignal(SignalState.None);
                }

                if(getSignal() != SignalState.FastForward)
                    setSignal(SignalState.None);
                break;
            case Reversing:

                if(lpiter.hasPrevious()){
                    Box previous = current;
                    LoadPlanStep previousStep = currentStep;
                    currentStep = lpiter.previous();
                    current = currentStep != null ? currentStep.currentBox : null;
                    current.setVisible(true);

                    if(previousStep != null && previousStep.loading != currentStep.loading && getSignal() == SignalState.FastReverse){
                        setSignal(SignalState.None); //clear it on transitioning between loads
                    }

                    if(currentStep.loading){
                        if(previous != current)
                            previous.setVisible(false);
                        state = LoadPlanDisplayerState.BoxOnTruck;
                    }else{
                        if(previousStep != null && currentStep.loading != previousStep.loading)
                            previous.setVisible(false); //hide last member of previous unloading

                        state = LoadPlanDisplayerState.BoxStaged;

                    }


                }else{
                    state = LoadPlanDisplayerState.BoxStaged;
                    setSignal(SignalState.None);
                }

                if(getSignal() != SignalState.FastReverse)
                    setSignal(SignalState.None);

                break;

            case BoxStaged:


                if(currentStep.loading){
                    switch (getSignal()){
                        case FastForward:
                        case Forward:
                            //we've got the green light to go ahead
                            //objective is to move it to destination
                            Vector destination = theTruck.getWorldOffset().add(current.getDestination());
                            animateBox(current,  destination, getSignal() == SignalState.Forward , LoadPlanDisplayerState.BoxOnTruck);
                            break;
                        case FastReverse:
                        case Reverse:
                            state = LoadPlanDisplayerState.Reversing;

                            break;
                    }
                }else{
                    switch (getSignal()){
                        case FastForward:
                        case Forward:
                            state = LoadPlanDisplayerState.Advancing;
                            break;
                        case FastReverse:
                        case Reverse:
                            //we've got the green light to go ahead
                            //objective is to move it to destination

                            Vector destination = theTruck.getWorldOffset().add(current.getDestination());
                            animateBox(current,  destination, getSignal() == SignalState.Reverse , LoadPlanDisplayerState.BoxOnTruck);


                            break;
                    }
                }

                break;
            case BoxOnTruck:
                //we're at the destination... now we wait for a signal

                if(currentStep.loading){
                    switch(getSignal()){
                        case FastForward:
                        case Forward:
                            state = LoadPlanDisplayerState.Advancing;
                            break;
                        case FastReverse:
                        case Reverse:
                            animateBox(current, boxStagingArea, getSignal() == SignalState.Reverse, LoadPlanDisplayerState.BoxStaged);
                            break;
                    }

                }else{
                    switch(getSignal()){
                        case FastForward:
                        case Forward:
                            //if we're unloading we want the box to travel from the truck to the staging area
                            animateBox(current, boxStagingArea, getSignal() == SignalState.Forward, LoadPlanDisplayerState.BoxStaged);
                            break;
                        case FastReverse:
                        case Reverse:

                            state = LoadPlanDisplayerState.Reversing;

                            break;
                    }
                }


                break;

            case EndOfLoadPlan:

                switch(getSignal()){  //we can only go back
                    case FastReverse:
                    case Reverse:


                        Vector destination = theTruck.getWorldOffset().add(current.getDestination());
                        animateBox(current,  destination, getSignal() == SignalState.Reverse , LoadPlanDisplayerState.BoxOnTruck);

                        break;
                    default:
                        setSignal(SignalState.None);
                        break;
                }

                break;


        }


    }



    private void animateBox(Box target, Vector to, boolean clearSignal, LoadPlanDisplayerState resultingState){
        state = LoadPlanDisplayerState.BoxAnimating;
        TransposeAnimation animation = new TransposeAnimation(
                target,
                Duration.ofMillis(animationMillisecondsSeconds),
                theWorld.getTick(),
                to,
                i -> {
                    if(clearSignal)
                        setSignal(SignalState.None);  //only clear the signal for single step back
                    state =resultingState;

                    LoadPlanBox lpb = target.toLoadPlanBox();
                    LoadPlanStep current = lpiter.current();

                    switch (state){
                        case BoxOnTruck:
                            lpb.getBox().setStatus(Inventory.ON_TRUCK);
                            break;
                        case BoxStaged:
                            if(current.loading){
                                lpb.getBox().setStatus(Inventory.AT_SOURCE);
                            }else{
                                lpb.getBox().setStatus(Inventory.AT_DESTINATION);
                            }
                            break;
                    }

                    try {
                        inventoryService.editInventory(lpb.getBox());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        ) ;
        theWorld.addAnimation(animation); //add this so it gets processed
    }







    @Override
    protected void renderWorld() {
        updateState(); //update state of load plan before render
        super.renderWorld();
    }

    @Override
    protected void renderHud() {
        LoadPlanStep current = lpiter.current();


        if(current != lastRenderCheckStep) //don't re-render textures unless we detect a change
            onStepChanged();
        lastRenderCheckStep =  current;
        //then render the HUD
        super.renderHud();
    }

    private void onStepChanged(){
        LoadPlanStep current = lpiter.current();
        Box currentBox = current != null ? current.currentBox : null;

        String stepMessage = "";
        String loadMessage = "";
        String boxMessage = "";
        if(theLoadPlan != null && current != null){
            if(current.loading){
                stepMessage = "Loading " + (current.stepNumber + 1) + " of " + current.steps;
            }else{
                stepMessage = "Unloading " + (current.stepNumber + 1) + " of " + current.steps;
            }

            loadMessage = "Load " + (current.loadNumber + 1) + " of " + current.totalLoads;
        }


        if(currentBox != null){
            boxMessage = currentBox.getBoxMessage();
        }

        theHud.getStepDisplay().setMessage(stepMessage);
        theHud.getLoadDisplay().setMessage(loadMessage);
        theHud.getBoxDisplay().setMessage(boxMessage);
    }





    private enum LoadPlanDisplayerState{
        Initial,
        BoxStaged,
        BoxAnimating,
        BoxOnTruck,
        Advancing,
        Reversing,
        EndOfLoadPlan
    }

    private enum SignalState{
        None,
        Forward,
        Reverse,
        FastForward,
        FastReverse
    }
}
