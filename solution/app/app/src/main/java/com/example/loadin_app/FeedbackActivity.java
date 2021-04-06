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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.FeedbackServiceImpl;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

import odu.edu.loadin.common.Feedback;

public class FeedbackActivity extends AppCompatActivity {

    public static SharedPreferences sp;
    boolean thumbsup, thumbsdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(FeedbackActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }


        EditText accountCreationCommentText, itemInputCommentText, loadPlanCommentText, expertTipsCommentText, overallExperienceCommentText;
        Spinner accountCreationRatingSpinner, itemInputRatingSpinner, loadPlanRatingSpinner, expertTipsRatingSpinner;
        ToggleButton thumbsUp, thumbsDown;
        Button addNewFeedback;

        thumbsUp = findViewById(R.id.overallExperienceThumbsUpButton);
        thumbsDown = findViewById(R.id.overallExperienceThumbsDownButton);
        accountCreationRatingSpinner = findViewById(R.id.account_login_spinner);
        itemInputRatingSpinner = findViewById(R.id.itemInputSpinner);
        loadPlanRatingSpinner = findViewById(R.id.loadPlanSpinner);
        expertTipsRatingSpinner = findViewById(R.id.expertTipsSpinner);
        accountCreationCommentText = findViewById(R.id.accountLoginComments);
        itemInputCommentText = findViewById(R.id.itemInputComments);
        loadPlanCommentText = findViewById(R.id.loadPlanComments);
        expertTipsCommentText = findViewById(R.id.expertTipsComments);
        overallExperienceCommentText = findViewById(R.id.overallExperienceComments);
        addNewFeedback = findViewById(R.id.submitFeedBackButton);


        thumbsUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    thumbsup = true;
                    thumbsDown.setChecked(false);
                }
                else
                {
                    thumbsup = false;
                }

            }
        });

        thumbsDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    thumbsdown = true;
                    thumbsUp.setChecked(false);
                }
                else
                {
                    thumbsdown = false;
                }

            }
        });


        addNewFeedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addFeedbackToDB(accountCreationCommentText.getText().toString(), itemInputCommentText.getText().toString(), loadPlanCommentText.getText().toString(), expertTipsCommentText.getText().toString(),
                        overallExperienceCommentText.getText().toString(), Integer.parseInt(accountCreationRatingSpinner.getSelectedItem().toString()), Integer.parseInt(itemInputRatingSpinner.getSelectedItem().toString()),
                        Integer.parseInt(loadPlanRatingSpinner.getSelectedItem().toString()), Integer.parseInt(expertTipsRatingSpinner.getSelectedItem().toString()), thumbsup);
            }
        });


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    private void addFeedbackToDB(String accountCreationComment, String itemInputComment, String loadPlanComment, String expertTipsComment, String overallExperienceComment,
                                 Integer accountCreationRating, Integer itemInputRating, Integer loadPlanRating, Integer expertTipsRating, Boolean thumbsup)
    {
        Feedback newFeedback = new Feedback();
        if(thumbsup == true)
        {
            newFeedback.setOverallExperienceRating(1);
        }
        else
        {
            newFeedback.setOverallExperienceRating(0);
        }
        newFeedback.setUserID(sp.getInt("loginID", 0));
        newFeedback.setAccountCreationComment(accountCreationComment);
        newFeedback.setAccountCreationRating(accountCreationRating);
        newFeedback.setItemInputComment(itemInputComment);
        newFeedback.setItemInputRating(itemInputRating);
        newFeedback.setLoadPlanComment(loadPlanComment);
        newFeedback.setLoadPlanRating(loadPlanRating);
        newFeedback.setExpertTipsComment(expertTipsComment);
        newFeedback.setExpertTipsRating(expertTipsRating);
        newFeedback.setOverallExperienceComment(overallExperienceComment);

        LoadInApplication app = (LoadInApplication)getApplication();
        String username = app.getCurrentUser().getEmail();
        String password = app.getCurrentUser().getPassword();


        try{
            FeedbackServiceImpl service = new FeedbackServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
            service.addFeedback(newFeedback);
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
                Intent switchToMainMenu = new Intent(FeedbackActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(FeedbackActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(FeedbackActivity.this, BoxInputActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(FeedbackActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlan = new Intent(FeedbackActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(FeedbackActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(FeedbackActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}