package com.example.loadin_app;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.loadin_app.data.services.BoxServiceImpl;
import com.example.loadin_app.data.services.ExpertArticleImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;

public class BoxInputActivity extends AppCompatActivity
{
    private EditText descriptionInput, widthInput, depthInput, heightInput;
    private Button addBoxSizeButton;

    //used this video a bit for reference here https://youtu.be/V0AETAjxqLI -jason
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_input);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        descriptionInput = (EditText) findViewById(R.id.BoxDescriptionField);
        widthInput = (EditText) findViewById(R.id.BoxWidthField);
        depthInput = (EditText) findViewById(R.id.BoxDepthField);
        heightInput = (EditText) findViewById(R.id.BoxHeightField);

        addBoxSizeButton = (Button) findViewById(R.id.AddBoxSizeButton);
        addBoxSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AddBoxSizeToDB(descriptionInput.getText().toString(), Float.parseFloat(widthInput.getText().toString()), Float.parseFloat(depthInput.getText().toString()), Float.parseFloat(heightInput.getText().toString()));
            }
        });
    }

    private void AddBoxSizeToDB(String inputDescription, float inputWidth, float inputDepth, float inputHeight)
    {
        System.out.println("Creating a box of width: " + inputWidth + ", depth: " + inputDepth +", height: " + inputHeight + ", description: " + inputDescription + "!");
        //beep boop do DB stuff or whatever

        String dimension = inputWidth + "x" + inputHeight + "x" + inputDepth;


        //we're going to use the box service implementation to acheive this
        BoxSize bs = new BoxSize();
        bs.setDescription(inputDescription);
        bs.setDimensions(dimension);

        BoxServiceImpl service = new BoxServiceImpl("http://10.0.2.2:9000/");
        try{
            service.addBoxSize(bs);
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
                Intent switchToMainMenu = new Intent(BoxInputActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(BoxInputActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(BoxInputActivity.this, BoxInputActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(BoxInputActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlan = new Intent(BoxInputActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(BoxInputActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(BoxInputActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}