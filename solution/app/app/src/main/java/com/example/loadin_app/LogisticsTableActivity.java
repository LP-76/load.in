package com.example.loadin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.loadin_app.data.model.LogisticsResult;
import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.data.services.MovingTruckServiceImpl;
import com.example.loadin_app.ui.LogisticsDataAdapter;
import com.example.loadin_app.ui.MoveInventoryAdapter;
import com.example.loadin_app.ui.login.LoginActivity;
import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.MovingTruck;
import odu.edu.loadin.common.User;

public class LogisticsTableActivity extends AppCompatActivity {

    public static SharedPreferences sp;

    ArrayList<Box> movingInventory;
    ArrayList<MovingTruck> movingTrucks;
    InventoryServiceImpl inventoryService;
    MovingTruckServiceImpl movingTruckService;
    LoadPlanBoxServiceImpl loadPlanBoxService;
    int userId;
    Float milesTraveled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_table);
        Bundle extras = getIntent().getExtras();

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if ((userId = sp.getInt("loginID", 0)) == 0) {
            Intent switchToLogin = new Intent(LogisticsTableActivity.this, LoginActivity.class);
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
        inventoryService = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), currentUser.getEmail(), currentUser.getPassword());
        movingTruckService = new MovingTruckServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), currentUser.getEmail(), currentUser.getPassword());
        loadPlanBoxService = new LoadPlanBoxServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), currentUser.getEmail(), currentUser.getPassword());
        movingInventory = new ArrayList<Box>();
        movingTrucks = new ArrayList<MovingTruck>();
        try {
            movingInventory = inventoryService.getInventoryAsBoxes(userId);
            movingTrucks = new ArrayList<>(movingTruckService.getTrucks());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(extras != null){
            milesTraveled = (float) extras.getDouble("milesTraveled");
        }

       updateListView();

    }


    private void updateListView()
    {

        ArrayList<LogisticsResult> results = new ArrayList<>();

       for(MovingTruck t: movingTrucks){
           Truck t2 = new Truck(t);
            LoadPlanGenerator generator = new LoadPlanGenerator(userId, inventoryService, loadPlanBoxService,  t2, movingInventory );
            LoadPlan plan = generator.GenerateLoadPlan();
            LogisticsResult lr = new LogisticsResult(t);
            lr.setLoadPlan(plan);
            lr.setLpg(generator);
            lr.setNumOfMiles(milesTraveled);

            results.add(lr);

       }


        ListView listView = findViewById(R.id.LogisticsTable);
        LogisticsDataAdapter adapter = new LogisticsDataAdapter(this, R.layout.truck_information_listview, results);
        listView.setAdapter(adapter);

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
                Intent switchToMainMenu = new Intent(LogisticsTableActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(LogisticsTableActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(LogisticsTableActivity.this, BoxInputActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(LogisticsTableActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlan = new Intent(LogisticsTableActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(LogisticsTableActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(LogisticsTableActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
