package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;



/**
 * An object that can be displayed in the world
 */
public abstract class  WorldObject implements  IBaseDrawable {

    private Vector offset;


    public Vector getOffset() {
        return offset;
    }



    protected World myWorld;
    private boolean visible;



    public  WorldObject(World world){
        offset = new Vector(0,0,0);
        myWorld = world;
        myWorld.addObject(this);
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void place(Vector offset){
        this.offset  = offset;
    }



    public void moveToLocationOverDuration(Duration timeframe,  Vector newLocation){
        myWorld.addAnimation( new TransposeAnimation(this, timeframe, myWorld.getTick(), newLocation));
    }





    protected float[] processTranslation(float[] mvpMatrix){

        //apply translations here

        float[] translationMatrix = new float[16];

        //set the identity matrix for translation
        Matrix.setIdentityM(translationMatrix, 0);



        //add the translation to the matrix
        Matrix.translateM(translationMatrix,0, offset.getX(), offset.getY(), offset.getZ() );

        float[] postTranslationMatrix = new float[16];
        //apply the translation to the objects matrix translation
        Matrix.multiplyMM(postTranslationMatrix, 0, mvpMatrix, 0, translationMatrix, 0);
        return postTranslationMatrix;
    }

    protected float[] processScale(){
        float[] scaleMatrix = new float[16];

        //set the identity matrix for translation
        Matrix.setIdentityM(scaleMatrix, 0);

        //add the translation to the matrix
        Matrix.scaleM(scaleMatrix,0, World.INCHES_TO_WORLD_SCALE,  World.INCHES_TO_WORLD_SCALE,  World.INCHES_TO_WORLD_SCALE );

        return scaleMatrix;
    }

    public abstract OpenGLProgram getMyProgram();

    public abstract void draw(float[] view, float[] projection);


}
