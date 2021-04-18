package com.example.loadin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.data.services.MovingTruckServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.LoadPlanBox;
import odu.edu.loadin.common.User;

public class MainMenuActivity extends AppCompatActivity
{

    private ArrayList<LoadPlanBox> loadPlanBoxes;
    private Button tipsAndTricksButton, feedbackButton, moveInventoryButton, moveInventoryButton2, loadPlanButton, gotoLoginButton, loadPlanNavigatorNoColorButton, logisticsButton, loadPlanNavigatorWithColorButton;
    private Button testHarnessButton;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(MainMenuActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }



        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        LoadInApplication app = (LoadInApplication)getApplication();
        User currentUser = app.getCurrentUser();
        LoadPlanBoxServiceImpl loadPlanBoxService = new LoadPlanBoxServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), currentUser.getEmail(), currentUser.getPassword());
        try {
            loadPlanBoxes = loadPlanBoxService.getLoadPlan(sp.getInt("loginID",0));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        //this is actually to add item
        moveInventoryButton = (Button) findViewById(R.id.move_inventory_button);
        moveInventoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToMoveInventory = new Intent(MainMenuActivity.this, AddItemActivity.class);
                startActivity(switchToMoveInventory);
            }
        });

        moveInventoryButton2 = (Button) findViewById(R.id.goto_move_inventory_button);
        moveInventoryButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToMoveInventory = new Intent(MainMenuActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
            }
        });

        feedbackButton = (Button) findViewById(R.id.feedback_button);
        feedbackButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToFeedback = new Intent(MainMenuActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
            }
        });

        logisticsButton = (Button) findViewById(R.id.logistics_button);
        logisticsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToLoadPlan = new Intent(MainMenuActivity.this, LogisticsActivity.class);
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
        loadPlanNavigatorNoColorButton = (Button) findViewById(R.id.load_plan_navigator_no_color_button);
        loadPlanNavigatorNoColorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(loadPlanBoxes.size() == 0)
                {
                    Toast.makeText(MainMenuActivity.this, "Error: No Load Plan has been found. Please use the Logistics Page.", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent switchToLoadPlanNavigator = new Intent(MainMenuActivity.this, LoadPlanNavigatorActivity.class);
                    startActivity(switchToLoadPlanNavigator);
                }
            }
        });

        loadPlanNavigatorWithColorButton = (Button) findViewById(R.id.load_plan_navigator_with_color_button);
        loadPlanNavigatorWithColorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(loadPlanBoxes.size() == 0)
                {
                    Toast.makeText(MainMenuActivity.this, "Error: No Load Plan has been found. Please use the Logistics Page.", Toast.LENGTH_LONG).show();
                }
                else {
                Intent switchToLoadPlanNavigator = new Intent(MainMenuActivity.this, LoadPlanNavigatorActivity.class);
                switchToLoadPlanNavigator.putExtra(LoadPlanNavigatorActivity.COLOR_CODE_PREFERNCE_KEY, true);
                startActivity(switchToLoadPlanNavigator);
            }}
        });

        testHarnessButton = (Button) findViewById(R.id.goto_test_harness_button);
        testHarnessButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToTestHarness = new Intent(MainMenuActivity.this, TestHarnessActivity.class);
                startActivity(switchToTestHarness);
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
                Intent switchToBoxInput = new Intent(MainMenuActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(MainMenuActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(MainMenuActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(MainMenuActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
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