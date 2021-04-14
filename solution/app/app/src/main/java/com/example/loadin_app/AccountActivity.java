package com.example.loadin_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.UserServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

import java.util.ArrayList;

import odu.edu.loadin.common.User;

public class AccountActivity extends AppCompatActivity {

    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }
        LoadInApplication app = (LoadInApplication) getApplication();
        String username = app.getCurrentUser().getEmail();
        String password = app.getCurrentUser().getPassword();
        UserServiceImpl service = new UserServiceImpl(BaseServiceUrlProvider.getCurrentConfig());
        User user = new User();
        int j = sp.getInt("loginID", 0);
        try{
            user = service.getUser(j, username, password);
        }
        catch(Exception ex){
            System.out.println(ex);
        }


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        TextView emailH = (TextView) findViewById(R.id.account_email_header);
        emailH.setText("Email:");
        TextView emailV = (TextView) findViewById(R.id.account_email__value);
        emailV.setText(user.getEmail());

        TextView fNameH = (TextView) findViewById(R.id.account_fname_header);
        fNameH.setText("First Name:");
        TextView fNameV = (TextView) findViewById(R.id.account_fname_value);
        fNameV.setText(user.getFirstName());

        TextView lNameH = (TextView) findViewById(R.id.account_lname_header);
        lNameH.setText("Last Name:");
        TextView lNameV = (TextView) findViewById(R.id.account_lname_value);
        lNameV.setText(user.getLastName());

        TextView phoneH = (TextView) findViewById(R.id.account_phone_header);
        phoneH.setText("Phone Number:");
        TextView phoneV = (TextView) findViewById(R.id.account_phone_value);
        phoneV.setText(user.getPhoneNumber());

    }

    public void logout(View view){
        Intent logout = new Intent(AccountActivity.this, LoginActivity.class);
        startActivity(logout);
        finish();
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
                Intent switchToMainMenu = new Intent(AccountActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(AccountActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(AccountActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(AccountActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(AccountActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(AccountActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(AccountActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(AccountActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}