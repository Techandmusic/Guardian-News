package com.example.android.guardiannews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StoryAdapter extends ArrayAdapter<Story> {


    /*Constructor method for StoryAdapter object
    * @param context is the context of the app
    * @param stories is a list of news stories pulled from the Guardian API*/
    //TODO Rewrite adapter class

    public StoryAdapter(Context context, List<Story> stories) {
        super(context, 0, stories);
    }

    //GetView method to build the list for display

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Reuse an existing list item if one is available otherwise
        //inflate a new list item layout
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.story, parent, false);
        }
        //Get current story according to position in list
        Story currentStory = getItem(position);

        //Assign title of story to Textview
        TextView storyTitle = (TextView) listItemView.findViewById(R.id.title);
        storyTitle.setText(currentStory.getStoryTitle());

        //Assign story section to TextView
        TextView storySection = (TextView) listItemView.findViewById(R.id.section);
        storySection.setText(currentStory.getStorySection());

     //   Date dateObject = new Date(currentStory.getStoryDate());

        //Assign story date to TextView
        TextView storyDate = (TextView) listItemView.findViewById(R.id.date);
     //   String formattedDate = dateFormat(dateObject);
        storyDate.setText(currentStory.getStoryDate());


        return listItemView;

    }

    //Return properly formatted date from a date object
    private String dateFormat(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        return dateFormat.format(dateObject);
    }






}
