package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

public class Box extends WorldObject
{
    private CubeMappedHexahedron hexahedron;
    protected float width, height, length;
    protected float weight;
    protected int fragility;
    private String description = "objects that require a description";

    private int boxID, globalID;
    private static int lastGlobalId =0;

   private Vector destination = new Vector(0,0,0);

    public Box(int globalID, int boxID, float width, float height, float length, float weight, int fragility, String description)
    {
        super();

        this.width = width;
        this.height = height;
        this.length = length;

        this.globalID = globalID;
        this.boxID = boxID;
        this.fragility = fragility;
        this.weight = weight;
        this.description = description;
    }

    public Box(float width, float height, float length)
    {
        super();
        this.width = width;
        this.height = height;
        this.length = length;

        boxID = globalID = ++lastGlobalId;
     }

     public void setDestination(Vector destination){
        //System.out.println("box " + id + " is going to " + destination.toString());
        this.destination = destination;
     }
     public Vector getDestination()
     {
        return destination;
     }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public float getLength()
    {
        return length;
    }

    public float getVolume()
     {
         return getWidth() * getLength() * getHeight();
     }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getFragility() {
        return fragility;
    }

    public void setFragility(int fragility) {
        this.fragility = fragility;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vector getCenter()
     {
        float x = getWidth() / 2f;
        float z = getLength() / 2f;
        float y = getHeight() / 2f;
        return getWorldOffset().add( new Vector(x,y,z));

     }

    public void rotateLeftBy90Degrees()
    {
        width = getLength();
        height = getHeight();
        length = getWidth();
        recalculateShapes();
    }

    public boolean intersects(Box otherBox)
    {
        //TODO: implement
        return false;
    }
    public Vector getOffsetToLeftOf()
    {
        return null;
    }

    @Override
    public void draw(World worldContext, float[] view, float[] projection)
    {
        hexahedron.draw(worldContext, view, projection);
    }

    @Override
    public OpenGLVariableHolder getPositions()
    {
        return null;
    }

    @Override
    protected void recalculateShapes() {
        hexahedron = new CubeMappedHexahedron(
                width ,
                height ,
                length , this);
        hexahedron.setMap(myWorld.getCubeMapProgram().getBox());//the global box map

        //destination = new Vector(0f, 0f, 0f);
    }

    public int getBoxId()
    {
        return boxID;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isAbove(Box other){
        //asks the question if the box in question is directly above the other box
        //to be above, the x and z must be within the bounds of the other box

        Vector d1 = getDestination();
        Vector d2 = other.getDestination();

        boolean x_bounded = d2.getX() <= d1.getX() && d1.getX() + getWidth()  <=  d2.getX() + other.getWidth();
        boolean z_bounded = d2.getZ() <= d1.getZ() && d1.getZ() + getLength()   <=  d2.getZ() + other.getLength();

        return x_bounded && z_bounded && d1.getY() > d2.getY();

    }

}
