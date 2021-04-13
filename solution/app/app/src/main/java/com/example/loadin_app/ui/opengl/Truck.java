package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import java.util.ArrayList;
import java.util.stream.Stream;

import odu.edu.loadin.common.MovingTruck;

public class Truck extends WorldObject
{
    private final Color FLOOR_COLOR = new Color(161f/255f, 119f/255f,93f/255f, 1f);
    private final Color WALL_COLOR = new Color(206f/255f, 218f/255f,210f/255f, 1f);


    private float lengthInches,widthInches,heightInches;

    private ArrayList<TexturedHexahedron> shapes;

    public float getLengthInches() {
        return lengthInches;
    }

    public float getWidthInches() {
        return widthInches;
    }

    public float getHeightInches() {
        return heightInches;
    }

    public Truck()
    {
        super();
        shapes = new ArrayList<TexturedHexahedron>();
        //UHAUL SAMPLE TRUCK THAT's a 17 footer

        place(new Vector(3f * 12f, 0f, 3f*12f ));
        lengthInches = 14f * 12f ;  //14foot 3 inches
        widthInches = 7f * 12f ;
        heightInches = 7f* 12f ;

    }

    public Truck(MovingTruck fromDb){
        super();
        shapes = new ArrayList<TexturedHexahedron>();

        place(new Vector(3f * 12f, 0f, 3f*12f ));
        lengthInches = fromDb.getLengthInInches();
        widthInches = fromDb.getWidthInInches();
        heightInches = fromDb.getHeightInInches();

    }

    @Override
    protected void recalculateShapes(){
        //we're going to create the truck bed, the outer left wall and the front wall of the truck bed

        float wallThickness = 4f ;  //4 inch walls by default

        float height = heightInches ;
        float width = widthInches ;
        float length = lengthInches ;

        TexturedHexahedron floor = new TexturedHexahedron(
                width, //we'll say approximately 4 inches thick for now
                wallThickness,
                length,
                this
        );
        Texture floorTex = myWorld.getTextureViewProgram().getFloor();


        floor.move(new Vector(0f, -wallThickness, 0f));  //move down below 0,0 line
        floor.setAllSides(floorTex);
        float ratio = (float)floorTex.getSourceWidth() / (float)floorTex.getSourceHeight();
        float scaleInInches = 10f;
        float lengthScale = length /scaleInInches;  //this gives us the number of tiles for height

        floor.scaleFace(floor.getTop(), width / (ratio * scaleInInches), lengthScale );





        TexturedHexahedron leftWall = new TexturedHexahedron(
                wallThickness, //we'll say approximately 4 inches thick for now
                height,
                length,
                this
        );
        leftWall.move(new Vector( width, 0f, 0f));  //move to the left 4 inches
        leftWall.setAllSides(myWorld.getTextureViewProgram().getWall());


        TexturedHexahedron frontWall = new TexturedHexahedron(
                width, //we'll say approximately 4 inches thick for now
                height,
                wallThickness,
                this
        );
        frontWall.move(new Vector(0f, 0f, length));  //move to front of bed
        frontWall.setAllSides(myWorld.getTextureViewProgram().getWall());


        shapes.clear();
        shapes.add(frontWall);
        shapes.add(leftWall);
        shapes.add(floor);


    }


    public float GetVolumeOfTruckInches()
    {
        return lengthInches * widthInches * heightInches;
    }

    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        for(Shape s: shapes)
            s.draw(worldContext, view, projection);
    }

    @Override
    public OpenGLVariableHolder getPositions() {
        return null;
    }
}

