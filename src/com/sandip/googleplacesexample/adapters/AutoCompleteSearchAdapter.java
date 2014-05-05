package com.sandip.googleplacesexample.adapters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;



public class AutoCompleteSearchAdapter {

	private static final String LOG_TAG = "test";	
	
	private String autoCompletePath;
	private String requestType;
	private Context context;
	private int autoCompleteView;	
	private RelativeLayout progressLayout;
	private String activityname;
	public static String[] myArray;		
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

	public AutoCompleteSearchAdapter(String requestType, Context context,
			int autoCompleteView) {
		this.requestType = requestType;
		this.autoCompleteView = autoCompleteView;
		this.context = context;

		activityname = context.getClass().getSimpleName();
		Log.i(LOG_TAG, "activity name" + activityname);		
		this.progressLayout = progressLayout;
		Log.i("DISHOOM TEST", "requestType Value " + requestType);

	}

	public AutoCompleteSearchAdapter(String requestType, Context context,
			int autoCompleteView, Activity activity) {
		this.requestType = requestType;
		this.autoCompleteView = autoCompleteView;
		this.context = context;

		activityname = activity.getClass().getSimpleName();
		Log.i(LOG_TAG, "activity name" + activityname);		
		this.progressLayout = progressLayout;
		Log.i("DISHOOM TEST", "requestType Value " + requestType);

	}

	public void setProgressLayout(RelativeLayout progressLayout) {
		this.progressLayout = progressLayout;
	}

	
	

	public PlacesAutoCompleteAdapter startAutoComplete() {
		Log.i(LOG_TAG, "STARTING AUTOCOMPLETE");
		return new PlacesAutoCompleteAdapter(context, autoCompleteView, this,
				progressLayout);
	}

	ArrayList<String> autocomplete(String dropdownString) {
		ArrayList<String> resultList = null;

    HttpURLConnection conn = null;
    StringBuilder jsonResults = new StringBuilder();
    try {
        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
        sb.append("?sensor=false&key=" + API_KEY);
        sb.append("&components=country:in");
        sb.append("&input=" + URLEncoder.encode(dropdownString, "utf8"));

        URL url = new URL(sb.toString());
        Log.i(LOG_TAG, "autocomplete url "+url.toString());
        conn = (HttpURLConnection) url.openConnection();
        InputStreamReader in = new InputStreamReader(conn.getInputStream());

        // Load the results into a StringBuilder
        int read;
        char[] buff = new char[1024];
        while ((read = in.read(buff)) != -1) {
            jsonResults.append(buff, 0, read);
        }
    } catch (MalformedURLException e) {
        Log.e(LOG_TAG, "Error processing Places API URL", e);
        return resultList;
    } catch (IOException e) {
        Log.e(LOG_TAG, "Error connecting to Places API", e);
        return resultList;
    } finally {
        if (conn != null) {
            conn.disconnect();
        }
    }

    try {
        // Create a JSON object hierarchy from the results
        JSONObject jsonObj = new JSONObject(jsonResults.toString());
        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
        
        Log.i(LOG_TAG, "json object  "+jsonObj.toString());

        // Extract the Place descriptions from the results
        resultList = new ArrayList<String>(predsJsonArray.length());
        for (int i = 0; i < predsJsonArray.length(); i++) {
            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
        }      
        
        //setDishIdArray(resultList);
    } catch (JSONException e) {
        Log.e(LOG_TAG, "Cannot process JSON results", e);
    }

    return resultList;
    }

	public static String ltrim(String s) {
		int i = 0;
		while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
			i++;
		}
		return s.substring(i);
	}

}
