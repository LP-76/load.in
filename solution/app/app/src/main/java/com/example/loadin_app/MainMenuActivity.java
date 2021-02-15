package com.example.loadin_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loadin_app.ui.login.LoginActivity;

public class MainMenuActivity extends AppCompatActivity
{
    private Button tipsAndTricksButton, moveInventoryButton, loadPlanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tipsAndTricksButton = (Button) findViewById(R.id.tips_and_tricks_button);
        tipsAndTricksButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToTipsAndTricks = new Intent(MainMenuActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTipsAndTricks);
            }
        });

        moveInventoryButton = (Button) findViewById(R.id.move_inventory_button);
        moveInventoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToMoveInventory = new Intent(MainMenuActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
            }
        });

        loadPlanButton = (Button) findViewById(R.id.load_plan_button);
        loadPlanButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent switchToLoadPlan = new Intent(MainMenuActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
            }
        });
    }
}