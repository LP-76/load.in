package com.example.loadin_app.ui.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.loadin_app.R;

public class TestGLSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    int clickCount;
    boolean panMode;



    private final TestGLRenderer renderer;
    public TestGLSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(2);
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blah, ops);

        renderer = new TestGLRenderer(bitmap);
        setRenderer(renderer);
        panMode = true;

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);




    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        if(Math.abs(x - previousX) <= 0.1f && Math.abs(y - previousY) <=0.1f){
            clickCount++;
        }

        if(clickCount >2){
            panMode = !panMode;
            clickCount = 0;
        }

        switch (e.getAction()) {

            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy =  previousY - y;  //reversed coordinates

                float sensitivity = 0.1f;
                Camera c = renderer.getTheCamera();
                if(panMode){


                    c.setYaw(c.getYaw() + dx * sensitivity);
                    c.setPitch(c.getPitch() + dy * sensitivity);
                }else{
                    //move mode

                    if(dx < 0){
                        c.move(Camera.Direction.Left);
                    }else if(dx > 0){
                        c.move(Camera.Direction.Right);
                    }

                    if(dy < 0){
                        c.move(Camera.Direction.Backward);
                    }else if(dy > 0){
                        c.move(Camera.Direction.Forward);
                    }

                }




             requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }


}
