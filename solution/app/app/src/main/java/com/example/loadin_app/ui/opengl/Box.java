package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

public class Box extends WorldObject{
    private CubeMappedHexahedron hexahedron;
    protected float width, height, length;

    private int id;
    private static int lastGlobalId =0;

   private Vector destination = new Vector(0,0,0);

    public Box(float width, float height, float length) {
        super();
        this.width = width;
        this.height = height;
        this.length = length;
        id = ++lastGlobalId;
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
        return id;
    }
}
