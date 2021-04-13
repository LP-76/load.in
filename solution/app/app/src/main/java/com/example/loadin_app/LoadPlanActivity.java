package com.example.loadin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;
import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.MovingTruck;

public class LoadPlanActivity extends AppCompatActivity
{

    private TextView mTextView;

    private Button generateLoadPlanButton, viewLoadPlanButton;
    private RadioButton useRandomBoxesRadioButton,useMoveInventoryRadioButton;
    private boolean useRandomBoxes= false;

    public static SharedPreferences sp;
    private LoadPlanGenerator generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_plan);
//
//        //THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
//        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
//        int userId;
//        if( (userId = sp.getInt("loginID", 0)) == 0){
//            Intent switchToLogin = new Intent(this, LoginActivity.class);
//            startActivity(switchToLogin);
//        }
//
//        LoadInApplication app = (LoadInApplication)getApplication();
//        String username = app.getCurrentUser().getEmail();
//        String password = app.getCurrentUser().getPassword();
//        LoadPlanBoxServiceImpl boxService = new LoadPlanBoxServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
//        InventoryServiceImpl inventoryService = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
//        ArrayList<Box> inventory = null;
//        try {
//             inventory = inventoryService.getInventoryAsBoxes(userId) ;
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        generator = new LoadPlanGenerator(userId,  inventoryService, boxService, new Truck(), inventory);
//
//        generator.setUseRandomBoxes(useRandomBoxes);
//
//        // Find the toolbar view inside the activity layout
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(Color.WHITE);
//        // Sets the Toolbar to act as the ActionBar for this Activity window.
//        // Make sure the toolbar exists in the activity and is not null
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);
//
//        mTextView = (TextView) findViewById(R.id.text);
//
//        generateLoadPlanButton = (Button) findViewById(R.id.generate_load_plan);
//        generateLoadPlanButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                StartLoadPlan();
//                Toast.makeText(LoadPlanActivity.this, "Load Plan Generated", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        viewLoadPlanButton = (Button) findViewById(R.id.ViewLoadPlanButton);
//        viewLoadPlanButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                GoTo3DRenderingActivity();
//            }
//        });
//        useMoveInventoryRadioButton = (RadioButton) findViewById(R.id.UseMoveInventoryRadioButton);
//        useMoveInventoryRadioButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {useRandomBoxes = false;}
//        });
//
//        useRandomBoxesRadioButton = (RadioButton) findViewById(R.id.UseRandomBoxesRadioButton);
//        useRandomBoxesRadioButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {useRandomBoxes = true;}
//        });
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
                Intent switchToMainMenu = new Intent(LoadPlanActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(LoadPlanActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(LoadPlanActivity.this, BoxInputActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(LoadPlanActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlan = new Intent(LoadPlanActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(LoadPlanActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(LoadPlanActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void StartLoadPlan()
    {
        //LoadPlanGenerator will take it from here!
        generator.setUseRandomBoxes(useRandomBoxes);
        //TODO: wire up other steps here for generating fake data
        LoadPlan plan = generator.GenerateLoadPlan();
        generator.sendLoadPlanToDatabase();

    }

    private void GoTo3DRenderingActivity()
    {
        Intent switchToLogin = new Intent(LoadPlanActivity.this, LoadPlanNavigatorActivity.class);
        startActivity(switchToLogin);
    }
}