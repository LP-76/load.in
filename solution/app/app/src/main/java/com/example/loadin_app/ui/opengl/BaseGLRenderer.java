package com.example.loadin_app.ui.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class BaseGLRenderer implements GLSurfaceView.Renderer {
    public static final Color LOAD_IN_GREEN = new Color(109, 209, 161, 1);

    private final float[] orthoMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private  float[] viewMatrix = new float[16];
    private float[] hudViewMatrix = new float[16];
    private  Vector upperLeftScreenCorner;
    int animationMillisecondsSeconds;


    private boolean colorCodeBoxModeOn;

    public Camera getTheCamera(){
        return theCamera;
    }

    protected World theWorld;
    protected Camera theCamera;

    protected Hud theHud;

    protected LoadPlanBoxServiceImpl boxService;

    protected Context context;



    public BaseGLRenderer(Context ctx, LoadPlanBoxServiceImpl boxService, boolean colorCodeBoxModeOn){

        context = ctx;
        animationMillisecondsSeconds = 2 * 1000;
        this.boxService = boxService;
        this.colorCodeBoxModeOn = colorCodeBoxModeOn;
    }


    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);




        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        theWorld = new World(context, colorCodeBoxModeOn);
        theHud = new Hud(theWorld); //setup the hud
        theCamera = new Camera();

        //theLoadPlan = TestingLoadPlanGenerator.GenerateBasicSampleLoadPlan(theWorld);






    }

    protected abstract void adjustCameraPlacement();

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
    protected void renderWorld(){

        for(Animation a : theWorld.getAnimiations().toArray(Animation[]::new)){
            if(!a.isComplete())
                a.performOperationPerTick();
            else{
                theWorld.removeAnimation(a);
            }
        }


        adjustCameraPlacement();

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


    protected void renderHud(){

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
