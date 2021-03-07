package com.example.loadin_app.ui.opengl;

import android.opengl.Matrix;

import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.IRotatable;
import com.example.loadin_app.ui.opengl.programs.IScalable;
import com.example.loadin_app.ui.opengl.programs.ITexturable;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import java.util.Arrays;

public class NavigationButton extends Shape implements ITexturable, IScalable , IRotatable {

    Vector scale;
    TexturedTriangle[] triangles;
    OpenGLVariableHolder positions;
    OpenGLVariableHolder texCoords;

    World myWorld;
    float yaw;
    float pitch;

    public NavigationButton(World theWorld, IPlaceable parent) {
        super(parent);
        scale = new Vector(1,1,1);
        myWorld = theWorld;
        Vector p1 = new Vector(1f,0f,0);
        Vector p2 = new Vector(-1, 1, 0);
        Vector p3 = new Vector(-1, -1, 0);
        yaw = 0;
        pitch = 0;




        Vector middleLeft = new Vector(0, 0.5f, 0);
        Vector upperRight = new Vector(1, 0, 0);
        Vector lowerRight = new Vector(1, 1, 0);

        triangles = new TexturedTriangle[]{
            new TexturedTriangle(p1, p2, p3, middleLeft, upperRight, lowerRight )
        };

        positions = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getCoordinates()), 3
        );
        texCoords = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getTextureCoordinates()), 2
        );


    }

    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        worldContext.getHudProgram().render(this, view, projection);
    }

    @Override
    public OpenGLVariableHolder getPositions() {
        return positions;
    }

    @Override
    public Texture getTexture() {
        return myWorld.getHudProgram().getStainlessSteel();
    }

    @Override
    public OpenGLVariableHolder getTextureCoordiantes() {
        return texCoords;
    }

    public void setScale(Vector scale){
        this.scale = scale;
    }

    @Override
    public Vector getScale() {
        return scale;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
