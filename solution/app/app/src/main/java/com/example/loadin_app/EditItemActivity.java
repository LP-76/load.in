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
import android.widget.TextView;
import android.widget.Toast;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.BoxServiceImpl;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

import odu.edu.loadin.common.Inventory;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;

public class EditItemActivity extends AppCompatActivity {

    private EditText descriptionInput, widthInput, depthInput, heightInput, fragilityInput, weightInput, roomInput, itemListInput;
    private Button editItemButton;
    private String description, width, length, height, weight, fragility, room, itemList;

    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(EditItemActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        EditText descH = (EditText) findViewById(R.id.BoxDescriptionField2);
        descH.setHint(sp.getString("itemDescription", ""));

        EditText fragH = (EditText) findViewById(R.id.FragilityField2);
        fragH.setHint(sp.getString("itemFragility", ""));

        EditText weightH = (EditText) findViewById(R.id.WeightField2);
        weightH.setHint(sp.getString("itemWeight", ""));

        EditText widthH = (EditText) findViewById(R.id.BoxWidthField2);
        widthH.setHint(sp.getString("itemWidth", ""));

        EditText lengthH = (EditText) findViewById(R.id.BoxDepthField2);
        lengthH.setHint(sp.getString("itemLength", ""));

        EditText heightH = (EditText) findViewById(R.id.BoxHeightField2);
        heightH.setHint(sp.getString("itemHeight", ""));

        EditText roomH = (EditText) findViewById(R.id.BoxRoom2);
        roomH.setHint(sp.getString("itemRoom", ""));

        EditText itemsH = (EditText) findViewById(R.id.BoxItemList2);
        itemsH.setHint(sp.getString("itemList", ""));

        descriptionInput = (EditText) findViewById(R.id.BoxDescriptionField2);
        weightInput = (EditText) findViewById(R.id.WeightField2);
        fragilityInput = (EditText) findViewById(R.id.FragilityField2) ;
        widthInput = (EditText) findViewById(R.id.BoxWidthField2);
        depthInput = (EditText) findViewById(R.id.BoxDepthField2);
        heightInput = (EditText) findViewById(R.id.BoxHeightField2);
        roomInput = (EditText) findViewById(R.id.BoxRoom2);
        itemListInput = (EditText) findViewById(R.id.BoxItemList2);

        editItemButton = (Button) findViewById(R.id.editItemButton);
        editItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                description = descriptionInput.getText().toString();
                width = widthInput.getText().toString();
                length = depthInput.getText().toString();
                height = heightInput.getText().toString();
                weight = weightInput.getText().toString();
                fragility = fragilityInput.getText().toString();
                room = roomInput.getText().toString();
                itemList = itemListInput.getText().toString();
                LoadInApplication app = (LoadInApplication) getApplication();
                String username = app.getCurrentUser().getEmail();
                String password = app.getCurrentUser().getPassword();

                InventoryServiceImpl service = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);

                ArrayList<Inventory> item = new ArrayList<Inventory>();
                try {
                    item.add(service.getInventory(sp.getInt("loginID", 0)).get(Integer.parseInt(sp.getString("itemBoxID", ""))-1));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!description.matches("")) {
                    item.get(0).setDescription(description);
                }
                if(!width.matches("")){
                    item.get(0).setWidth(Float.parseFloat(width));
                }
                if(!length.matches("")){
                    item.get(0).setLength(Float.parseFloat(length));
                }
                if(!height.matches("")){
                    item.get(0).setHeight(Float.parseFloat(height));
                }
                if(!fragility.matches("")){
                    item.get(0).setFragility(Integer.parseInt(fragility));
                }
                if(!weight.matches("")){
                    item.get(0).setWeight(Float.parseFloat(weight));
                }
                if(!room.matches("")) {
                    item.get(0).setRoom(room);
                }
                if(!itemList.matches("")) {
                    item.get(0).setItemList(itemList);
                }




                updateItemToDB(item.get(0), service);
            }
        });



    }









    private void updateItemToDB(Inventory item, InventoryServiceImpl service){


        try{
            service.editInventory(item);

            Toast.makeText(EditItemActivity.this, "Edit Successful", Toast.LENGTH_SHORT).show();
            Intent switchToInventoryView = new Intent(EditItemActivity.this, MoveInventoryActivity.class);
            startActivity(switchToInventoryView);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
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
                Intent switchToMainMenu = new Intent(EditItemActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(EditItemActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(EditItemActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(EditItemActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(EditItemActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(EditItemActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(EditItemActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(EditItemActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

