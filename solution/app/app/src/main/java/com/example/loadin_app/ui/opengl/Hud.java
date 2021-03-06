package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

public class Hud implements IDrawable, IPlaceable {


    Vector worldOffset;
    private World myWorld;
    private HudElement test;

    public Hud(World world) {
        myWorld = world;

        test = new HudElement(world, this);
        worldOffset = new Vector(-1f, -1f, 0f); //lower left corner
        test.move(new Vector(0f, 0f, 0));

    }


    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        test.draw(worldContext, view, projection);
    }

    @Override
    public OpenGLVariableHolder getPositions() {
        return null;
    }

    @Override
    public Vector getWorldOffset() {
        return worldOffset;
    }
}
