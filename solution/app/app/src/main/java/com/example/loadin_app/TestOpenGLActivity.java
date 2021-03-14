package com.example.loadin_app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import android.widget.TextView;

import com.example.loadin_app.ui.opengl.TestGLSurfaceView;

public class TestOpenGLActivity extends Activity {

    private GLSurfaceView glView;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        glView  = new TestGLSurfaceView(this);
        setContentView(glView);

    }
}