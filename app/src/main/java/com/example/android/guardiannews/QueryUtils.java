package com.example.android.guardiannews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    //Tag for log messages
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    //Private constructor since there is no need to create a Utils object
    private QueryUtils() {

    }

    //Returns a list of news stories from The Guardian
    public static List<Story> fetchNewsStories(String requestUrl) {
        //Create a URL object
        URL url = createURL(requestUrl);

        //Perform http request to the URL and get a JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "fetchNewsStories: Problem encountered with http request.", e);
        }

        //Extract relevant fields from JSON to create list of news stories
        List<Story> stories = extractResultsFromJson(jsonResponse);

        //return list of stories
        return stories;
    }

    //Returns a URL from a String object
    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "createURL: Problem building the url.", e);
        }
        return url;

    }

    //Takes URL parameter and makes request to the server, returns a String
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //if connection is successful
            //then read input stream and parse response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "makeHttpRequest: Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "makeHttpRequest: Problem retrieving news stories.", e);
        } finally {
            if (urlConnection == null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Converts the input stream from server into a String with the JSON response
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder outStream = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                outStream.append(line);
                line = reader.readLine();
            }
        }
        return outStream.toString();
    }

    //Parses JSON response and returns a list of story objects
    private static List<Story> extractResultsFromJson(String jsonResponse) {
        //Return early if empty or null String
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        //Create empty ArrayList to store news stories
        List<Story> stories = new ArrayList<>();

        //Parse json response String
        try {
            //Create a jsonObject from the response String
            JSONObject mainJsonResponse = new JSONObject(jsonResponse);

            //Create a jsonObject to parse the top level result from response
            JSONObject mainJsonResponseResult = mainJsonResponse.getJSONObject("response");

            //Extract the associated JSON array from the response
            JSONArray resultArray = mainJsonResponseResult.getJSONArray("results");

            //Create a Story object for each story in the Array
            for (int i = 0; i < resultArray.length(); ++i) {
                JSONObject currentStory = resultArray.getJSONObject(i);

                //Extract value for key sectionName
                String section = currentStory.getString("sectionName");
                //Extract value for key webTitle
                String title = currentStory.getString("webTitle");
                //Extract value for key webUrl
                String url = currentStory.getString("webUrl");
                //Extract value for key webPublicationDate
                String pubDate = currentStory.getString("webPublicationDate");
                //Author string variable initialized
                String author = "";

                JSONArray authorArray = currentStory.getJSONArray("tags");

                if (authorArray.length() == 0) {
                    author = "Author Name Unavailable";
                } else {
                    for (int j = 0; j < authorArray.length(); ++j) {
                        JSONObject currentAuthor = authorArray.getJSONObject(j);
                        author += currentAuthor.toString();
                    }
                }

                Story story = new Story(title, section, author, pubDate, url);

                stories.add(story);

            }
        } catch (JSONException e) {
            Log.e("Utils", "extractResultsFromJson: Problem parsing JSON results.", e);
        }

        return stories;

    }
}
