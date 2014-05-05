package com.sandip.googleplaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.sandip.googleplaces.adapter.ListAdapter;
import com.sandip.googleplacesexample.adapters.AutoCompleteSearchAdapter;
import com.sandip.googleplacesexample.adapters.PlacesAutoCompleteAdapter;

public class MainActivity extends Activity implements TextWatcher{
	
	private static ArrayList<String> placesArrayList = new ArrayList<String>();
	private AutoCompleteTextView txtPlacesAutocomplete;
	private AutoCompleteSearchAdapter autoCompleteSearchAdapter;
	private RelativeLayout progressLayout;
	private PlacesAutoCompleteAdapter placesAutoCompleteSearchAdapter;
	public static int deviceWidth = -1;
	private int deviceHeight;
	private ListAdapter adapter;
	private ListView placesList;
	SharedPreferences myPrefs;
	private Typeface faceNormal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initializingViews();
		
		int size = myPrefs.getInt("array_size", 0);
		if (size > 0) {
			String array[] = new String[size];
			for (int i = 0; i < size; i++)
				array[i] = myPrefs.getString("placesList" + i, null);
			placesArrayList = new ArrayList<String>(Arrays.asList(array));
		}
		
		adapter = new ListAdapter(MainActivity.this,placesArrayList);
		adapter.notifyDataSetChanged();
		placesList.setAdapter(adapter);
		
		txtPlacesAutocomplete.addTextChangedListener(new TextWatcher() {
			private int countForText;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				countForText = txtPlacesAutocomplete.getText().toString().trim()
						.length();
				if (countForText >= 3)
					progressLayout.setVisibility(View.VISIBLE);
				else
					progressLayout.setVisibility(View.GONE);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		
		
		txtPlacesAutocomplete.setOnItemClickListener(new OnItemClickListener() {

			

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				txtPlacesAutocomplete.dismissDropDown();
				progressLayout.setVisibility(View.GONE);
				placesArrayList.add(txtPlacesAutocomplete.getText().toString());
				adapter = new ListAdapter(MainActivity.this,placesArrayList);
				adapter.notifyDataSetChanged();
				placesList.setAdapter(adapter);
				
				txtPlacesAutocomplete.setText("");
				
			}
		});
		
	}
	private void initializingViews() {
		faceNormal = Typeface.createFromAsset(getAssets(),
				"fonts/eau_sans_book.otf");
		placesList = (ListView)findViewById(R.id.autocompletePlacesListView);
		txtPlacesAutocomplete = (AutoCompleteTextView)findViewById(R.id.txtPlacesAutocomplete);
		txtPlacesAutocomplete.setTypeface(faceNormal);
		myPrefs = getSharedPreferences("places",
				MODE_WORLD_READABLE);
		txtPlacesAutocomplete.addTextChangedListener(this);
		txtPlacesAutocomplete.setThreshold(3);
		txtPlacesAutocomplete.setDropDownVerticalOffset(0);
		txtPlacesAutocomplete.setDropDownHorizontalOffset(0);
		setDropDownHeightAndWidth(txtPlacesAutocomplete);
		txtPlacesAutocomplete.setDropDownWidth(deviceWidth - 24);
		progressLayout = (RelativeLayout) findViewById(R.id.progressLayout);
		txtPlacesAutocomplete.setDropDownBackgroundResource(R.color.autocompletet_background_color);
		autoCompleteSearchAdapter = new AutoCompleteSearchAdapter("search",
				getApplicationContext(), R.layout.autocompletelistview_test,
				 MainActivity.this);
		autoCompleteSearchAdapter.setProgressLayout(progressLayout);
		placesAutoCompleteSearchAdapter = autoCompleteSearchAdapter
				.startAutoComplete();
		if (placesAutoCompleteSearchAdapter != null)
			txtPlacesAutocomplete.setAdapter(placesAutoCompleteSearchAdapter);
		
	}
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressLint("NewApi")
	protected void setDropDownHeightAndWidth(AutoCompleteTextView myAutoComplete) {
		int Measuredwidth = 0;
		int Measuredheight = 0;
		// Point size = new Point();
		// WindowManager w = getWindowManager();

		Display display = getWindowManager().getDefaultDisplay();
		Measuredwidth = display.getWidth(); // deprecated
		Measuredheight = display.getHeight();
		deviceWidth = display.getWidth(); // deprecated
		deviceHeight = display.getHeight();

		myAutoComplete.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		myAutoComplete.setDropDownWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
		
	}

	protected void storeNameAraay(List<String> names2) {		
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putInt("array_size", names2.size());
		for (int i = 0; i < names2.size(); i++) {
			prefsEditor.putString("placesList" + i, names2.get(i));
		}		
		prefsEditor.commit();
		
	}
	
	@Override
	protected void onStop() {
		storeNameAraay(placesArrayList);
		super.onStop();
	}

}
