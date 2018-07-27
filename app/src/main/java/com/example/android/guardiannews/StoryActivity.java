package com.example.android.guardiannews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private LoaderManager.LoaderCallbacks<List<Story>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<Story>>() {
        @Override
        public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
            StoryLoader loader = new StoryLoader(appContext, GUARDIAN_REQUEST_URL);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<Story>> loader, List<Story> data) {
            //Set text for empty state to "no news" string
            mEmptyStateView.setText(R.string.no_news);
            //If news stores are available then add them to the adapter
            if (stories != null && !stories.isEmpty()) {
                mAdapter.addAll(stories);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Story>> loader) {
            mAdapter.clear();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        //Set context
        appContext = this;

        //Set listView in layout
        ListView storyView = findViewById(R.layout.activity_story);

        //Set empty state textview
        mEmptyStateView = findViewById(R.id.empty_view);
        storyView.setEmptyView(mEmptyStateView);

        //Create a new story adapter that takes a list of stories as input
        mAdapter = new StoryAdapter(appContext,new ArrayList<Story>());

        //Set the adapter to the ListView
        storyView.setAdapter(mAdapter);

        //TODO Set the onItemClickListener




    }
}
