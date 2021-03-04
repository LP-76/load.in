package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.Arrays;
import java.util.stream.Stream;

public class Box extends TexturedWorldObject{
   //private final Color BOX_COLOR =  new Color(102f/255f, 84f/255f, 74f/255f, 1f);
   private TexturedHexahedron mHexahedron;

   private Vector destination;

    public Box(float width, float height, float length, World world) {
        super(world);


       mHexahedron = new TexturedHexahedron(
               width ,
               height ,
               length );


        destination = new Vector(0f, 0f, 0f);
     }

     public void setDestination(Vector destination){
        this.destination = destination;
     }
     public Vector getDestination(){
        return destination;
     }

    @Override
    public Stream<Shape> getShapes() {
        return Arrays.stream(new Shape[]{ mHexahedron });
    }



    @Override
    protected Stream<Float> getTextureCoordinates() {
        return mHexahedron.getTexturedTriangles().flatMap(i -> i.getTextureCoordinates());
    }

    public void rotateLeftBy90Degrees(){
        mHexahedron = new TexturedHexahedron(mHexahedron.getLength(), mHexahedron.getHeight(), mHexahedron.getWidth() );
    }

    public boolean intersects(Box otherBox){
        //TODO: implement
        return false;
    }
    public Vector getOffsetToLeftOf(){
        return null;
    }



    @Override
    public Texture getTexture() {
        return myWorld.getTextureViewProgram().getCardboard();
    }




}
