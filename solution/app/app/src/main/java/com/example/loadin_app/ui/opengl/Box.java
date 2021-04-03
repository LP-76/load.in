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

     public float getXZArea()
     {
         return getWidth()*getLength();
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
    public int getId()
    {
        return globalID;
    }
    public String getDescription()
    {
        return description;
    }

    private boolean withinRange(float s, float e, float p){
        float max = Math.max(s, e);
        float min = Math.min(s, e);
        return min < p && p < max;
    }

    private boolean overlaps(float s1, float e1, float s2, float e2 ){
        return withinRange(s1, e1, s2) || withinRange(s1, e1, e2) || withinRange(s2, e2, s1) || withinRange(s2, e2, e1);
    }

    public boolean isInSameRowAs(Box other){
        //to be in the same row, we must not be above the other box

        Vector myDestination = getDestination();
        Vector othersDestination = other.getDestination();


        return overlaps(myDestination.getZ(), myDestination.getZ() + getLength(), othersDestination.getZ(), othersDestination.getZ() + other.getLength())
                && !isAbove(other) && !other.isAbove(this);  //neither box is above each other

    }

    public boolean isInFrontOf(Box other){
        Vector myDestination = getDestination();
        Vector othersDestination = other.getDestination();
        boolean sameRow = isInSameRowAs(other);
        return !sameRow && othersDestination.getZ() > myDestination.getZ();  //the greater the z, the further back in the truck it is
    }


    public boolean isAbove(Box other){
        //asks the question if the box in question is directly above the other box
        //to be above, the x and z must be within the bounds of the other box

        Vector myDestination = getDestination();
        Vector otherDestination = other.getDestination();

        //both x and z overlap and my y is greater than the other box

        return overlaps(myDestination.getX(), myDestination.getX() + getWidth(),
                otherDestination.getX(), otherDestination.getX() + other.getWidth())  //overlap x
                && overlaps(myDestination.getZ(), myDestination.getZ() + getLength(),
                otherDestination.getZ(), otherDestination.getZ() + other.getLength()) //overlap z
                && myDestination.getY() > otherDestination.getY();
    }

}
