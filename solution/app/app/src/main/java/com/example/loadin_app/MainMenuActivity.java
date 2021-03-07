package com.example.loadin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.loadin_app.ui.login.LoginActivity;

public class MainMenuActivity extends AppCompatActivity
{
    private Button tipsAndTricksButton, moveInventoryButton, loadPlanButton, gotoLoginButton, openGLTestButton;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /* THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(MainMenuActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }
        */


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        tipsAndTricksButton = (Button) findViewById(R.id.tips_and_tricks_button);
        tipsAndTricksButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToTipsAndTricks = new Intent(MainMenuActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTipsAndTricks);
            }
        });

        moveInventoryButton = (Button) findViewById(R.id.move_inventory_button);
        moveInventoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToMoveInventory = new Intent(MainMenuActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
            }
        });

        loadPlanButton = (Button) findViewById(R.id.load_plan_button);
        loadPlanButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToLoadPlan = new Intent(MainMenuActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
            }
        });

        gotoLoginButton = (Button) findViewById(R.id.goto_login_button);
        gotoLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToLogin = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(switchToLogin);
            }
        });
        openGLTestButton = (Button) findViewById(R.id.open_gl_test_button);
        openGLTestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToLogin = new Intent(MainMenuActivity.this, TestOpenGLActivity.class);
                startActivity(switchToLogin);
            }
        });


    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_main_menu:
                Intent switchToMainMenu = new Intent(MainMenuActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(MainMenuActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(MainMenuActivity.this, BoxInputActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(MainMenuActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlan = new Intent(MainMenuActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(MainMenuActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(MainMenuActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}