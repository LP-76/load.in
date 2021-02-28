package com.example.loadin_app;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import android.widget.TextView;

import com.example.loadin_app.ui.opengl.TestGLSurfaceView;

public class TestOpenGLActivity extends Activity {

    private GLSurfaceView glView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView  = new TestGLSurfaceView(this);
        setContentView(glView);

    }
}