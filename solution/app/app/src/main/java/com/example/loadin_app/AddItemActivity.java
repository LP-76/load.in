package com.example.loadin_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.InventoryService;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText descriptionInput, widthInput, depthInput, heightInput, weightInput, fragilityInput;
    private Button addItemButton;

    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(AddItemActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        descriptionInput = (EditText) findViewById(R.id.BoxDescriptionField);
        weightInput = (EditText) findViewById(R.id.WeightField);
        fragilityInput = (EditText) findViewById(R.id.FragilityField) ;
        widthInput = (EditText) findViewById(R.id.BoxWidthField);
        depthInput = (EditText) findViewById(R.id.BoxDepthField);
        heightInput = (EditText) findViewById(R.id.BoxHeightField);

        addItemButton = (Button) findViewById(R.id.AddItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addItemToDB(descriptionInput.getText().toString(), Float.parseFloat(widthInput.getText().toString()), Float.parseFloat(depthInput.getText().toString()),
                        Float.parseFloat(heightInput.getText().toString()), Float.parseFloat(weightInput.getText().toString()), Integer.parseInt(fragilityInput.getText().toString()));
            }
        });
    }

    private void addItemToDB(String inputDescription, float inputWidth,  float inputDepth, float inputHeight,
                             float inputWeight, int inputFragility){

        Inventory inv = new Inventory();
        inv.setDescription(inputDescription);
        inv.setWidth(inputWidth);
        inv.setLength(inputDepth);
        inv.setHeight(inputHeight);
        inv.setWeight(inputWeight);
        inv.setFragility(inputFragility);
        inv.setUserID(sp.getInt("loginID", 0));

        InventoryServiceImpl service = new InventoryServiceImpl("http://10.0.2.2:9000/");
        try{
            service.addInventory(inv);

            Toast.makeText(AddItemActivity.this, "Item Added To Inventory", Toast.LENGTH_SHORT).show();
            Intent switchToInventoryView = new Intent(AddItemActivity.this, AddItemActivity.class);
            startActivity(switchToInventoryView);
        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
        }
        //TODO: figure out what happens
        //what happens here?
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
                Intent switchToMainMenu = new Intent(AddItemActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(AddItemActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(AddItemActivity.this, BoxInputActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(AddItemActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlan = new Intent(AddItemActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(AddItemActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(AddItemActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}