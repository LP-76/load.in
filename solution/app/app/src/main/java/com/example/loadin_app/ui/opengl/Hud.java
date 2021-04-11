package com.example.loadin_app.ui.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.text.Layout;

import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import java.util.ArrayList;

public class Hud implements IDrawable, IPlaceable {


    Vector worldOffset;
    private World myWorld;
    private IDrawable[] elements;
    private HudElement stepDisplay;
    private HudElement loadDisplay;
    private HudElement boxDisplay;

    private NavigationButton leftButton;
    private NavigationButton rightButton;

    Vector upperLeftScreenCorner;
    Vector upperRightScreenCorner;
    Vector lowerLeftScreenCorner;
    Vector lowerRightScreenCorner;

    float screenWidth;
    float screenHeight;

    boolean displayButtons;

    public Hud(World world) {
        this(world,true);

    }
    public Hud(World world, boolean displayButtons){
        myWorld = world;
        this.displayButtons = displayButtons;
        worldOffset = new Vector(0f, 0f, 0f); //i'll put myself in the center
        upperLeftScreenCorner = new Vector(0,0,0);
        upperRightScreenCorner = new Vector(0,0,0);
        lowerLeftScreenCorner = new Vector(0,0,0);
        lowerRightScreenCorner = new Vector(0,0,0);

        screenWidth = 1f;
        setupHudElements();
    }

    public Vector getUpperLeftScreenCorner() {
        return upperLeftScreenCorner;
    }

    public void setUpperLeftScreenCorner(Vector upperLeftScreenCorner) {

        screenWidth = upperLeftScreenCorner.getX() * 2f;
        screenHeight = upperLeftScreenCorner.getY() * 2f;
        updateCorners(upperLeftScreenCorner);
        refreshHudElementPositions();


    }

    private void refreshHudElementPositions(){
        //step display is upper left corner with half screen
        stepDisplay.setWidth(screenWidth/2f);
        stepDisplay.move(upperLeftScreenCorner.add(stepDisplay.getScale().multiply(-1f)));

        //load display is upper right corner with half screen
        loadDisplay.setWidth(screenWidth/2f);
        loadDisplay.move(upperRightScreenCorner.add(new Vector(0f, -loadDisplay.getScale().getY(), 0f)));

        //box display is at the bottom right corner and spans the entire corner
        boxDisplay.setWidth(screenWidth);
        boxDisplay.move(lowerRightScreenCorner);

        leftButton.move(new Vector(screenWidth/2f - leftButton.getScale().getX(), 0f, 0f));
        rightButton.move(new Vector(-screenWidth/2f + leftButton.getScale().getX(), 0f, 0f));



    }

    private void updateCorners(Vector upperLeftScreenCorner){

        this.upperLeftScreenCorner = upperLeftScreenCorner;
        upperRightScreenCorner =upperLeftScreenCorner.add(new Vector(-screenWidth, 0f, 0f));
        lowerRightScreenCorner = upperRightScreenCorner.add(new Vector(0f, -screenHeight, 0f));
        lowerLeftScreenCorner = upperLeftScreenCorner.add(new Vector(0f, -screenHeight, 0f));

    }


    public void setupHudElements(){
        stepDisplay = new HudElement(myWorld, this);
        //we want to upper right corner to be equal to the upper left screen corner

        loadDisplay = new HudElement(myWorld, this);
        boxDisplay = new HudElement(myWorld, this);
        boxDisplay.setTextAlign(Layout.Alignment.ALIGN_NORMAL);
        boxDisplay.setTextSize(24);

        leftButton = new NavigationButton(myWorld, this);
        float scale = 1f/12f;
        leftButton.setScale(new Vector(scale, scale, scale));

        rightButton = new NavigationButton(myWorld, this);
        rightButton.setScale(new Vector(scale, scale, scale));
        rightButton.setYaw(180f);

        if(displayButtons)
            elements = new IDrawable[]{
          stepDisplay,
                loadDisplay,
                boxDisplay,
                leftButton,
                rightButton
        };
        else
            elements = new IDrawable[]{
                    stepDisplay,
                    loadDisplay,
                    boxDisplay
            };

    }

    public HudElement getBoxDisplay() {
        return boxDisplay;
    }

    public HudElement getStepDisplay() {
        return stepDisplay;
    }

    public HudElement getLoadDisplay() {
        return loadDisplay;
    }

    public boolean isDisplayButtons() {
        return displayButtons;
    }

    public void setDisplayButtons(boolean displayButtons) {
        this.displayButtons = displayButtons;
        setupHudElements();
    }

    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        for(IDrawable e: elements)
            e.draw(worldContext, view, projection);
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
