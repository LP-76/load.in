package com.example.loadin_app;

import android.util.Pair;

import com.example.loadin_app.data.services.BoxServiceImpl;
import com.example.loadin_app.data.services.UserServiceImpl;
import com.example.loadin_app.ui.opengl.Camera;
import com.example.loadin_app.ui.opengl.Vector;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.User;

import static org.junit.Assert.*;



public class CameraTests {

    @Test
    public void testCameraLookatYaw(){
        //we're going to make sure the yaw is correct
        Camera theCamera = new Camera();


        Vector center = new Vector(0f,0f,0f);
        theCamera.placeCamera(center);

        for(int a= 0; a <= 360; a+= 45){
            double asRadians = Math.toRadians(a);
            double x = Math.sin(asRadians);
            double z = Math.cos(asRadians);
            Vector toLookAt =  new Vector((float)x, 0f, (float)z);
            theCamera.lookAt(toLookAt);
            float yaw = theCamera.getYaw();
            double yawAsRadians = Math.toRadians(yaw);  //we don't really care what the yaw is, we care that it looks at the same point

            double cosDelta = Math.cos(yawAsRadians) - Math.cos(asRadians);  //cos is the z component
            double sinDelta = Math.sin(yawAsRadians) - Math.sin(asRadians);  //sin is the x component

            Assert.assertTrue(Math.abs(cosDelta) < 1d);  //make sure that they are pretty close to one another within +/- 1 degree
            Assert.assertTrue(Math.abs(sinDelta) < 1d);


        }





    }

    @Test
    public void testCameraLookatPitch(){
        //we're going to make sure the yaw is correct
        Camera theCamera = new Camera();

        Vector center = new Vector(0f,0f,0f);
        theCamera.placeCamera(center);

        for(int a= 0; a <= 360; a+= 45){

            double asRadians = Math.toRadians(a);
            double x = Math.cos(asRadians);
            double y = Math.sin(asRadians);
            Vector toLookAt =  new Vector((float)x, (float)y, 0f);
            theCamera.lookAt(toLookAt);
            float pitch = theCamera.getPitch();
            double pitchAsRadians = Math.toRadians(pitch);  //we don't really care what the yaw is, we care that it looks at the same point
            double expectedPitch = a > 270 ? -(360 - a) : a > 180 ? -(a - 180) : a > 90 ? 180 - a : a;
            double expectedPitchAsRadians = Math.toRadians(expectedPitch);

            double sinDelta = Math.sin(pitchAsRadians) - Math.sin(expectedPitchAsRadians);  //sin is the y component

            //y is either positive or negative

             //make sure that they are pretty close to one another within +/- 1 degree
            Assert.assertTrue(Math.abs(sinDelta) < 1d);


        }





    }

}
