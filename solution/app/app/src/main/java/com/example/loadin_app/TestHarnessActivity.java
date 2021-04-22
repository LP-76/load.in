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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.ExpertArticleImpl;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class TestHarnessActivity extends AppCompatActivity {

    private EditText randomItemsNumber;
    private Button deleteAllItemButton, generateRandomInventoryButton, resetItemStatus;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_harness);

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(TestHarnessActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }

        LoadInApplication app = (LoadInApplication) getApplication();
        String username = app.getCurrentUser().getEmail();
        String password = app.getCurrentUser().getPassword();


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);



        deleteAllItemButton = (Button) findViewById(R.id.delete_all_items);
        deleteAllItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                InventoryServiceImpl service = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);


                try {
                    service.deleteAllItem( sp.getInt("loginID", 0) );

                    Toast.makeText(TestHarnessActivity.this, "Inventory Deleted", Toast.LENGTH_SHORT).show();
                    //Intent switchToInventory = new Intent(ItemViewActivity.this, MoveInventoryActivity.class);
                    //startActivity(switchToInventory);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        generateRandomInventoryButton = (Button) findViewById(R.id.generate_random_inventory);
        generateRandomInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                randomItemsNumber = (EditText) findViewById(R.id.number_of_random_boxes);
                int randItems = Integer.parseInt(randomItemsNumber.getText().toString());

                InventoryServiceImpl service = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);


                try {
                    //TODO: NEED TO PASS IN THE NUMBER OF ITEMS TO BE GENERATED PASS IN "randItems"
                    service.insertRandomItem( sp.getInt("loginID", 0), randItems + 1 );

                    Toast.makeText(TestHarnessActivity.this, "Random Inventory Generated", Toast.LENGTH_SHORT).show();
                    //Intent switchToInventory = new Intent(ItemViewActivity.this, MoveInventoryActivity.class);
                    //startActivity(switchToInventory);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        resetItemStatus = (Button) findViewById(R.id.reset_status);
        resetItemStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                InventoryServiceImpl service = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);


                try {
                    service.setUserInventoryStatus( sp.getInt("loginID", 0) );

                    Toast.makeText(TestHarnessActivity.this, "Status Reset", Toast.LENGTH_SHORT).show();
                    //Intent switchToInventory = new Intent(ItemViewActivity.this, MoveInventoryActivity.class);
                    //startActivity(switchToInventory);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


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
                Intent switchToMainMenu = new Intent(TestHarnessActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(TestHarnessActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(TestHarnessActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(TestHarnessActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(TestHarnessActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(TestHarnessActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(TestHarnessActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(TestHarnessActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
