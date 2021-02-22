package com.example.loadin_app.ui.opengl;

import java.util.Arrays;
import java.util.stream.Stream;

public class Triangle {
   private final Vector[] values = new Vector[3];
    public Triangle(Vector p1, Vector p2, Vector p3){
        values[0] = p1;
        values[1] = p2;
        values[2] = p3;
    }


    private Vector calculateOrthogonalVectorToPoint(int originPoint){
        Vector v1 = new Vector(values[originPoint], values[(originPoint + 1) % 3]);
        Vector v2 = new Vector(values[originPoint], values[(originPoint + 2) % 3]);
        //this gives us two vectors from our plane

        return v1.crossProduct(v2);  //taking the cross product gives us this answer
    }

    public Vector[] getOrthogonalVectorsForPlane(){
        Vector[] orthogonalVectors = new Vector[3];
        for(int x = 0; x < 3; x++)
            orthogonalVectors[x] = calculateOrthogonalVectorToPoint(x);
        return orthogonalVectors;
    }

    public Stream<Float> getCoordinates() {
        return Arrays.stream(values).flatMap(i -> i.getCoordinates());
    }

    public Stream<Float> getColors(){
        return  Arrays.stream(values).flatMap(i -> i.getColors());
    }
}
