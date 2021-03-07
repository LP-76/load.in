package com.example.loadin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

import java.util.ArrayList;

import odu.edu.loadin.common.Inventory;

public class MoveInventoryActivity extends AppCompatActivity {

    private TextView mTextView;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_inventory);

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(MoveInventoryActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }

        sp.edit().putString("itemDescription", "").apply();
        sp.edit().putString("itemBoxID", "Box ID").apply();
        sp.edit().putString("itemWidth", "").apply();
        sp.edit().putString("itemLength", "").apply();
        sp.edit().putString("itemHeight", "").apply();
        sp.edit().putString("itemFragility", "").apply();
        sp.edit().putString("itemWeight", "").apply();


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        InventoryServiceImpl newInv = new InventoryServiceImpl("http://10.0.2.2:9000/");
        ArrayList<Inventory> inventory = new ArrayList<Inventory>();
        try{
            inventory.addAll(newInv.getInventory());
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        int i = 0;
        ArrayList<String> inventoryHeaders = new ArrayList<String>();
        while(i < inventory.size()){
            inventoryHeaders.add(inventory.get(i).getDescription());
            i++;
        }



        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.simple_list_view, inventoryHeaders);
        ListView listView = (ListView) findViewById(R.id.InventoryListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sp.edit().putString("itemDescription", inventory.get(position).getDescription()).apply();
                sp.edit().putString("itemBoxID", Integer.toString(inventory.get(position).getBoxID())).apply();
                sp.edit().putString("itemWidth", Float.toString(inventory.get(position).getWidth())).apply();
                sp.edit().putString("itemLength", Float.toString(inventory.get(position).getLength())).apply();
                sp.edit().putString("itemHeight", Float.toString(inventory.get(position).getHeight())).apply();
                sp.edit().putString("itemWeight", Double.toString(inventory.get(position).getWeight())).apply();
                sp.edit().putString("itemFragility", Integer.toString(inventory.get(position).getFragility())).apply();

                Intent switchToItemView = new Intent(MoveInventoryActivity.this, ItemViewActivity.class);
                startActivity(switchToItemView);
            }
        });

        mTextView = (TextView) findViewById(R.id.text);
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
                Intent switchToMainMenu = new Intent(MoveInventoryActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(MoveInventoryActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(MoveInventoryActivity.this, BoxInputActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(MoveInventoryActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlan = new Intent(MoveInventoryActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(MoveInventoryActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(MoveInventoryActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}