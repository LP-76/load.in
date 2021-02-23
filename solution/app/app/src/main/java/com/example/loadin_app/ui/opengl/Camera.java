package com.example.loadin_app.ui.opengl;

import android.icu.text.MessagePattern;
import android.opengl.GLES20;
import android.opengl.Matrix;



public class Camera {
    private Vector up;  //what is the direction that we consider up
    private Vector location;
    private Vector front; //front of the camera
    private Vector worldUp;
    private Vector right;

    public enum Direction{
        Forward,
        Backward,
        Left,
        Right
    }


    float yaw;
    float pitch;

    public Camera(){
        worldUp = new Vector(0, 1f, 0f);
        location = new Vector(0,0,0);
        front = new Vector(0, 0, -1f);
        up = new Vector(0f, 1f, 0f); //Y IS UP!!!!!  NOT Z!
        yaw = -90f;  //this represents the rotation about the y axis.  in other words, if i turn left or right kind of thing
        pitch = 0f;  //pitch is the degree that i am looking up or down
        updateCameraVectors();
    }


    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        updateCameraVectors();
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        updateCameraVectors();
    }

    public Vector getLocation() {
        return location;
    }


    public void move( Direction dir ){
        float velocity = 0.25f;
        switch(dir){
            case Left:
                location = location.add(right.multiply(-velocity));
                break;
            case Right:
                location = location.add(right.multiply(velocity));
                break;
            case Backward:
                location = location.add(front.multiply(-velocity));
                break;
            case Forward:
                location = location.add(front.multiply(velocity));
                break;
        }

    }




    private void updateCameraVectors(){
        if(pitch > 89.0f){  //enforce constraints with pitch so we don't get weird effects
            pitch = 89.0f;
        }
        else if(pitch < -89.0f){
            pitch = -89.0f;
        }

        yaw = yaw % 360f;

        double yawInRadians = Math.toRadians(yaw);
        double pitchInRadians = Math.toRadians(pitch);

        front = new Vector(
                (float)(Math.cos(yawInRadians) * Math.cos( pitchInRadians)),
                (float)Math.sin(pitchInRadians),
                (float)( Math.sin(yawInRadians) * Math.cos(pitchInRadians))
        ).normalize();
        right = front.crossProduct(worldUp).normalize();
        up = right.crossProduct(front).normalize();
    }

    public float[]  getLookatMatrix(){


        float[] result = new float[16];
        Vector direction = location.add(front);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(result, 0,
                location.getX(),
                location.getY(),
                location.getZ(),
                direction.getX(),
                direction.getY(),
                direction.getZ(),
                up.getX(),
                up.getY(),
                up.getZ());

        return result;
    }

    public void placeCamera(Vector location){
        this.location = location;
    }





}
