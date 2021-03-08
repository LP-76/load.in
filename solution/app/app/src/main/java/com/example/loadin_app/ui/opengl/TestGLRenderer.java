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
import com.example.loadin_app.TestingLoadPlanGenerator;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
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

    private enum LoadPlanDisplayerState{
        Stopped,
        Advancing,
        Reversing,
        FastFoward,
        FastRewind,
        BoxStaged,
        BoxAnimating,
        BoxAtDestination
    }

    private enum SignalState{
        None,
        Advancing
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

    private Vector boxStagingArea;

    private Box currentBox;
    private Box lastRenderCheckBox;


    private Context context;

    private Object signalLock = new Object();



    public TestGLRenderer(Bitmap source, Context ctx){
        testBitmap = source;
        state = LoadPlanDisplayerState.Stopped;
        signal = SignalState.None;
        context = ctx;

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

   public void reverse(){
       //setSignal(SignalState.Advancing.Reversing);
   }

    public void advance(){
        switch(getSignal()){
            case None:   //we can only raise the flag once until the system is ready to respond to another request
                setSignal(SignalState.Advancing);
                break;
        }

    }

    private void updateState(){

        switch(state){
            case Stopped:
                switch(getSignal()){
                    case Advancing:
                        //we want to advance to the next box
                        state = LoadPlanDisplayerState.Advancing; //go ahead and move to next state
                        updateState();
                        break;
                }

                break;
            case Advancing:
                putNextBoxIntoStaging(false);
                setSignal(SignalState.None); //we're ready for the next signal
                state = LoadPlanDisplayerState.BoxStaged;

                break;
            case BoxStaged:
                switch (getSignal()){
                    case Advancing:
                        //we've got the green light to go ahead
                        state = LoadPlanDisplayerState.BoxAnimating;
                       TransposeAnimation animation = new TransposeAnimation(
                                currentBox,
                                Duration.ofSeconds(2),
                                theWorld.getTick(),
                                currentBox.getDestination(),
                                i -> {
                                    setSignal(SignalState.None);
                                    state = LoadPlanDisplayerState.BoxAtDestination;
                                }
                        ) ;
                        theWorld.addAnimation(animation); //add this so it gets processed
                        break;
                }
                break;
            case BoxAtDestination:
                //we're at the destination... now we wait for a signal
                switch(getSignal()){
                    case Advancing:
                        //we want to advance to the next box
                        state = LoadPlanDisplayerState.Advancing; //go ahead and move to next state
                        updateState();
                        break;
                }

                break;

        }


    }




    private  void putNextBoxIntoStaging(boolean useCurrentBox){
       if(theLoadPlan.GetCurrentLoad() != null){
           Load current = theLoadPlan.GetCurrentLoad();

           if(useCurrentBox && current.GetCurrentBox() != null){
               currentBox = current.GetCurrentBox();
           }
           else if(current.HasNextBox()){
               currentBox = current.GetNextBox();

           }

       }

       if(currentBox != null){
           if(currentBox.getMyWorld() == null)
           {
               currentBox.setMyWorld(theWorld);
           }
           currentBox.place(boxStagingArea);
           currentBox.setVisible(true);
       }
    }



    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);




        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        theWorld = new World(context);
        theHud = new Hud(theWorld); //setup the hud
        theCamera = new Camera();

        //theLoadPlan = TestingLoadPlanGenerator.GenerateBasicSampleLoadPlan(theWorld);

        theLoadPlan = new LoadPlanGenerator().StartLoadPlan();
        theLoadPlan.GetTruck().setMyWorld(theWorld);
        Vector test = theLoadPlan.GetCurrentLoad().GetCurrentBox().getDestination();
        //theCamera.placeCamera(new Vector(-3f*12f, 8f*12f, -3f*12f));



        boxStagingArea = new Vector(0f, 0f, 0f);

        putNextBoxIntoStaging(true);
        state = LoadPlanDisplayerState.BoxStaged;

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

        if(currentBox != null){

            Vector boxCenter = currentBox.getCenter();
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
        String stepMessage = "Step 1 of 10000";
        String loadMessage = "Load 1 of 10";
        String boxMessage = null;
        if(currentBox != null){
            boxMessage = "Box #" + currentBox.getBoxId() + "\n"+
                    "Contents:\n"+
                    "Fine China and other dishware\n"+  //TODO: get box description
                    currentBox.getDestination().toString() + "\n"+
                    currentBox.getWidth() + " x " + currentBox.getHeight() + " x " + currentBox.getLength();
        }else{
            boxMessage = "";
        }

        theHud.getStepDisplay().setMessage(stepMessage);
        theHud.getLoadDisplay().setMessage(loadMessage);
        theHud.getBoxDisplay().setMessage(boxMessage);
    }

    private void renderHud(){
        if(currentBox != lastRenderCheckBox) //don't re-render textures unless we detect a change
            onBoxChanged();
        lastRenderCheckBox = currentBox;

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
