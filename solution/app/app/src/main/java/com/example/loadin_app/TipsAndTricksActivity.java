package com.example.loadin_app;

import android.content.Intent;
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
    private static final String VIDEO_SAMPLE = "eric_andre";
    private VideoView mVideoView;
    private TextView mBufferingTextView;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";
    private Button searchForArticle;
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

        mVideoView = findViewById(R.id.articleVideo);

        if(savedInstanceState != null)
        {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);
        mBufferingTextView = findViewById(R.id.buffering_textview);


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
           System.out.println("Article Keyword is: " + expertArticle.getKeyword());
           System.out.println("Article Content is: " + expertArticle.getArticleContent());
           System.out.println("Article Title is: " + expertArticle.getArticleTitle());
           System.out.println("Article File is: " + expertArticle.getVisualFile());
           System.out.println("Article Created at: " + expertArticle.getCreatedAt());
           System.out.println("Article Updated at: " + expertArticle.getUpdatedAt());

        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
        }
    }
    private Uri getMedia(String mediaName) {
        if(URLUtil.isValidUrl(mediaName)) {
            return Uri.parse(mediaName);
        }else {
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + mediaName);
        }

    }

    private void initializePlayer() {
        Uri videoUri = getMedia(VIDEO_SAMPLE);
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
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(TipsAndTricksActivity.this, "Playback completed", Toast.LENGTH_SHORT).show();
                mVideoView.seekTo(1);
            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }
    private void releasePlayer(){
        mVideoView.stopPlayback();
    }
    @Override
    protected void onStart() {
        super.onStart();

        initializePlayer();
    }
    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }
    @Override
    protected void onPause() {
        super.onPause();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }
/*
    private void startLucene() throws IOException, ParseException {
        // Specify the analyzer for tokenizing text.
        //    The same analyzer should be used for indexing and searching

        // StandardAnalyzer removes stop words from the input, which is a
        // great help when processing naturally occurring text. StandardAnalyzer
        // also converts text to lowercase (an important point to note because case
        // issues can lead to subtle bugs in application code when searching for data
        // indexed with StandardAnalyzer).
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // IndexWriter is the main user-facing class responsible for indexing
        // data in Lucene. IndexWriter is used for analyzing documents, opening
        // directories, and writing the data to directories.
        Directory index = new RAMDirectory(); //TODO: NEED TO REPLACE THIS SINCE IT IS DEPRECATED SEE API https://lucene.apache.org/core/8_8_1/core/index.html

        // Holds all the configuration that is used to create an IndexWriter.
        // Once IndexWriter has been created with this object, changes to this object will not affect the IndexWriter instance.
        // For that, use LiveIndexWriterConfig that is returned from IndexWriter.getConfig().
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //An IndexWriter creates and maintains an index.
        IndexWriter w = new IndexWriter(index, config);
        addDoc(w, "Clothes", "Roll Clothes to Keep Things Compact", "Hi");
        addDoc(w, "Map out", "Map out the best way to get to your new home.", "Whether you’re moving to NYC, across the country, across state lines, or just to a neighboring town, you’re going to need an efficient travel route so you don’t waste your move-in day sitting in gridlock traffic or pulling over three different times to type an address into your GPS.");
        addDoc(w, "electronics", "Check to see if you have original boxes for your electronics", "Check to see if you stashed these boxes somewhere — attic? Garage? If you don’t have them, make a list of what you’ll need to buy or borrow to properly cushion your stuff.\n Quilted blankets, bubble wrap, and sturdy tape all work well to protect TVs and similarly delicate items.");
        addDoc(w, "heavy item", "Use small boxes for heavy items.", "It sounds obvious, but if you’ve ever known the struggle that is carrying a large cardboard box stuffed full of college textbooks across a parking lot, then you also know this advice cannot be overstated.");
        w.close();

        // the quertString is the keyword we are matching. If you look at our addDoc function, the param named keyword is what we are searching.
        String queryString = "heavy";

        // the "keyword" arg below specifies the default field to use when querying data. Currently I have three fields we could use: keyword, title, and article. Either of these can replace keyword
        Query query = new QueryParser("keyword", analyzer).parse(queryString);

        // limit of the amount of tips to display
        int hitsPerPage = 10;

        //used to read the underlying index using the Directory and corresponding abstractions.
        IndexReader reader = DirectoryReader.open(index);

        // IndexSearcher is the abstraction present in Lucene that executes
        // search over a single Lucene index. IndexSearcher is opened on top
        // of IndexReader, which is used to read the underlying index using
        // the Directory and corresponding abstractions.
        IndexSearcher searcher = new IndexSearcher(reader);

        //TopDocs is the representation of the top documents that match the given
        //query. They are a generic representation and do not necessarily depend
        //on the underlying algorithm used to calculate how the top documents
        //are calculated. TopDocs consist of two components: scoreDocs (the
        //documentIDs of the top N hits, where N was the requested value) and the
        //score of each of those hits.
        TopDocs docs = searcher.search(query, hitsPerPage);


        ScoreDoc[] hits = docs.scoreDocs;

        // printing off the result to the console.
        Log.d("test","Found " + hits.length + " hits.");
        for(int i=0; i < hits.length; ++i) {

            int docId = hits[i].doc;
            Document doc = searcher.doc(docId);
            Log.d("test",(i + 1) + ". Keyword: " + doc.get("keyword") + "\tExpect Tip Title: " + doc.get("title") +
                    "\tExpect Tip Article: " + doc.get("article"));
        }

        reader.close();
    }
    */


    /**
     * Used to add items to the Document element.
     *
     * @param w - creates and maintains an index.
     * @param keyword - keyword we are searching for inside
     * @param title - title of the articles
     * @param article - the meat of the article
     * @throws IOException
     */
    /*
    private static void addDoc(IndexWriter w, String keyword, String title, String article) throws IOException {
        Document doc = new Document();

        // using TextField that is indexed and tokenized, without term vectors.
        doc.add(new TextField("keyword", keyword, Field.Store.YES));

        // using a string field for title because we don't want it tokenized
        doc.add(new StringField("article", article, Field.Store.YES));

        // using a string field for title because we don't want it tokenized
        doc.add(new StringField("title", title, Field.Store.YES));
        w.addDocument(doc);
    }

    */
}