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

        /**
         * Declaration of all the needed views and objects from the activity_feedback.xml layout file.
         */
        EditText accountCreationCommentText, itemInputCommentText, loadPlanCommentText, expertTipsCommentText, overallExperienceCommentText;
        Spinner accountCreationRatingSpinner, itemInputRatingSpinner, loadPlanRatingSpinner, expertTipsRatingSpinner;
        ToggleButton thumbsUp, thumbsDown;
        Button addNewFeedback;

        /**
         * Assigns all the needed views and objects to their matching objects in the activity_feedback.xml layout file.
         */
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


        /**
         * Listener for the thumbs up button, if button is clicked will set Boolean thumbsUp to true.
         * If button is clicked and thumbsDown button is also clicked it will unclick thumbsDown.
         */
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
        /**
         * Listener for the thumbs down button, if button is clicked will set Boolean thumbsDown to true.
         * If button is clicked and thumbsUp button is also clicked it will unclick thumbsUp.
         */
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


        /**
         * Listener for addNewFeedback button. Will call the addFeedbackToDB function and pass all values from fields.
         * The variables being passed are all converted into needed data types.
         */
        addNewFeedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addFeedbackToDB(accountCreationCommentText.getText().toString(), itemInputCommentText.getText().toString(), loadPlanCommentText.getText().toString(), expertTipsCommentText.getText().toString(),
                        overallExperienceCommentText.getText().toString(), accountCreationRatingSpinner.getSelectedItemPosition(), itemInputRatingSpinner.getSelectedItemPosition(),
                        loadPlanRatingSpinner.getSelectedItemPosition(), expertTipsRatingSpinner.getSelectedItemPosition(), thumbsup);
            }
        });


        /**
         * Toolbar variable declarations. Boilerplate for consistency with all other pages.
         */
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    /**
     * Will create a new feedback object and pass it to webAPI using the retroservice.
     *
     * @param accountCreationComment
     * @param itemInputComment
     * @param loadPlanComment
     * @param expertTipsComment
     * @param overallExperienceComment
     * @param accountCreationRating
     * @param itemInputRating
     * @param loadPlanRating
     * @param expertTipsRating
     * @param thumbsUp The way I have this, if thumbsUp button is activated then boolean thumbsUp will be true, so a 1 will be sent
     *                  if the button is not activated which is only possible if thumbs down is activated boolean thumbsUp will be false so it will send as a 0.
     *                  Felt this was easier way to determine the result of that rather than send both booleans and do a check.
     */
    private void addFeedbackToDB(String accountCreationComment, String itemInputComment, String loadPlanComment, String expertTipsComment, String overallExperienceComment,
                                 Integer accountCreationRating, Integer itemInputRating, Integer loadPlanRating, Integer expertTipsRating, Boolean thumbsUp)
    {
        /**
         * If/else to check status of boolean thumbsUp as discussed above.
         */
        Feedback newFeedback = new Feedback();
        if(thumbsUp == true)
        {
            newFeedback.setOverallExperienceRating(1);
        }
        else
        {
            newFeedback.setOverallExperienceRating(0);
        }
        /**
         * Assignment of all the relevant data into new feedback object data members.
         * The ratings are +1 because the spinner index begins at 0, adjusted to reflect true rating.
         * Otherwise a 5 rating would be a 4.
         */
        newFeedback.setUserID(sp.getInt("loginID", 0));
        newFeedback.setAccountCreationComment(accountCreationComment);
        newFeedback.setAccountCreationRating(accountCreationRating+1);
        newFeedback.setItemInputComment(itemInputComment);
        newFeedback.setItemInputRating(itemInputRating+1);
        newFeedback.setLoadPlanComment(loadPlanComment);
        newFeedback.setLoadPlanRating(loadPlanRating+1);
        newFeedback.setExpertTipsComment(expertTipsComment);
        newFeedback.setExpertTipsRating(expertTipsRating+1);
        newFeedback.setOverallExperienceComment(overallExperienceComment);

        /**
         * Gets current user information
         */
        LoadInApplication app = (LoadInApplication)getApplication();
        String username = app.getCurrentUser().getEmail();
        String password = app.getCurrentUser().getPassword();


        /**
         * Will call FeedbackServiceImpl to pass the new feedback object into the webAPI
         */
        try{
            FeedbackServiceImpl service = new FeedbackServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
            service.addFeedback(newFeedback);

            Toast.makeText(FeedbackActivity.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();
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
                Intent switchToBoxInput = new Intent(FeedbackActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(FeedbackActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(FeedbackActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(FeedbackActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
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