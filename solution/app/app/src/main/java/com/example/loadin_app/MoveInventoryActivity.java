package com.example.loadin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.ExpertArticleImpl;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.ui.MoveInventoryAdapter;
import com.example.loadin_app.ui.login.LoginActivity;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.ArrayList;

import odu.edu.loadin.common.ExpertArticle;
import odu.edu.loadin.common.Inventory;

public class MoveInventoryActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText searchBar;
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
        sp.edit().putString("itemBoxID", "").apply();
        sp.edit().putString("itemWidth", "").apply();
        sp.edit().putString("itemLength", "").apply();
        sp.edit().putString("itemHeight", "").apply();
        sp.edit().putString("itemFragility", "").apply();
        sp.edit().putString("itemWeight", "").apply();
        sp.edit().putString("itemStatus", "").apply();
        sp.edit().putString("itemList", "").apply();
        sp.edit().putString("itemRoom", "").apply();
        sp.edit().putInt("itemID", 0).apply();

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

        InventoryServiceImpl newInv = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
        ArrayList<Inventory> inventory = new ArrayList<Inventory>();
        int j = sp.getInt("loginID", 0);
        try{
            inventory.addAll(newInv.getInventory(j));
        }
        catch(Exception ex){
            System.out.println(ex);
        }


        EditText searchBar = (EditText) findViewById(R.id.searchBar);
        updateListView(inventory);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Inventory> searchedInventory = new ArrayList<>();
                searchedInventory = searchForBox(inventory, s.toString());
                updateListView(searchedInventory);
            }

        });
    }

    private void updateListView(ArrayList<Inventory> searchedInventory)
    {

        ListView listView = findViewById(R.id.InventoryListView);
        MoveInventoryAdapter adapter = new MoveInventoryAdapter(this, R.layout.move_inventory_listview, searchedInventory);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int temp = (int) searchedInventory.get(position).getWeight();
                sp.edit().putString("itemDescription", searchedInventory.get(position).getDescription()).apply();
                sp.edit().putString("itemBoxID", Integer.toString(searchedInventory.get(position).getBoxID())).apply();
                sp.edit().putString("itemWidth", Float.toString(searchedInventory.get(position).getWidth())).apply();
                sp.edit().putString("itemLength", Float.toString(searchedInventory.get(position).getLength())).apply();
                sp.edit().putString("itemHeight", Float.toString(searchedInventory.get(position).getHeight())).apply();
                sp.edit().putString("itemWeight", Integer.toString(temp)).apply();
                sp.edit().putString("itemFragility", Integer.toString(searchedInventory.get(position).getFragility())).apply();
                sp.edit().putString("itemStatus", searchedInventory.get(position).getStatus()).apply();
                sp.edit().putString("itemList", searchedInventory.get(position).getItemList()).apply();
                sp.edit().putString("itemRoom", searchedInventory.get(position).getRoom()).apply();
                sp.edit().putInt("itemID", searchedInventory.get(position).getId()).apply();

                Intent switchToItemView = new Intent(MoveInventoryActivity.this, ItemViewActivity.class);
                startActivity(switchToItemView);
                finish();
            }
        });
    }

    private ArrayList<Inventory> searchForBox(ArrayList<Inventory> originalInventory, String searchWord)
    {
        ArrayList<Inventory> searchedInventory = new ArrayList<>();
        try{
            for(int i = 0; i < originalInventory.size(); i++)
            {
                Inventory inventory = originalInventory.get(i);
                String boxIDnum;
                Integer boxID;
                boxID = originalInventory.get(i).getBoxID();
                boxIDnum = boxID.toString();
                if(inventory.getDescription() != null&&inventory.getDescription().toLowerCase().contains(searchWord.toLowerCase()))
                {
                    searchedInventory.add(originalInventory.get(i));
                }
                else if (inventory.getRoom() != null&&inventory.getRoom().toLowerCase().contains(searchWord.toLowerCase()))
                {
                    searchedInventory.add(originalInventory.get(i));
                }
                else if(inventory.getStatus() !=null&&inventory.getStatus().toLowerCase().contains(searchWord.toLowerCase()))
                {
                    searchedInventory.add(originalInventory.get(i));
                }
                else if(boxIDnum != null && boxIDnum.toLowerCase().contains(searchWord.toLowerCase()))
                {
                    searchedInventory.add(originalInventory.get(i));
                }
                else if(inventory.getItemList() != null && inventory.getItemList().toLowerCase().contains(searchWord.toLowerCase()))
                {
                    searchedInventory.add(originalInventory.get(i));
                }

            }
        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
        }
        return searchedInventory;
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
                Intent switchToBoxInput = new Intent(MoveInventoryActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(MoveInventoryActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(MoveInventoryActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(MoveInventoryActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
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
