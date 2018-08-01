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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StoryActivity extends AppCompatActivity {
    //URL to access API
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?q=smartphone&api-key=c8703a02-b362-46bc-a071-be6ec2eca354";

    //Constant int value for loader
    private final int STORY_LOADER_ID = 1;

    //Adapter instance
    private StoryAdapter mAdapter;

    //TextView for EmptyState
    private TextView mEmptyStateView;

    //Context variable
    private Context appContext;

    //Tag for log messages
    private static final String LOG_TAG = StoryActivity.class.getSimpleName();

    //View for progress Bar
    private View progress;

    //List view for news stories
    private ListView storyView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        //Set context
        appContext = this;

        //Set listView in layout
        storyView = findViewById(R.id.story_view);




        //Create a new story adapter that takes a list of stories as input
        mAdapter = new StoryAdapter(appContext, new ArrayList<Story>());

        //Set the adapter to the ListView
        storyView.setAdapter(mAdapter);

        //Set empty state textview
        mEmptyStateView = findViewById(R.id.empty_view);
        mEmptyStateView.setText(R.string.no_news);
        storyView.setEmptyView(mEmptyStateView);

        //TODO Set the onItemClickListener
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

        //Setup the connectivity manager and check connection status
        ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Check currently active network
        NetworkInfo netInfo = connectMgr.getActiveNetworkInfo();

        //If there is an active network connection get data otherwise display error
        if (netInfo != null && netInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(STORY_LOADER_ID, null, loaderCallbacks);
        } else {
            progress = findViewById(R.id.progressBar1);
            progress.setVisibility(View.GONE);


            mEmptyStateView.setText(R.string.no_internet);

        }



    }

    private final LoaderManager.LoaderCallbacks<List<Story>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<Story>>() {
        @Override
        public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
            StoryLoader loader = new StoryLoader(appContext, GUARDIAN_REQUEST_URL);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<Story>> loader, List<Story> stories) {
            //Set text for empty state to "no news" string
            //   mEmptyStateView.setText(R.string.no_news);
            //   mEmptyStateView.setVisibility(View.GONE);
            storyView.setVisibility(View.VISIBLE);
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
    };
}
