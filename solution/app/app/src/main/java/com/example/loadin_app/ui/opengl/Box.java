package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.Arrays;
import java.util.stream.Stream;

public class Box extends TexturedWorldObject{
   //private final Color BOX_COLOR =  new Color(102f/255f, 84f/255f, 74f/255f, 1f);
   private TexturedHexahedron hexahedron;

   private Vector destination;

    public Box(float width, float height, float length, World world) {
        super(world);


        hexahedron = new TexturedHexahedron(
               width ,
               height ,
               length );

        hexahedron.setBackTexture(world.getTextureViewProgram().getCardboard());
        hexahedron.setFrontTexture(world.getTextureViewProgram().getCardboard());
        hexahedron.setBottomTexture(world.getTextureViewProgram().getCardboard());
        hexahedron.setLeftTexture(world.getTextureViewProgram().getCardboard());
        hexahedron.setRightTexture(world.getTextureViewProgram().getCardboard());
        hexahedron.setTopTexture(world.getTextureViewProgram().getCardboard());


        destination = new Vector(0f, 0f, 0f);
     }

     public void setDestination(Vector destination){
        this.destination = destination;
     }
     public Vector getDestination(){
        return destination;
     }




    public void rotateLeftBy90Degrees(){
        hexahedron = new TexturedHexahedron(hexahedron.getLength(), hexahedron.getHeight(), hexahedron.getWidth() );
    }

    public boolean intersects(Box otherBox){
        //TODO: implement
        return false;
    }
    public Vector getOffsetToLeftOf(){
        return null;
    }


    @Override
    public Stream<IDrawable> getDrawableShapes() {
        return Arrays.stream(new IDrawable[]{hexahedron});
    }
}
