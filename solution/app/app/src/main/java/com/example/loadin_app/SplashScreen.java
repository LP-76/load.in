package com.example.loadin_app;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.loadin_app.ui.login.LoginActivity;

public class SplashScreen extends AppCompatActivity
{
    private int splashScreenDurationInMillis = 4099;
    public static SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        sp.edit().putInt("loginID", 0).apply();

        //used these resources as reference here -Jason
        //https://youtu.be/Q0gRqbtFLcw
        //https://abhiandroid.com/programming/splashscreen

        //getSupportActionBar().hide(); //hides the top bar, giving the splash screen more ...splash -Jason

        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                Intent switchToLogin = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(switchToLogin);
                finish();
            }
        }, splashScreenDurationInMillis);
    }
}