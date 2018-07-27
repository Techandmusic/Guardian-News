package com.example.android.guardiannews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {
    //URL to access API
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?q=smartphone&api-key=c8703a02-b362-46bc-a071-be6ec2eca354";

    //Constant int value for loader
    private final int STORY_LOADER_ID = 1;

    //Adapter instance
    private StoryAdapter mAdapter;

    //TextView for EmptyState
    private TextView mEmptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
    }
}
