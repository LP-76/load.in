package com.example.loadin_app;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.loadin_app.R.drawable.loadin_logo;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("                      Load.In");
        String title = actionBar.getTitle().toString();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.loadin_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        setContentView(R.layout.activity_login);
        //setContentView(R.layout.activity_main);
    }
}