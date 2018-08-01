package com.example.android.guardiannews;

public class Story {
    //String variables for list items
    private String title, section, pubDate, url;

    public Story(String mTitle, String mSection, String mDate, String mUrl) {
        title = mTitle;
        section = mSection;
        pubDate = mDate;
        url = mUrl;
    }

    public String getStoryTitle() {
        return title;
    }

    public String getStorySection() {
        return section;
    }

    public String getStoryDate() {
        return pubDate;
    }

    public String getStoryURL() { return url; }
}
