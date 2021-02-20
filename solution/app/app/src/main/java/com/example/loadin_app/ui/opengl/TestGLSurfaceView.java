package com.example.loadin_app.ui.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class TestGLSurfaceView extends GLSurfaceView {

    private final TestGLRenderer renderer;
    public TestGLSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(2);

        renderer = new TestGLRenderer();
        setRenderer(renderer);
    }

}
