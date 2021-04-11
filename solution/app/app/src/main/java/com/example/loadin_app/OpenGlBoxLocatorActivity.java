package com.example.loadin_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;
import com.example.loadin_app.ui.opengl.LoadPlanNavigatorSurfaceView;
import com.example.loadin_app.ui.opengl.OpenGlBoxLocatorSurfaceView;

public class OpenGlBoxLocatorActivity extends Activity {

    private GLSurfaceView glView;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(this, LoginActivity.class);
            startActivity(switchToLogin);
        }
        Bundle extras = getIntent().getExtras();
        //int id = (sp.getInt("id",0));
        int id = extras.getInt("id");
        LoadInApplication app = (LoadInApplication)getApplication();
        String username = app.getCurrentUser().getEmail();
        String password = app.getCurrentUser().getPassword();
        LoadPlanBoxServiceImpl boxService = new LoadPlanBoxServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
        InventoryServiceImpl inventoryService = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);

        super.onCreate(savedInstanceState);
        glView  = new OpenGlBoxLocatorSurfaceView(this, boxService, inventoryService,id);
        setContentView(glView);

    }
}