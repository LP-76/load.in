package com.example.loadin_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import android.widget.TextView;

import com.example.loadin_app.ui.login.LoginActivity;
import com.example.loadin_app.ui.opengl.TestGLSurfaceView;

public class TestOpenGLActivity extends Activity {

    private GLSurfaceView glView;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(this, LoginActivity.class);
            startActivity(switchToLogin);
        }

        super.onCreate(savedInstanceState);
        glView  = new TestGLSurfaceView(this);
        setContentView(glView);

    }
}