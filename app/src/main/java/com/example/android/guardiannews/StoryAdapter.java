package com.example.android.guardiannews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StoryAdapter extends ArrayAdapter<Story> {


    /*Constructor method for StoryAdapter object
     * @param context is the context of the app
     * @param stories is a list of news stories pulled from the Guardian API*/


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

        //Assign story author to TextView
        TextView storyAuthor = (TextView) listItemView.findViewById(R.id.author);
        storyAuthor.setText(currentStory.getStoryAuthor());


        //Assign story date to TextView
        TextView storyDate = (TextView) listItemView.findViewById(R.id.date);
        storyDate.setText(currentStory.getStoryDate());




        return listItemView;

    }


}
