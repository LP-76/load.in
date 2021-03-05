package com.example.loadin_app.ui.opengl;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Truck extends ColorWorldObject
{
    private final Color FLOOR_COLOR = new Color(161f/255f, 119f/255f,93f/255f, 1f);
    private final Color WALL_COLOR = new Color(206f/255f, 218f/255f,210f/255f, 1f);


    private float lengthInches,widthInches,heightInches;
    private float costPerDayDollars, costPerMileDollars;
    private ArrayList<Shape> shapes;

    public float getLengthInches() {
        return lengthInches;
    }

    public float getWidthInches() {
        return widthInches;
    }

    public float getHeightInches() {
        return heightInches;
    }

    public Truck(World parent)
    {
        super(parent);
        shapes = new ArrayList<Shape>();
        //UHAUL SAMPLE TRUCK THAT's a 17 footer



        lengthInches = 14f * 12f ;  //14foot 3 inches
        widthInches = 7f * 12f ;
        heightInches = 7f* 12f ;
        costPerDayDollars = 39.95f;
        costPerMileDollars = 0.99f;

        //TODO: Make a real constructor that takes real data from somewhere and doesn't hardwire random data from uhaul.com

        recalculateShapes();

    }

    public void recalculateShapes(){
        //we're going to create the truck bed, the outer left wall and the front wall of the truck bed

        float wallThickness = 4f ;  //4 inch walls by default

        float height = heightInches ;
        float width = widthInches ;
        float length = lengthInches ;

        ColorHexahedron floor = new ColorHexahedron(
                width, //we'll say approximately 4 inches thick for now
                wallThickness,
                length,
                FLOOR_COLOR
        );
        floor.move(new Vector(0f, -wallThickness, 0f));  //move down below 0,0 line

        ColorHexahedron leftWall = new ColorHexahedron(
                wallThickness, //we'll say approximately 4 inches thick for now
                height,
                length,
                WALL_COLOR
        );
        leftWall.move(new Vector( width, 0f, 0f));  //move to the left 4 inches

        ColorHexahedron frontWall = new ColorHexahedron(
                width, //we'll say approximately 4 inches thick for now
                height,
                wallThickness,
                WALL_COLOR
        );
        frontWall.move(new Vector(0f, 0f, length));  //move to front of bed



        shapes.clear();
        shapes.add(frontWall);
        shapes.add(leftWall);
        shapes.add(floor);


    }

    @Override
    public Stream<Shape> getShapes()
    {
        return shapes.stream();
    }



    public float GetAreaOfTruckInches()
    {
        return lengthInches * widthInches * heightInches;
    }
}

