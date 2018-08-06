package com.example.android.guardiannews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Story>> {
    //URL to access API
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?q=smartphone&section=technology&pageSize=10&api-key=c8703a02-b362-46bc-a071-be6ec2eca354&show-tags=contributors";
    //Tag for log messages
    private static final String LOG_TAG = StoryActivity.class.getSimpleName();
    //Constant int value for loader
    private final int STORY_LOADER_ID = 1;
    //Adapter instance
    private StoryAdapter mAdapter;
    //TextView for EmptyState
    private TextView mEmptyStateView;
    //View for progress Bar
    private View loadIndicator;

    //List view for news stories
    private ListView storyView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);



        //Set listView in layout
        storyView = findViewById(R.id.story_view);


        //Create a new story adapter that takes a list of stories as input
        mAdapter = new StoryAdapter(this, new ArrayList<Story>());


        //Set the adapter to the ListView
        storyView.setAdapter(mAdapter);


        //Set empty state textview
        mEmptyStateView = (TextView) findViewById(R.id.empty_view);

        //Setup the connectivity manager and check connection status
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        //Check currently active network
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();

        //If there is an active network connection get data otherwise display error
        if (netInfo != null && netInfo.isConnected()) {
            LoaderManager loadMgr = getLoaderManager();
            loadMgr.initLoader(STORY_LOADER_ID, null, this);
        } else {
            loadIndicator = (ProgressBar) findViewById(R.id.progressBar1);
            loadIndicator.setVisibility(View.GONE);
            //Set empty state view to no internet message
            mEmptyStateView.setText(R.string.no_internet);
        }


        storyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Find current story in story adapter
                Story currentStory = mAdapter.getItem(position);
                //Get url from current story
                Uri storyUri = Uri.parse(currentStory.getStoryURL());
                //Create intent to view story at the URL
                Intent webStoryIntent = new Intent(Intent.ACTION_VIEW, storyUri);
                //Launch intent
                startActivity(webStoryIntent);
            }
        });


    }

    @Override
    public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
        return new StoryLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> stories) {
        //Hide loading indicator
        loadIndicator = (ProgressBar) findViewById(R.id.progressBar1);
        loadIndicator.setVisibility(View.GONE);
        //Set empty state text view to empty state message
        mEmptyStateView.setText(R.string.no_news);
        mEmptyStateView.setVisibility(View.GONE);
        //If news stores are available then add them to the adapter
        if (stories != null && !stories.isEmpty()) {
            mAdapter.addAll(stories);
        } else {
            Log.e(LOG_TAG, "Adapter contains no data.");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        mAdapter.clear();
    }

}
