package com.example.android.guardiannews;

public class Story {
    //String variables for list items
    private String title, section, author, pubDate, url;

    public Story(String mTitle, String mSection, String mAuthor, String mDate, String mUrl) {
        title = mTitle;
        section = mSection;
        author = mAuthor;
        pubDate = mDate;
        url = mUrl;
    }

    public String getStoryTitle() {
        return title;
    }

    public String getStorySection() {
        return section;
    }

    public String getStoryAuthor() { return author; }

    public String getStoryDate() {
        return pubDate;
    }

    public String getStoryURL() { return url; }
}
