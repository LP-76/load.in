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
import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.ExpertArticle;
import odu.edu.loadin.common.LoadPlanBox;
import odu.edu.loadin.common.User;

public class ItemViewActivity extends AppCompatActivity {

    public static SharedPreferences sp;
    private String keyword, activityTitle;
    private TableLayout table;
    private Button deleteItemButton;
    private Button boxLocatorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        // THIS IS THE PERSISTENT LOGIN STUFF, UNCOMMENT FOR LOGIN REQUIREMENT
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        if(sp.getInt("loginID", 0) == 0){
            Intent switchToLogin = new Intent(ItemViewActivity.this, LoginActivity.class);
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

        searchForArticle(sp.getString("itemDescription", ""));

        TextView idH = (TextView) findViewById(R.id.item_boxID_header);
        idH.setText("Box Number:");
        TextView idV = (TextView) findViewById(R.id.item_boxID_value);
        idV.setText(sp.getString("itemBoxID", ""));

        activityTitle = "Box #" + sp.getString("itemBoxID", "");
        TextView title = (TextView) findViewById(R.id.BoxViewTitle);
        title.setText(activityTitle);

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

        TextView rH = (TextView) findViewById(R.id.item_room_header);
        rH.setText("Room:");
        TextView rV = (TextView) findViewById(R.id.item_room_value);
        rV.setText(sp.getString("itemRoom", ""));

        TextView lH = (TextView) findViewById(R.id.item_list_header);
        lH.setText("List of Items:");
        TextView lV = (TextView) findViewById(R.id.item_list_value);
        lV.setText(sp.getString("itemList", ""));

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
                    test.deleteItem(sp.getInt("itemID",0));

                    Toast.makeText(ItemViewActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                    Intent switchToInventory = new Intent(ItemViewActivity.this, MoveInventoryActivity.class);
                    startActivity(switchToInventory);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        boxLocatorButton = (Button) findViewById(R.id.FindBox);
        boxLocatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                LoadInApplication app = (LoadInApplication)getApplication();
                User currentUser = app.getCurrentUser();
                LoadPlanBoxServiceImpl loadPlanBoxService = new LoadPlanBoxServiceImpl(BaseServiceUrlProvider.getCurrentConfig(), currentUser.getEmail(), currentUser.getPassword());
                ArrayList<LoadPlanBox> loadPlanBoxes = new ArrayList<>();
                try {
                    loadPlanBoxes = loadPlanBoxService.getLoadPlan(sp.getInt("loginID",0));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(loadPlanBoxes.size() == 0)
                {
                    Toast.makeText(ItemViewActivity.this, "Error: No Load Plan has been found. Please use the Logistics Page.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent switchToBoxLocatorActivity = new Intent(ItemViewActivity.this, OpenGlBoxLocatorActivity.class);
                    int id = Integer.parseInt(sp.getString("itemBoxID",""));
                    //sp.edit().putInt("id",id).apply();
                    switchToBoxLocatorActivity.putExtra("id",id);
                    startActivity(switchToBoxLocatorActivity);
                    finish();
                }

            }
        });

    }

    public void viewExpertTips(View view){
        Intent switchToExpertTips = new Intent(ItemViewActivity.this, TipsAndTricksActivity.class);
        switchToExpertTips.putExtra(keyword, sp.getString("itemDescription", ""));
        startActivity(switchToExpertTips);
        finish();
    }

    public void editInventory(View view){
        Intent switchToEditItem = new Intent(ItemViewActivity.this, EditItemActivity.class);
        startActivity(switchToEditItem);
        finish();
    }


    public void addItem(View view){
        Intent switchToAddItem = new Intent(ItemViewActivity.this, AddItemActivity.class);
        startActivity(switchToAddItem);
        finish();
    }


    private void searchForArticle(String inputDescription)
    {
        LoadInApplication app = (LoadInApplication) getApplication();
        String username = app.getCurrentUser().getEmail();
        String password = app.getCurrentUser().getPassword();
        TextView viewExpertTipsText = (TextView) findViewById(R.id.ViewExpertTipsText);
        ExpertArticleImpl service = new ExpertArticleImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
        ArrayList<ExpertArticle> expertArticle = new ArrayList<ExpertArticle>();
        try{
            expertArticle = service.getExpertArticles(inputDescription);

            if(expertArticle.size() > 0)
            {
                viewExpertTipsText.setVisibility(View.VISIBLE);
            }

        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
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
                Intent switchToMainMenu = new Intent(ItemViewActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(ItemViewActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(ItemViewActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(ItemViewActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(ItemViewActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(ItemViewActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(ItemViewActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(ItemViewActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}