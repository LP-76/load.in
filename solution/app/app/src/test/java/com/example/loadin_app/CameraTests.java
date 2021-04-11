package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Camera;
import com.example.loadin_app.ui.opengl.Vector;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;



public class CameraTests {

    @Test
    public void testCameraLookatYaw(){
        //we're going to make sure the yaw is correct
        Camera theCamera = new Camera();


        Vector center = new Vector(0f,0f,0f);
        theCamera.place(center);

        for(int a= 0; a <= 360; a+= 15){
            double asRadians = Math.toRadians(a);
            double x = Math.cos(asRadians);
            double z = Math.sin(asRadians);
            Vector toLookAt =  new Vector((float)x, 0f, (float)z);
            theCamera.lookAt(toLookAt);
            float yaw = theCamera.getYaw();
            double yawAsRadians = Math.toRadians(yaw);  //we don't really care what the yaw is, we care that it looks at the same point

            double cosDelta = Math.cos(yawAsRadians) - Math.cos(asRadians);  //cos is the z component
            double sinDelta = Math.sin(yawAsRadians) - Math.sin(asRadians);  //sin is the x component

            Assert.assertTrue(Math.abs(cosDelta) < 1d);  //make sure that they are pretty close to one another within +/- 1 degree
            Assert.assertTrue(Math.abs(sinDelta) < 1d);


        }

        Vector upCenter = new Vector(0, 30, 0); //moving up should not affect yaw
        theCamera.place(upCenter);

        for(int a= 0; a <= 360; a+= 15){
            double asRadians = Math.toRadians(a);
            double x = Math.cos(asRadians);
            double z = Math.sin(asRadians);
            Vector toLookAt =  new Vector((float)x, 0f, (float)z);
            theCamera.lookAt(toLookAt);
            float yaw = theCamera.getYaw();
            double yawAsRadians = Math.toRadians(yaw);  //we don't really care what the yaw is, we care that it looks at the same point

            double cosDelta = Math.cos(yawAsRadians) - Math.cos(asRadians);  //cos is the z component
            double sinDelta = Math.sin(yawAsRadians) - Math.sin(asRadians);  //sin is the x component

            Assert.assertTrue(Math.abs(cosDelta) < 1d);  //make sure that they are pretty close to one another within +/- 1 degree
            Assert.assertTrue(Math.abs(sinDelta) < 1d);


        }

        Vector downCenter = new Vector(0, -30, 0); //moving up should not affect yaw
        theCamera.place(upCenter);

        for(int a= 0; a <= 360; a+= 15){
            double asRadians = Math.toRadians(a);
            double x = Math.cos(asRadians);
            double z = Math.sin(asRadians);
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

        Camera theCamera = new Camera();

        Vector center = new Vector(0f,0f,0f);
        theCamera.place(center);

        for(int a= 0; a <= 360; a+= 15){

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

    @Test
    public void testCameraYawCircleAroundCenter(){

        Vector center = new Vector(0f, 0f, 0f);
        Camera theCamera = new Camera();

        //we want to circle around the unit circle

        for(int a = 0; a <= 360; a++){
            double asRadians = Math.toRadians(a);
            Vector placement  = new Vector(
                    (float)Math.cos(asRadians),
                0f,
                    (float)Math.sin(asRadians)
            );

            theCamera.place(placement);
            Assert.assertTrue(within(theCamera.getWorldOffset().getX(), placement.getX(), 0.01f));
            Assert.assertTrue(within(theCamera.getWorldOffset().getY(), placement.getY(), 0.01f));
            Assert.assertTrue(within(theCamera.getWorldOffset().getZ(), placement.getZ(), 0.01f));
            int a1 = 0;


            theCamera.lookAt(center);//peform the lookat

            double expectedYaw = (180f + a) % 360d;  //we start by always looking to the right
            double yaw = theCamera.getYaw() % 360d;  //normalize the angles



            double delta = Math.abs(expectedYaw - yaw);



            Assert.assertTrue("yaw did not calculate as expected for angle : " + a, delta <= 0.5f);  //within one-half degree of accuracy

            //if we are looking toward the center, that is the oposite vector of the location

            Vector expectedFront = theCamera.getWorldOffset().multiply(-1f).normalize(); //reverse vector
            Vector normalFront = theCamera.getFront().normalize();

            assertTrue(within(expectedFront.getLength(), normalFront.getLength(), 0.001f));

            assertTrue(within(expectedFront.getX(), normalFront.getX(), 0.1f));
            assertTrue(within(expectedFront.getY(), normalFront.getY(), 0.1f));
            assertTrue(within(expectedFront.getZ(), normalFront.getZ(), 0.1f));


        }

        //check up should not affect yaw
        for(int a = 0; a <= 360; a++){
            double asRadians = Math.toRadians(a);
            Vector placement  = new Vector(
                    (float)Math.cos(asRadians),
                    30f,
                    (float)Math.sin(asRadians)
            );

            theCamera.place(placement);
            Assert.assertTrue(within(theCamera.getWorldOffset().getX(), placement.getX(), 0.01f));
            Assert.assertTrue(within(theCamera.getWorldOffset().getY(), placement.getY(), 0.01f));
            Assert.assertTrue(within(theCamera.getWorldOffset().getZ(), placement.getZ(), 0.01f));
            int a1 = 0;


            theCamera.lookAt(center);//peform the lookat

            double expectedYaw = (180f + a) % 360d;  //we start by always looking to the right
            double yaw = theCamera.getYaw() % 360d;  //normalize the angles



            double delta = Math.abs(expectedYaw - yaw);



            Assert.assertTrue("yaw did not calculate as expected for angle : " + a, delta <= 0.5f);  //within one-half degree of accuracy

            //if we are looking toward the center, that is the oposite vector of the location

            Vector expectedFront = theCamera.getWorldOffset().multiply(-1f).normalize(); //reverse vector
            Vector normalFront = theCamera.getFront().normalize();

            assertTrue(within(expectedFront.getLength(), normalFront.getLength(), 0.001f));

            assertTrue(within(expectedFront.getX(), normalFront.getX(), 0.1f));
            assertTrue(within(expectedFront.getY(), normalFront.getY(), 0.1f));
            assertTrue(within(expectedFront.getZ(), normalFront.getZ(), 0.1f));


        }

        for(int a = 0; a <= 360; a++){
            double asRadians = Math.toRadians(a);
            Vector placement  = new Vector(
                    (float)Math.cos(asRadians),
                    -30f,
                    (float)Math.sin(asRadians)
            );

            theCamera.place(placement);
            Assert.assertTrue(within(theCamera.getWorldOffset().getX(), placement.getX(), 0.01f));
            Assert.assertTrue(within(theCamera.getWorldOffset().getY(), placement.getY(), 0.01f));
            Assert.assertTrue(within(theCamera.getWorldOffset().getZ(), placement.getZ(), 0.01f));
            int a1 = 0;


            theCamera.lookAt(center);//peform the lookat

            double expectedYaw = (180f + a) % 360d;  //we start by always looking to the right
            double yaw = theCamera.getYaw() % 360d;  //normalize the angles



            double delta = Math.abs(expectedYaw - yaw);



            Assert.assertTrue("yaw did not calculate as expected for angle : " + a, delta <= 0.5f);  //within one-half degree of accuracy

            //if we are looking toward the center, that is the oposite vector of the location

            Vector expectedFront = theCamera.getWorldOffset().multiply(-1f).normalize(); //reverse vector
            Vector normalFront = theCamera.getFront().normalize();

            assertTrue(within(expectedFront.getLength(), normalFront.getLength(), 0.001f));

            assertTrue(within(expectedFront.getX(), normalFront.getX(), 0.1f));
            assertTrue(within(expectedFront.getY(), normalFront.getY(), 0.1f));
            assertTrue(within(expectedFront.getZ(), normalFront.getZ(), 0.1f));


        }

    }


    private boolean within(float value1, float value2, float accuracy){
        return Math.abs(value1 - value2) <= accuracy;
    }

}
