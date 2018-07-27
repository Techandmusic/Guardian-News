package com.example.android.guardiannews;

public class Story {
    //String variables for list items
    private String storyTitle, storySection, storyDate, storyURL;

    public Story(String mTitle, String mSection, String mDate, String mUrl) {
        mTitle = storyTitle;
        mSection = storySection;
        mDate = storyDate;
        mUrl = storyURL;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public String getStorySection() {
        return storySection;
    }

    public String getStoryDate() {
        return storyDate;
    }

    public String getStoryURL() { return storyURL; }
}
