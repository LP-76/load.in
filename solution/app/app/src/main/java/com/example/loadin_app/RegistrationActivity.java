package com.example.loadin_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("                    Registration");
        String title = actionBar.getTitle().toString();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.loadin_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_registration);
    }
}