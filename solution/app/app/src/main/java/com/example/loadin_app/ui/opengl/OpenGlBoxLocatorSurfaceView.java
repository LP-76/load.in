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

public class OpenGlBoxLocatorSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    int clickCount;
    boolean panMode;



    private final BoxLocatorRenderer renderer;
    public OpenGlBoxLocatorSurfaceView(Context context, LoadPlanBoxServiceImpl boxService, InventoryServiceImpl inventoryService, int id){
        super(context);
        setEGLContextClientVersion(2);

        Box b = new Box( 24, 24, 24  );
        b.setBoxId(id); //some random box from bruce 6 inventory for now

        renderer = new BoxLocatorRenderer( context, boxService, inventoryService, b);
        setRenderer(renderer);
        panMode = true;

        // Render the view only when there is a change in the drawing data
       // setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);




    }





}
