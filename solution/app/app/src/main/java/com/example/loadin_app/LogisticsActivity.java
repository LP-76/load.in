package com.example.loadin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.RetroLogisticService;
import com.example.loadin_app.data.services.logisticalthings.Example;
import com.example.loadin_app.data.services.logisticalthings.Resource;
import com.example.loadin_app.data.services.logisticalthings.ResourceSet;
import com.example.loadin_app.data.services.logisticalthings.RouteLeg;
import com.example.loadin_app.ui.login.LoginActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import odu.edu.loadin.common.Inventory;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogisticsActivity extends AppCompatActivity {


    public static SharedPreferences sp;
    public EditText startAddressStreet, startAddressCityAndState, endAddressStreet, endAddressCityAndState;
    public String startSA, startCS, endSA, endCS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if (sp.getInt("loginID", 0) == 0) {
            Intent switchToLogin = new Intent(LogisticsActivity.this, LoginActivity.class);
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
        String username = app.getCurrentUser().getEmail();
        String password = app.getCurrentUser().getPassword();

        //TODO set these to pull values from user table in DB default values to empty strings
        /*
        sp.edit().putString("startStreet", pull start street from DB).apply();
        sp.edit().putString("startCityState", pull start CityState from DB).apply();
        sp.edit().putString("endStreet", pull end street from DB).apply();
        sp.edit().putString("endCityState", pull end CityState from DB).apply();
        */
        startAddressStreet = findViewById(R.id.logisticsStartingAddressStreet);
        startAddressStreet.setText(sp.getString("startStreet", ""));

        startAddressCityAndState = findViewById(R.id.logisticsStartingAddressCityAndState);
        startAddressCityAndState.setText(sp.getString("startCityState", ""));

        endAddressStreet = findViewById(R.id.logisticsEndingAddressStreet);
        endAddressStreet.setText(sp.getString("endStreet", ""));

        endAddressCityAndState = findViewById(R.id.logisticsEndingAddressCityAndState);
        endAddressCityAndState.setText(sp.getString("endCityState", ""));

        InventoryServiceImpl newInv = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
        ArrayList<Inventory> inventory = new ArrayList<Inventory>();
        int j = sp.getInt("loginID", 0);
        try{
            inventory.addAll(newInv.getInventory(j));
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        Integer moveInvSizeraw = inventory.size();
        String moveInvSizeStr = moveInvSizeraw.toString();
        TextView moveInvSize = findViewById(R.id.logisticsSizeOfMoveInventoryNumber);
        moveInvSize.setText(moveInvSizeStr);

        Button calculateNewDistance = (Button) findViewById(R.id.logisticsCalculationButton);

        calculateNewDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                double travelDistance = calculateDistanceOfMove();
                float travelDistanceConversion = (float)travelDistance;
                Intent switchToLogTable = new Intent(LogisticsActivity.this, LogisticsTableActivity.class);
                switchToLogTable.putExtra("milesTraveled", travelDistanceConversion);
                switchToLogTable.putExtra("sizeOfMoveInt", moveInvSizeStr);
                startActivity(switchToLogTable);
            }
        });

    }
    public String startingAddress()
    {

        sp.edit().putString("startStreet", startAddressStreet.getText().toString()).apply();
        sp.edit().putString("startCityState", startAddressCityAndState.getText().toString()).apply();

        String startingAddress = "Driving?wp.0=" + startAddressStreet.getText().toString() + startAddressCityAndState.getText().toString();

        return startingAddress;
    }
    public String endingAddress()
    {

        sp.edit().putString("endStreet", endAddressStreet.getText().toString()).apply();
        sp.edit().putString("endCityState", endAddressCityAndState.getText().toString()).apply();

        String endingAddress = "&wp.1=" + endAddressStreet.getText().toString() + endAddressCityAndState.getText().toString();

        return endingAddress;
    }
    public String generateURL(String startAddress, String endAddress)
    {
        String apiKey = "&mfa=1&key=AogocRILEPuE6yZt3RmY5k42gxzEuAXM9ub4qIGJtgZOEQeTRUBxCKjpJJWA1f3P";
        String unitOfMeasure = "&distanceUnit=Mile";
        String optimization = "&optimize=distance";
        String avoidance = "&avoid=tolls";
        String maxSolutions = "&maxSolns=1";

        String completedURL = startAddress + endAddress + optimization + avoidance + maxSolutions + unitOfMeasure + apiKey;

        return completedURL;
    }
    public double calculateDistanceOfMove()
    {
        String startAddress = startingAddress();
        String endAddress = endingAddress();

        String requestURL = generateURL(startAddress, endAddress);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dev.virtualearth.net/REST/V1/Routes/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        RetroLogisticService service = retrofit.create(RetroLogisticService.class);

        Call<Example> callSync = service.getExampleSet(requestURL);
        System.out.println("Creating a GET request to: " + callSync.request().url());

        try{
            Response<Example> response = callSync.execute();
            Example example = response.body();
            List<ResourceSet> apiResponse = example.getResourceSets();
            List<Resource> resource = apiResponse.get(0).getResources();
            double travelDistance = resource.get(0).getTravelDistance();
            return travelDistance;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        callSync.cancel();


        return 0d;
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
                Intent switchToMainMenu = new Intent(LogisticsActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(LogisticsActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(LogisticsActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(LogisticsActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(LogisticsActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(LogisticsActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(LogisticsActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(LogisticsActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
