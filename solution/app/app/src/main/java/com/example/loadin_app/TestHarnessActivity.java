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

    private Button deleteItemButton;
    public static SharedPreferences sp;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        /*
        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(MainMenuActivity.this, LoginActivity.class);
            startActivity(switchToLogin);
        }


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        String dimensions = sp.getString("itemWidth", "") + " x " + sp.getString("itemLength", "") + " x " + sp.getString("itemHeight", "");

        TextView descH = (TextView) findViewById(R.id.item_description_header);
        descH.setText("Description:");
        TextView descV = (TextView) findViewById(R.id.item_description_value);
        descV.setText(sp.getString("itemDescription", ""));


        TextView idH = (TextView) findViewById(R.id.item_boxID_header);
        idH.setText("Box Number:");
        TextView idV = (TextView) findViewById(R.id.item_boxID_value);
        idV.setText(sp.getString("itemBoxID", ""));

        TextView dimH = (TextView) findViewById(R.id.item_dimensions_header);
        dimH.setText("Dimensions:");
        TextView dimV = (TextView) findViewById(R.id.item_dimensions_value);
        dimV.setText(dimensions);

        TextView wH = (TextView) findViewById(R.id.item_weight_header);
        wH.setText("Weight:");
        TextView wV = (TextView) findViewById(R.id.item_weight_value);
        wV.setText(sp.getString("itemWeight", ""));

        TextView fH = (TextView) findViewById(R.id.item_fragility_header);
        fH.setText("Fragility:");
        TextView fV = (TextView) findViewById(R.id.item_fragility_value);
        fV.setText(sp.getString("itemFragility", ""));

        deleteItemButton = (Button) findViewById(R.id.deleteItemButton);

        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoadInApplication app = (LoadInApplication) getApplication();
                String username = app.getCurrentUser().getEmail();
                String password = app.getCurrentUser().getPassword();

                InventoryServiceImpl test = new InventoryServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);



                try {
                    //TODO: NEED TO PASS IN THE USER_ID + ITEM ID
                    //test.deleteAllItem( USER_ID , ITEM_ID);

                    //Toast.makeText(ItemViewActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
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
*/






}
