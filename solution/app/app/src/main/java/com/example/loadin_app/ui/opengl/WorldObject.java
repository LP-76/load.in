package com.example.loadin_app.ui.opengl;

import android.opengl.Matrix;

import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;

import java.time.Duration;


/**
 * An object that can be displayed in the world
 */
public abstract class  WorldObject implements  IDrawable, IPlaceable {

    private Vector offset;

    @Override
    public Vector getWorldOffset() {
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


}
