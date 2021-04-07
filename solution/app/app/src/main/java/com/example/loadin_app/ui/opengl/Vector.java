package com.example.loadin_app.ui.opengl;

import java.util.Arrays;
import java.util.stream.Stream;

public class Vector  {
    private  Float[] values;
    private Color color;

    public Vector(float x, float y, float z){
       this(x, y, z, new Color());
    }
    public Vector(float x, float y, float z, Color c){
        values = new Float[3];
        values[0] = x;
        values[1] = y;
        values[2] = z;
        color = c;
    }

    /*
    Create a vector from two points in space
     */
    public Vector(Vector start, Vector end){
        this(
                end.getX() - start.getX(),
                end.getY() - start.getY(),
                end.getZ() - start.getZ()
        );
    }

    public Vector multiply(float scalar){
        return new Vector(
                getX() * scalar,
                getY() * scalar,
                getZ() * scalar
        );
    }

    public float getLength(){
        return (float)Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2)+ Math.pow(getZ(), 2) );
    }

    public Vector  normalize(){
        float length = getLength();
        return new Vector(
                getX() / length,
                getY() / length,
                getZ() / length
        );
    }


    public void setX(float x){
         values[0] = x;
    }
    public void setY(float y){
         values[1] = y;
    }
    public void setZ(float z){
         values[2] = z;
    }


    public float getX(){
        return values[0];
    }
    public float getY(){
        return values[1];
    }
    public float getZ(){
        return values[2];
    }

    public Vector crossProduct(Vector other){

        Vector result = new Vector(
                values[1] * other.values[2] - values[2]* other.values[1],
                values[2] * other.values[0] - values[0]* other.values[2],
                values[0] * other.values[1] - values[1]* other.values[0]
                );
        return result;

    }
    public Vector add(Vector other){
        Vector result = new Vector(
                values[0] + other.values[0],
                values[1] + other.values[1],
                values[2] + other.values[2],
                color //persist the color
        );
        return result;
    }

    public float GetDistanceFromOrigin()
    {
        return (float)Math.sqrt(Math.pow((double)getX(),2) + Math.pow((double)getX(),2) + Math.pow((double)getX(),2) );
    }

    public boolean equalsVector(Vector v){
        return getX() == v.getX()
                && getY() ==v.getY()
                && getZ() == v.getZ();
    }


    public Stream<Float> getCoordinates() {
        return Arrays.stream(values);
    }
    public Stream<Float> getColors(){
        return color.asFloats();
    }

    @Override
    public String toString()
    {
        return "x: " + getX() + " y: " + getY() + "z: " + getZ();
    }
}
