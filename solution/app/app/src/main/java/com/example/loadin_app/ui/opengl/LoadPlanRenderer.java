package com.example.loadin_app.ui.opengl;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.loadin_app.Load;
import com.example.loadin_app.LoadPlan;
import com.example.loadin_app.TestOpenGLActivity;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.extensions.IExtendedIterator;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

import javax.microedition.khronos.opengles.GL10;

/*
In this class, we want to be able to display the load plan
 */
public class LoadPlanRenderer extends BaseGLRenderer {

    private LoadPlan theLoadPlan;
    private IExtendedIterator<Load> loadIterator;
    private IExtendedIterator<Box> boxIterator;
    private Object signalLock = new Object();


    private Truck theTruck;
    private Vector boxStagingArea;
    private Box lastRenderCheckBox;

    private SignalState signal;
    private LoadPlanDisplayerState state;

    public LoadPlanRenderer(Bitmap source, Context ctx, LoadPlanBoxServiceImpl boxService) {
        super(source, ctx, boxService);

       // testBitmap = source;
        state = LoadPlanDisplayerState.Initial;
        signal = SignalState.None;

    }

    @Override
    protected void adjustCameraPlacement() {
        if(boxIterator.current() != null){

            Vector boxCenter = boxIterator.current().getCenter();
            Vector pointOfView = boxCenter.add(new Vector(-2f*12, 0f, -6f*12f));
            pointOfView.setY(6f*12f); //fixed at eye height

            theCamera.placeCamera(pointOfView);
            theCamera.lookAt(boxCenter);  //always look at the current box
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        super.onSurfaceChanged(unused, width, height);
        try
        {
            theLoadPlan = new LoadPlan(boxService.getLoadPlan(TestOpenGLActivity.sp.getInt("loginID",0)));
        }
        catch (ExecutionException e)
        {
            //e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            //e.printStackTrace();
        }

        loadIterator = theLoadPlan.iterator();
        loadIterator.next(); //advance to the first load
        boxIterator = loadIterator.current().iterator();  //get the box iterator

        theTruck =theLoadPlan.GetTruck();
        theTruck.setMyWorld(theWorld);


        //theCamera.placeCamera(new Vector(-3f*12f, 8f*12f, -3f*12f));



        boxStagingArea = new Vector(0f, 0f, 0f);

        advance();
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



    public void fastForward(){
        animationSeconds = 1;
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.FastForward);
                break;
        }
    }
    public void fastRewind(){
        animationSeconds = 1;
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.FastReverse);
                break;
        }
    }

    public void reverse(){
        animationSeconds = 2;
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.Reverse);
                break;
        }
    }

    public void advance(){
        animationSeconds = 2;
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.Forward);
                break;
        }

    }

    private void updateState(){

        switch(state){
            case Initial:
                if(boxIterator.hasNext())
                    state = LoadPlanDisplayerState.Advancing;
                else
                    state = LoadPlanDisplayerState.EndOfLoadPlan;

                break;
            case Advancing:
                if(boxIterator.hasNext()){
                    putNextBoxIntoStaging();
                    state = LoadPlanDisplayerState.BoxStaged;
                }else if(loadIterator.hasNext()){
                    transitionToNextLoadPlan();
                    state = LoadPlanDisplayerState.Initial;
                    setSignal(SignalState.None); //always clear signal
                }
                else
                    state = LoadPlanDisplayerState.EndOfLoadPlan;

                if(getSignal() != SignalState.FastForward)
                    setSignal(SignalState.None);
                break;
            case Reversing:
                if(boxIterator.hasPrevious()){
                    boxIterator.current().setVisible(false);
                    boxIterator.previous();
                    state = LoadPlanDisplayerState.BoxAtDestination;

                }else if(loadIterator.hasPrevious()){
                    transitionToPreviousLoadPlan();
                    setSignal(SignalState.None);
                    state = LoadPlanDisplayerState.BoxAtDestination;
                }else {
                    state = LoadPlanDisplayerState.BoxStaged;
                    setSignal(SignalState.None);
                }

                if(getSignal() != SignalState.FastReverse)
                    setSignal(SignalState.None);
                break;

            case BoxStaged:
                switch (getSignal()){
                    case FastForward:
                    case Forward:
                        //we've got the green light to go ahead
                        //objective is to move it to destination
                        Box current = boxIterator.current();
                        Vector destination = theTruck.getWorldOffset().add(current.getDestination());
                        animateBox(current,  destination, getSignal() == SignalState.Forward , LoadPlanDisplayerState.BoxAtDestination);
                        break;
                    case FastReverse:
                    case Reverse:
                        state = LoadPlanDisplayerState.Reversing;

                        break;
                }
                break;
            case BoxAtDestination:
                //we're at the destination... now we wait for a signal

                switch(getSignal()){
                    case FastForward:
                    case Forward:
                        state = LoadPlanDisplayerState.Advancing;
                        break;
                    case FastReverse:
                    case Reverse:
                        animateBox(boxIterator.current(), boxStagingArea, getSignal() == SignalState.Reverse, LoadPlanDisplayerState.BoxStaged);
                        break;
                }

                break;

            case EndOfLoadPlan:
                switch(getSignal()){  //we can only go back
                    case FastReverse:
                    case Reverse:
                        animateBox(boxIterator.current(), boxStagingArea, getSignal() == SignalState.Reverse, LoadPlanDisplayerState.BoxStaged);
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
                Duration.ofSeconds(animationSeconds),
                theWorld.getTick(),
                to,
                i -> {
                    if(clearSignal)
                        setSignal(SignalState.None);  //only clear the signal for single step back
                    state =resultingState;
                }
        ) ;
        theWorld.addAnimation(animation); //add this so it gets processed
    }

    private void setAllBoxesVisibility(boolean visible){
        for(IExtendedIterator<Box> i = loadIterator.current().iterator(); i.hasNext();)
            i.next().setVisible(visible);  //toggle all boxes to be hidden
    }

    private void getPreviousBox(){
        boxIterator.previous(); //we will move null into current if we can't reverse
    }

    private  void putNextBoxIntoStaging(){


        Box currentBox = boxIterator.next();
        if(currentBox != null){
            if(currentBox.getMyWorld() == null)
            {
                currentBox.setMyWorld(theWorld);
            }
            currentBox.place(boxStagingArea);
            currentBox.setVisible(true);
        }

    }


    private void transitionToNextLoadPlan(){

        setAllBoxesVisibility(false); //hide all current boxes
        loadIterator.next();
        boxIterator = loadIterator.current().iterator();

    }


    private void transitionToPreviousLoadPlan(){

        setAllBoxesVisibility(false); //hide all current boxes
        loadIterator.previous();
        boxIterator = loadIterator.current().iterator();
        boxIterator.goToEnd();
        setAllBoxesVisibility(true);

    }

    @Override
    protected void renderWorld() {
        updateState(); //update state of load plan before render
        super.renderWorld();
    }

    @Override
    protected void renderHud() {
        if(boxIterator.current() != lastRenderCheckBox) //don't re-render textures unless we detect a change
            onBoxChanged();
        lastRenderCheckBox = boxIterator.current();
        //then render the HUD
        super.renderHud();
    }

    private void onBoxChanged(){
        String stepMessage = "";
        String loadMessage = "";
        String boxMessage = "";
        if(theLoadPlan != null){
            //TODO: alter load plan to give this information
            stepMessage = "Step " + (boxIterator.currentPosition() + 1) + " of " + boxIterator.size();
            loadMessage = "Load " + (loadIterator.currentPosition() + 1) + " of " + loadIterator.size();
        }
        Box currentBox = boxIterator.current();

        if(currentBox != null){
            boxMessage = "Box #" + currentBox.getBoxId() + "\n"+
                    "Contents:\n"+
                    currentBox.getDescription() + "\n" +
                    currentBox.getDestination().toString() + "\n"+
                    currentBox.getWidth() + " x " + currentBox.getHeight() + " x " + currentBox.getLength();
        }

        theHud.getStepDisplay().setMessage(stepMessage);
        theHud.getLoadDisplay().setMessage(loadMessage);
        theHud.getBoxDisplay().setMessage(boxMessage);
    }


    private enum LoadPlanDisplayerState{
        Initial,
        BoxStaged,
        BoxAnimating,
        BoxAtDestination,
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
