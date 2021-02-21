package com.example.loadin_app.ui.opengl;

import java.util.Arrays;
import java.util.stream.Stream;

public class Box extends WorldObject{

   private Hexahedron mHexahedron;

    public Box(float width, float height, float length){
       mHexahedron = new Hexahedron(width, height, length);
     }

    @Override
    public Stream<Shape> getShapes() {
        return Arrays.stream(new Shape[]{ mHexahedron });
    }

    public void rotateLeftBy90Degrees(){
        mHexahedron = new Hexahedron(mHexahedron.getLength(), mHexahedron.getHeight(), mHexahedron.getWidth() );
    }

    public boolean intersects(Box otherBox){
        //TODO: implement
        return false;
    }
    public Vector getOffsetToLeftOf(){
        return null;
    }

}
