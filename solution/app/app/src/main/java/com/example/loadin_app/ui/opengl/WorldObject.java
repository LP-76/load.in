package com.example.loadin_app.ui.opengl;

import android.opengl.Matrix;

import com.example.loadin_app.ui.opengl.programs.IMoveable;
import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;

import java.time.Duration;


/**
 * An object that can be displayed in the world
 */
public abstract class  WorldObject implements  IDrawable, IPlaceable, IMoveable {

    private Vector offset;

    @Override
    public Vector getWorldOffset() {
        return offset;
    }

    protected World myWorld;
    private boolean visible;



    public  WorldObject(){
        offset = new Vector(0,0,0);
        visible = true;
    }

    public World getMyWorld() {
        return myWorld;
    }

    public void setMyWorld(World myWorld) {
        this.myWorld = myWorld;
        myWorld.addObject(this);
        recalculateShapes();
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


    protected abstract void recalculateShapes();



}
