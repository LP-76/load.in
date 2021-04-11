package com.example.loadin_app.ui.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.loadin_app.R;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;

public class LoadPlanNavigatorSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    int clickCount;
    boolean panMode;



    private final LoadPlanRenderer renderer;
    public LoadPlanNavigatorSurfaceView(Context context, LoadPlanBoxServiceImpl boxService, InventoryServiceImpl inventoryService, boolean colorCodeBoxModeOn){
        super(context);
        setEGLContextClientVersion(2);


        renderer = new LoadPlanRenderer( context, boxService, inventoryService, colorCodeBoxModeOn);
        setRenderer(renderer);
        panMode = true;

        // Render the view only when there is a change in the drawing data
       // setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);




    }




    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        int height = getHeight();
        int centerHeight = height/2;
        int width = getWidth();

        int hitSpace = 200;

        Rect left = new Rect(0, centerHeight - (hitSpace/2), hitSpace, centerHeight + (hitSpace/2));
        Rect right = new Rect(width - hitSpace, centerHeight - (hitSpace/2), width, centerHeight+(hitSpace/2));




        float x = e.getX();
        float y = e.getY();

        boolean reverse = left.contains((int)x, (int)y);
        boolean forward = right.contains((int)x, (int)y);

//        if(Math.abs(x - previousX) <= 0.1f && Math.abs(y - previousY) <=0.1f){
//            clickCount++;
//        }
//
//        if(clickCount >2){
//            panMode = !panMode;
//            clickCount = 0;
//        }



        switch (e.getAction()) {

            case MotionEvent.ACTION_UP:
                if(reverse){
                    renderer.requestReverse();
                }else if(forward){
                    renderer.requestAdvance();
                }

                break;

            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy =  previousY - y;  //reversed coordinates
//
//
                if(dx < 0)  //someone swiped to the left
                {
                    //next box
                    renderer.requestFastForward();

                }else if(dx > 0){ //someone swiped to the right
                    renderer.requestFastRewind();

                }


                //float sensitivity = 0.1f;
                //Camera c = renderer.getTheCamera();
//                if(panMode){
//
//
//                    c.setYaw(c.getYaw() + dx * sensitivity);
//                    c.setPitch(c.getPitch() + dy * sensitivity);
//                }else{
//                    //move mode
//
//                    if(dx < 0){
//                        c.move(Camera.Direction.Left);
//                    }else if(dx > 0){
//                        c.move(Camera.Direction.Right);
//                    }
//
//                    if(dy < 0){
//                        c.move(Camera.Direction.Backward);
//                    }else if(dy > 0){
//                        c.move(Camera.Direction.Forward);
//                    }
//
//                }




             requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }


}
