package com.example.loadin_app;

import android.content.Intent;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



public class TipsAndTricksActivity extends AppCompatActivity {


    private String videoLink = "cardboard.mp4";
    private Button searchForArticle;
    private ImageView imageView;
    private String keyword;
    private EditText articleKeyword;
    private String value;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    private boolean playWhenReady = true;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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


        /*
        Checks the file extension of media file to see if it is an image or video.
        At the moment this is a very janky approach as only .jpg or .mp4 will be accepted.
        Depending on what media type is found it will set their respective view to visible
        If a video is detected then it will also start the mediaplayer activity.
         */
        if (videoLink.toString().endsWith(".jpg"))
        {
            imageView = findViewById(R.id.articleImage);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.cardboard);
        }
        else if (videoLink.toString().endsWith(".mp4"))
        {
            initializePlayer(videoLink);
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
                Intent switchToBoxInput = new Intent(TipsAndTricksActivity.this, BoxInputActivity.class);
                startActivity(switchToBoxInput);
                finish();
                return true;

            case R.id.action_move_inventory:
                Intent switchToMoveInventory = new Intent(TipsAndTricksActivity.this, MoveInventoryActivity.class);
                startActivity(switchToMoveInventory);
                finish();
                return true;

            case R.id.action_load_plan:
                Intent switchToLoadPlan = new Intent(TipsAndTricksActivity.this, LoadPlanActivity.class);
                startActivity(switchToLoadPlan);
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
        System.out.println("Searching for an article with keyword: " + keyword + "!");

        ExpertArticleImpl service = new ExpertArticleImpl(BaseServiceUrlProvider.getCurrentConfig());
        ExpertArticle expertArticle = new ExpertArticle();
        try{

            expertArticle = service.getExpertArticles(keyword);

            RelativeLayout relativelayout1 = findViewById(R.id.relativelayout1);
            Toolbar toolbar = findViewById(R.id.toolbar);
            TextView mArticleContent = findViewById(R.id.articleContent);
            TextView mArticleTitle = findViewById(R.id.articleTitle);

            if((keyword.equals("grinch")))
            {
                toolbar.setBackgroundColor(Color.parseColor("#32E300"));
                relativelayout1.setBackgroundColor(Color.parseColor("#32E300"));
                mArticleContent.setTextColor(Color.parseColor("#6DD1A1"));
                mArticleContent.setBackgroundColor(Color.WHITE);
                mArticleTitle.setTextColor(Color.parseColor("#6DD1A1"));
                mArticleTitle.setBackgroundColor(Color.WHITE);
            }
            else
            {
                toolbar.setBackgroundColor(Color.parseColor("#6DD1A1"));
                relativelayout1.setBackgroundColor(Color.WHITE);
                mArticleContent.setTextColor(Color.parseColor("#6DD1A1"));
                mArticleTitle.setTextColor(Color.parseColor("#6DD1A1"));
            }
            mArticleContent.setText(expertArticle.getArticleContent());
            mArticleTitle.setText(expertArticle.getArticleTitle());
            videoLink = expertArticle.getVisualFile();

            initializePlayer(videoLink);


        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
        }
        releasePlayer();
    }

    private void initializePlayer(String videoLink) {

        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        playerView = findViewById(R.id.articleVideo);
        playerView.setVisibility(View.VISIBLE);
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri(videoLink);
        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(true);
        player.prepare();

        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
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