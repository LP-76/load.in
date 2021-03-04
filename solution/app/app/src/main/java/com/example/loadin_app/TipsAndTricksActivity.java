package com.example.loadin_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.loadin_app.data.services.ExpertArticleImpl;
import odu.edu.loadin.common.ExpertArticle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.w3c.dom.Text;

import java.io.IOException;

//TODO: Paul: Add Documentation to video playback methods.
public class TipsAndTricksActivity extends AppCompatActivity {

    private TextView mTextView;
    private static final String mediaName = "cardboard.jpg";
    private VideoView mVideoView;
    private TextView mBufferingTextView;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";
    private Button searchForArticle;
    private ImageView imageView;
    private EditText articleKeyword;
    private String keyword;
    private String value;

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
        if (mediaName.toString().endsWith(".jpg"))
        {
            mVideoView = findViewById(R.id.articleVideo);
            imageView = findViewById(R.id.articleImage);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.cardboard);
        }
        else if (mediaName.toString().endsWith(".mp4"))
        {
            mVideoView = findViewById(R.id.articleVideo);
            mVideoView.setVisibility(View.VISIBLE);
            if(savedInstanceState != null)
            {
                mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
            }
            MediaController controller = new MediaController(this);
            controller.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(controller);
            mBufferingTextView = findViewById(R.id.buffering_textview);
            initializePlayer();
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

        ExpertArticleImpl service = new ExpertArticleImpl("http://10.0.2.2:9000/");
        ExpertArticle expertArticle = new ExpertArticle();
        try{
           expertArticle = service.getExpertArticles(keyword);
            TextView mArticleContent = findViewById(R.id.articleContent);
            TextView mArticleTitle = findViewById(R.id.articleTitle);
            mArticleContent.setText(expertArticle.getArticleContent());
            mArticleTitle.setText(expertArticle.getArticleTitle());
        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
        }
    }

    /**
     * Will check if the media file being provided is a url or is locally stored
     * TODO This is now broken and won't work with locally stored files? Not sure why yet. - Paul
     * @param mediaName
     * @return valid uri of the media file
     */
    private Uri getMedia(String mediaName)
    {
        if(URLUtil.isValidUrl(mediaName))
        {
            return Uri.parse(mediaName);
        }
        else
            {
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + mediaName);
        }

    }

    /**
     * Will initialize the video player being used for tips and tricks
     * Will call the getMedia(String mediaName) function to retrieve file name
     * Todo: Should probably modify this to have it accept the media name and then pass it into the getMedia method - Paul
     * This is the main driver of the video playing feature.
     */
    private void initializePlayer() {
        Uri videoUri = getMedia(mediaName);
        mBufferingTextView.setVisibility(VideoView.VISIBLE);
        mVideoView.setVideoURI(videoUri);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                if(mCurrentPosition > 0){
                    mVideoView.seekTo(mCurrentPosition);
                }
                else
                {
                    mVideoView.seekTo(1);
                }
                mVideoView.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(TipsAndTricksActivity.this, "Playback completed", Toast.LENGTH_SHORT).show();
                mVideoView.seekTo(0);
            }
        });
        releasePlayer();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }
    private void releasePlayer()
    {
        mVideoView.stopPlayback();
    }
    @Override
    protected void onPause()
    {
        super.onPause();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

}