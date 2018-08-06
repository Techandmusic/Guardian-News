package com.example.android.guardiannews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class StoryLoader extends AsyncTaskLoader<List<Story>> {

    private static final String LOG_TAG = StoryLoader.class.getSimpleName();
    private String mUrl;

    public StoryLoader(Context context, String url) {
        super(context);
        mUrl = url;

    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Story> loadInBackground() {
        if (mUrl == null) {
            Log.e(LOG_TAG, "No info from Url.");
            return null;
        }

        List<Story> newsStories = QueryUtils.fetchNewsStories(mUrl);
        return newsStories;
    }
}
