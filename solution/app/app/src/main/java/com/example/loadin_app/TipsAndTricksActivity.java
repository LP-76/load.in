package com.example.loadin_app;

import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.ExpertArticleImpl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import odu.edu.loadin.common.ExpertArticle;
import odu.edu.loadin.common.Inventory;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;


public class TipsAndTricksActivity extends AppCompatActivity {


    private String videoLink = "cardboard.mp4";
    private Button searchForArticle;
    private String keyword;
    private EditText articleKeyword;
    private String value;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView mArticleContent;
    private TextView mArticleTitle;
    private ListView listView;
    private Toolbar toolbar;
    private RelativeLayout relativelayout1;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_and_tricks);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            value = extras.getString(keyword);
            System.out.println(value);
        }
        searchForArticle(value);

        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        articleKeyword = (EditText) findViewById(R.id.articleSearchTool);
        searchForArticle = (Button) findViewById(R.id.searchForArticle);

        searchForArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                searchForArticle(articleKeyword.getText().toString());
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
                Intent switchToMainMenu = new Intent(TipsAndTricksActivity.this, MainMenuActivity.class);
                startActivity(switchToMainMenu);
                finish();
                return true;

            case R.id.action_tips_and_tricks:
                Intent switchToTips = new Intent(TipsAndTricksActivity.this, TipsAndTricksActivity.class);
                startActivity(switchToTips);
                finish();
                return true;

            case R.id.action_box_input:
                Intent switchToBoxInput = new Intent(TipsAndTricksActivity.this, AddItemActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(TipsAndTricksActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_logistics:
                Intent switchToLogistics = new Intent(TipsAndTricksActivity.this, LogisticsActivity.class);
                startActivity(switchToLogistics);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlanNavigator = new Intent(TipsAndTricksActivity.this, LoadPlanNavigatorActivity.class);
                startActivity(switchToLoadPlanNavigator);
                finish();
                return true;

            case R.id.action_feedback:

                Intent switchToFeedback = new Intent(TipsAndTricksActivity.this, FeedbackActivity.class);
                startActivity(switchToFeedback);
                finish();

                return true;

            case R.id.action_account:

                Intent switchToAccount = new Intent(TipsAndTricksActivity.this, AccountActivity.class);
                startActivity(switchToAccount);
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Searches for an article based on keyword provided.
     *
     * @param keyword
     */
    private void searchForArticle(String keyword)
    {
        releasePlayer();
        LoadInApplication app = (LoadInApplication) getApplication();
        String username = app.getCurrentUser().getEmail();
        String password = app.getCurrentUser().getPassword();
        mArticleContent = findViewById(R.id.articleContent);
        mArticleTitle = findViewById(R.id.articleTitle);
        playerView = findViewById(R.id.articleVideo);
        listView = (ListView) findViewById(R.id.ExpertListView);
        relativelayout1 = findViewById(R.id.relativelayout1);
        toolbar = findViewById(R.id.toolbar);

        ExpertArticleImpl service = new ExpertArticleImpl(BaseServiceUrlProvider.getCurrentConfig(), username, password);
        try{

            ArrayList<ExpertArticle> foundArticles = service.getExpertArticles(keyword);
            ExpertArticle expertArticle;

            if(foundArticles.size() > 1)
            {
                setExpertArticleComponentsInvisible();
                setListViewComponentsVisible();
                int i = 0;
                ArrayList<Pair<String,Integer>> articleTitles = new ArrayList<Pair<String,Integer>>();
                while(i < foundArticles.size()){
                    Pair<String, Integer> header = new Pair<String, Integer> (foundArticles.get(i).getArticleTitle(), i);
                    articleTitles.add(header);
                    i++;
                }

                updateListView(articleTitles, foundArticles);
            }
            else {

                setExpertArticleComponentsVisible();
                setListViewComponentsInvisible();
                expertArticle = foundArticles.get(0);

                if ((keyword.equals("grinch")))
                {
                    setExpertArticleComponentsColors("#32E300", "#32E300", "#ffffff", "#ffffff","#6DD1A1", "#6DD1A1");
                }
                else if(expertArticle.getArticleTitle().equals(""))
                {
                    Toast.makeText(TipsAndTricksActivity.this, "Sorry, we don't have a tip for this at the moment. But we saved it for future updates!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    setExpertArticleComponentsColors("#6DD1A1", "#ffffff", "#ffffff", "#ffffff","#6DD1A1", "#6DD1A1");
                }
                mArticleContent.setText(expertArticle.getArticleContent());
                mArticleTitle.setText(expertArticle.getArticleTitle());
                videoLink = expertArticle.getVisualFile();
            }
            initializePlayer(videoLink);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        releasePlayer();
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

    private void updateListView(ArrayList<Pair<String, Integer>> newArticleTitles, ArrayList<ExpertArticle> foundArticles)
    {
        ArrayList<String> headers = extractHeaders(newArticleTitles);

        ListView listView = (ListView) findViewById(R.id.ExpertListView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.simple_list_view, headers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int positionInInventory = newArticleTitles.get(position).second;

                mArticleContent.setText(foundArticles.get(positionInInventory).getArticleContent());
                mArticleTitle.setText(foundArticles.get(positionInInventory).getArticleTitle());
                videoLink = foundArticles.get(positionInInventory).getVisualFile();

                setListViewComponentsInvisible();
                setExpertArticleComponentsVisible();
                setExpertArticleComponentsColors("#6DD1A1", "#ffffff", "#ffffff", "#ffffff","#6DD1A1", "#6DD1A1");
                initializePlayer(videoLink);
            }
        });
    }

    private void setExpertArticleComponentsColors(String toolbarColor, String relativeLayoutColor, String titleBackgroundColor, String contentBackgroundColor, String titleTextColor, String contentTextColor)
    {
        toolbar.setBackgroundColor(Color.parseColor(toolbarColor));
        relativelayout1.setBackgroundColor(Color.parseColor(relativeLayoutColor));
        mArticleContent.setTextColor(Color.parseColor(contentTextColor));
        mArticleContent.setBackgroundColor(Color.parseColor(contentBackgroundColor));
        mArticleTitle.setBackgroundColor(Color.parseColor(titleBackgroundColor));
        mArticleTitle.setTextColor(Color.parseColor(titleTextColor));
    }
    private void setExpertArticleComponentsInvisible()
    {
        mArticleContent.setVisibility(View.INVISIBLE);
        mArticleTitle.setVisibility(View.INVISIBLE);
        playerView.setVisibility(View.INVISIBLE);
    }
    private void setExpertArticleComponentsVisible()
    {
        mArticleContent.setVisibility(View.VISIBLE);
        mArticleTitle.setVisibility(View.VISIBLE);
        playerView.setVisibility(View.VISIBLE);
    }
    private void setListViewComponentsInvisible()
    {
        listView.setVisibility(View.GONE);
    }
    private void setListViewComponentsVisible()
    {
        listView.setVisibility(View.VISIBLE);
    }
    private void initializePlayer(String videoLink) {

        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri(videoLink);
        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(true);
        player.prepare();

        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        initializePlayer("");
    }
    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }
    @Override
    protected void onPause()
    {
        super.onPause();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            player.pause();
        }
    }

}