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
        sp.edit().putInt("itemID", 0).apply();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        InventoryServiceImpl newInv = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig());
        ArrayList<Inventory> inventory = new ArrayList<Inventory>();
        int j = sp.getInt("loginID", 0);
        try{
            inventory.addAll(newInv.getInventory(j));
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        int i = 0;
        ArrayList<Pair<String,Integer>> inventoryHeaders = new ArrayList<Pair<String,Integer>>();
        while(i < inventory.size()){
            Pair<String, Integer> header = new Pair<String, Integer> (inventory.get(i).getDescription(), i);
            inventoryHeaders.add(header);
            i++;
        }


        EditText searchBar = (EditText) findViewById(R.id.searchBar);
        updateListView(inventoryHeaders, inventory);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Pair <String, Integer>> searchedInventoryHeaders = new ArrayList<>();
                searchedInventoryHeaders = searchForBox(s.toString(), extractHeaders(inventoryHeaders));
                updateListView(searchedInventoryHeaders, inventory);
            }
        });


    }

    /**
     * Will update the ListView object with the inventoryHeader ArrayList provided to it
     * @param newinventoryHeaders ArrayList holding inventory header strings
     * @param inventory ArrayList holding inventory details
     */
    private void updateListView(ArrayList<Pair<String, Integer>> newinventoryHeaders, ArrayList<Inventory> inventory)
    {
        ArrayList<String> headers = extractHeaders(newinventoryHeaders);

        ListView listView = (ListView) findViewById(R.id.InventoryListView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.simple_list_view, headers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int positionInInventory = newinventoryHeaders.get(position).second;

                sp.edit().putString("itemDescription", inventory.get(positionInInventory).getDescription()).apply();
                sp.edit().putString("itemBoxID", Integer.toString(inventory.get(positionInInventory).getBoxID())).apply();
                sp.edit().putString("itemWidth", Float.toString(inventory.get(positionInInventory).getWidth())).apply();
                sp.edit().putString("itemLength", Float.toString(inventory.get(positionInInventory).getLength())).apply();
                sp.edit().putString("itemHeight", Float.toString(inventory.get(positionInInventory).getHeight())).apply();
                sp.edit().putString("itemWeight", Double.toString(inventory.get(positionInInventory).getWeight())).apply();
                sp.edit().putString("itemFragility", Integer.toString(inventory.get(positionInInventory).getFragility())).apply();
                sp.edit().putInt("itemID", inventory.get(position).getId()).apply();

                Intent switchToItemView = new Intent(MoveInventoryActivity.this, ItemViewActivity.class);
                startActivity(switchToItemView);
            }
        });
    }

    private ArrayList<String> extractHeaders(ArrayList <Pair<String,Integer>> HeaderPairs)
    {
        ArrayList<String> headers = new ArrayList<>();
        int i = 0;
        while(i < HeaderPairs.size()){
            headers.add(HeaderPairs.get(i).first);
            i++;
        }

        return headers;
    }
    /**
     * Will take in the description being typed into searchbar and search inventoryHeaders arrayList for matches
     * @param inputDescription  Keyword being typed into searchbar
     * @param inventoryHeaders ArrayList that holds inventoryHeaders
     * @return searchedInventoryHeaders The new ArrayList holding matches
     */
    private ArrayList <Pair<String, Integer>> searchForBox(String inputDescription, ArrayList<String> inventoryHeaders)
    {
        ArrayList <Pair<String, Integer>> searchedInventoryHeaders = new ArrayList<>();
        try{
            int currentPosition = 0;
            for(String element : inventoryHeaders)
            {
                if (element.toLowerCase().contains(inputDescription.toLowerCase()))
                {
                    Pair <String, Integer> header = new Pair <String, Integer> (element, currentPosition);
                    searchedInventoryHeaders.add(header);

                }
                currentPosition++;
            }
        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
        }
        return searchedInventoryHeaders;
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