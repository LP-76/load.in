package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

public class Box extends WorldObject{


    private CubeMappedHexahedron hexahedron;

   private Vector destination;

    public Box(float width, float height, float length, World world) {
        super(world);


        hexahedron = new CubeMappedHexahedron(
               width ,
               height ,
               length , this);
        hexahedron.setMap(world.getCubeMapProgram().getBox());//the global box map

        destination = new Vector(0f, 0f, 0f);
     }

     public void setDestination(Vector destination){
        this.destination = destination;
     }
     public Vector getDestination(){
        return destination;
     }


     public float getWidth(){
        return hexahedron.getWidth();
     }

     public float getHeight(){
        return hexahedron.getHeight();
     }
     public float getLength(){
        return hexahedron.getLength();
     }

     public float getArea()
     {
         return hexahedron.getWidth() * hexahedron.getLength() * hexahedron.getHeight();
     }


     public Vector getCenter(){
        float x = getWidth() / 2f;
        float z = getLength() / 2f;
        float y = getHeight() / 2f;
        return getWorldOffset().add( new Vector(x,y,z));

     }


    public void rotateLeftBy90Degrees(){
        hexahedron = new CubeMappedHexahedron(hexahedron.getLength(), hexahedron.getHeight(), hexahedron.getWidth(), this );
    }

    public boolean intersects(Box otherBox){
        //TODO: implement
        return false;
    }
    public Vector getOffsetToLeftOf(){
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
}
