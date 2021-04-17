package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.LoadPlanBox;

public class Box extends WorldObject {
    private CubeMappedHexahedron hexahedron;
    protected float width, height, length;
    protected float weight;
    protected int fragility;
    private String description = "objects that require a description";
    private String status;
    private String room;
    private String itemList;

    private int boxID, globalID, userID;
    private static int lastGlobalId = 0;

    private Vector destination = new Vector(0, 0, 0);

    public Box(int globalID, int boxID, float width, float height, float length, float weight, int fragility, String description, int userID, String status, String room, String itemList) {
        super();

        this.width = width;
        this.height = height;
        this.length = length;
        this.userID = userID;

        this.globalID = globalID;
        this.boxID = boxID;
        this.fragility = fragility;
        this.weight = weight;
        this.description = description;
        this.status = status;
        this.room = room;
        this.itemList = itemList;
    }

    public Box(float width, float height, float length) {
        super();
        this.width = width;
        this.height = height;
        this.length = length;

        boxID = globalID = ++lastGlobalId;
    }
    public Box(Box other)
    {
        super();
        this.length = other.length;
        this.width = other.width;
        this.height = other.height;

        this.fragility = other.fragility;
        this.weight = other.weight;
        this.description = other.description;
        this.status = other.status;
        this.boxID = other.boxID;
        this.userID = other.userID;
        this.globalID = other.globalID;
        this.destination = other.destination;
        this.hexahedron = other.hexahedron;
        this.room = other.room;
        this.itemList = other.itemList;
    }

    public void setDestination(Vector destination) {
        //System.out.println("box " + id + " is going to " + destination.toString());
        this.destination = destination;
    }


    public LoadPlanBox toLoadPlanBox(){

        LoadPlanBox lpb = new LoadPlanBox();
        Inventory i = lpb.getBox();
        i.setId(globalID);
        i.setBoxID(boxID);
        i.setDescription(description);
        i.setWidth(width);
        i.setHeight(height);
        i.setLength(length);
        i.setFragility(fragility);
        i.setWeight(weight);
        i.setStatus(status);
        i.setUserID(userID);
        i.setRoom(room);
        i.setItemList(itemList);
        lpb.setxOffset(destination.getX());
        lpb.setyOffset(destination.getY());
        lpb.setzOffset(destination.getZ());

        return lpb;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vector getDestination() {
        return destination;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getLength() {
        return length;
    }

    public float getVolume() {
        return getWidth() * getLength() * getHeight();
    }

    public float getXZArea() {
        return getWidth() * getLength();
    }


    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
        if(myWorld != null)
            recalculateShapes(); //this has an impact on the color
    }

    public int getFragility() {
        return fragility;
    }

    public void setFragility(int fragility) {

        this.fragility = fragility;
        if(myWorld != null)
            recalculateShapes();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vector getCenter() {
        float x = getWidth() / 2f;
        float z = getLength() / 2f;
        float y = getHeight() / 2f;
        return getWorldOffset().add(new Vector(x, y, z));

    }

    private Color calculateColor(){

        if(!myWorld.isColorCodeBoxes())
            return new Color(0,0,0,0); //nothing, we changed to addition

        int adjFragility = Math.max(Math.min(fragility, 5), 1);
        float adjWeight = Math.max(Math.min(weight, 10), 1);
        float red = ((float)adjFragility)/5f;
        float blue = adjWeight/10f;

        return new Color(red, 0f, blue, 0f);

    }

    public void rotateLeftBy90Degrees() {
        width = getLength();
        height = getHeight();
        length = getWidth();
        recalculateShapes();
    }

    public boolean intersects(Box otherBox) {
        //TODO: implement
        return false;
    }

    public Vector getOffsetToLeftOf() {
        return null;
    }

    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        hexahedron.draw(worldContext, view, projection);
    }

    @Override
    public OpenGLVariableHolder getPositions() {
        return null;
    }

    @Override
    protected void recalculateShapes() {
        Color mycolor = calculateColor();
        hexahedron = new CubeMappedHexahedron(
                width,
                height,
                length, this, mycolor);

        hexahedron.setMap(myWorld.getCubeMapProgram().getBox());//the global box map

        //destination = new Vector(0f, 0f, 0f);
    }



    public void setBoxId(int boxID) {
        this.boxID = boxID;
    }

    public int getBoxId() {
        return boxID;
    }

    public int getId() {
        return globalID;
    }
    public void setId(int id){
        globalID = id;
    }

    public String getDescription() {
        return description;
    }

    public float calcArea(){
        return width *  length;
    }

    public static boolean withinRange(float s, float e, float p) {
        float max = Math.max(s, e);
        float min = Math.min(s, e);
        return min <= p && p <= max;
    }

    public static boolean overlaps(float s1, float e1, float s2, float e2) {
        return withinRange(s1, e1, s2) || withinRange(s1, e1, e2) || withinRange(s2, e2, s1) || withinRange(s2, e2, e1);
    }

    public boolean isInSameRowAs(Box other) {
        //to be in the same row, we must not be above the other box

        Vector myDestination = getDestination();
        Vector othersDestination = other.getDestination();


        return overlaps(
                myDestination.getZ() + 0.1f,
                myDestination.getZ() + getLength() - 0.1f,
                othersDestination.getZ() + 0.1f,
                othersDestination.getZ() + other.getLength() - 0.1f);

    }

    public boolean isInFrontOf(Box other) {
        Vector myDestination = getDestination();
        Vector othersDestination = other.getDestination();
        boolean sameRow = isInSameRowAs(other);
        return !sameRow && othersDestination.getZ() > myDestination.getZ();  //the greater the z, the further back in the truck it is
    }


    public boolean isAbove(Box other) {
        //asks the question if the box in question is directly above the other box
        //to be above, the x and z must be within the bounds of the other box

        Vector myDestination = getDestination();
        Vector otherDestination = other.getDestination();

        //both x and z overlap and my y is greater than the other box

        return overlaps
                (
                        myDestination.getX() + 0.1f,
                        myDestination.getX() + getWidth() -0.1f,
                        otherDestination.getX() + 0.1f,
                        otherDestination.getX() + other.getWidth() - 0.1f
                )  //overlap x
                && overlaps
                (
                        myDestination.getZ() + 0.1f,
                        myDestination.getZ() + getLength() -0.1f,
                        otherDestination.getZ() + 0.1f,
                        otherDestination.getZ() + other.getLength() -0.1f
                ) //overlap z
                && myDestination.getY() > otherDestination.getY(); //add has to be above the other box
    }

    private String nullChecker(String s){
        return s != null ? s : "";
    }

    public String getBoxMessage(){
       String boxMessage = "Box#: " + getBoxId() + "\n" +
                "Contents: " + nullChecker(getDescription()) + "\n" +
                "Room: " + nullChecker(getRoom()) + "\n" +
                "Items: " + nullChecker(getItemList()) + "\n" +
                "Dimensions: " + getWidth() + " x " + getHeight() + " x " + getLength() + "\n" +
                "Weight: " + getWeight() + " Fragility: " + getFragility();

       return boxMessage;
    }


}
