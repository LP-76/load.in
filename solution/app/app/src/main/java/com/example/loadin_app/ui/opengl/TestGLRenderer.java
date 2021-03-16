package com.example.loadin_app.ui.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.loadin_app.Load;
import com.example.loadin_app.LoadPlan;
import com.example.loadin_app.LoadPlanGenerator;
import com.example.loadin_app.TestOpenGLActivity;
import com.example.loadin_app.TestingLoadPlanGenerator;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.extensions.IExtendedIterator;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TestGLRenderer implements GLSurfaceView.Renderer {
    public static final Color LOAD_IN_GREEN = new Color(109, 209, 161, 1);

    private final float[] orthoMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private  float[] viewMatrix = new float[16];
    private float[] hudViewMatrix = new float[16];
    private  Vector upperLeftScreenCorner;
    int animationSeconds;

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

    private SignalState signal;
    private LoadPlanDisplayerState state;


    public Camera getTheCamera(){
        return theCamera;
    }

    private World theWorld;
    private Camera theCamera;
    private Bitmap testBitmap;
    private Hud theHud;

    private LoadPlan theLoadPlan;
    private IExtendedIterator<Load> loadIterator;
    private IExtendedIterator<Box> boxIterator;

    private Truck theTruck;

    private Vector boxStagingArea;


    private Box lastRenderCheckBox;


    private Context context;

    private Object signalLock = new Object();



    public TestGLRenderer(Bitmap source, Context ctx){
        testBitmap = source;
        state = LoadPlanDisplayerState.Initial;
        signal = SignalState.None;
        context = ctx;
        animationSeconds = 2;
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

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);




        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        theWorld = new World(context);
        theHud = new Hud(theWorld); //setup the hud
        theCamera = new Camera();

        //theLoadPlan = TestingLoadPlanGenerator.GenerateBasicSampleLoadPlan(theWorld);

        LoadPlanBoxServiceImpl service = new LoadPlanBoxServiceImpl("http://10.0.2.2:9000/");

        try
        {
            theLoadPlan = new LoadPlan(service.getLoadPlan(TestOpenGLActivity.sp.getInt("loginID",0)));
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

    public void onDrawFrame(GL10 unused) {

        theWorld.updateTicks();

        prepareWorld();
        renderWorld();

        prepareHud();
        renderHud();




    }
    private void prepareWorld(){
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);  //depth test enabled
        GLES20.glDisable(GLES20.GL_BLEND);

    }
    private void prepareHud(){

        GLES20.glDisable(GLES20.GL_DEPTH_TEST);  //we don't depth test for hud
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_BLEND);
        //GLES20.glBlendColor(0f, 0f, 0f, 1f);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

    }
    private void renderWorld(){
        updateState(); //we need to update and check on the state of the world
        for(Animation a : theWorld.getAnimiations().toArray(Animation[]::new)){
            if(!a.isComplete())
                a.performOperationPerTick();
            else{
                theWorld.removeAnimation(a);
            }
        }

        if(boxIterator.current() != null){

            Vector boxCenter = boxIterator.current().getCenter();
            Vector pointOfView = boxCenter.add(new Vector(-3f*12, 2f*12f, -3f*12f));

            theCamera.placeCamera(pointOfView);
            theCamera.lookAt(boxCenter);  //always look at the current box
        }




        // Redraw background color
        GLES20.glClearColor(109f/255f, 209f/255f, 161f/255f, 1f);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);


        viewMatrix = theCamera.getLookatMatrix();


        for(WorldObject wo: theWorld.getWorldObjects()){
            if(wo.isVisible())
                wo.draw(theWorld, viewMatrix, projectionMatrix);
        }
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

    private void renderHud(){
        if(boxIterator.current() != lastRenderCheckBox) //don't re-render textures unless we detect a change
            onBoxChanged();
        lastRenderCheckBox = boxIterator.current();

        theHud.setUpperLeftScreenCorner(upperLeftScreenCorner);
        theHud.draw(theWorld, hudViewMatrix, orthoMatrix);


    }


    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, 100f);

        Matrix.orthoM(orthoMatrix, 0, -ratio, ratio, -1f, 1f, 0.1f, 100f );  //close field of vision

        Matrix.setLookAtM(hudViewMatrix,0, 0f, 0f, -0.2f, 0f, 0f, 0f, 0f, 1f, 0f );

        upperLeftScreenCorner = new Vector(ratio, 1f, 0f);

    }
}
