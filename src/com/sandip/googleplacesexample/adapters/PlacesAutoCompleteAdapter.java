package com.sandip.googleplacesexample.adapters;

import java.util.ArrayList;

import com.sandip.googleplaces.R;


import android.widget.Filterable;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
	private ArrayList<String> resultList;
	private ArrayList<String> myArray;
	private Object autoCompleteObj;
	private Context appContext;
	private int id;
	private Typeface faceBold;
	private RelativeLayout progressLayout;
    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId,
			Object autoCompleteObj, RelativeLayout progressLayout) {
		super(context, textViewResourceId);
		this.autoCompleteObj = autoCompleteObj;
		id = textViewResourceId;
		this.appContext = context;
		this.progressLayout = progressLayout;
		faceBold = Typeface.createFromAsset(appContext.getAssets(),
				"fonts/eau_sans_bold.otf");
	}

    @Override
	public int getCount() {
		if (myArray != null)
			return myArray.size();
		else
			return 0;
	}

	@Override
	public String getItem(int index) {
		return myArray.get(index);
	}
	
   
    
    @Override
	public View getView(int position, View v, ViewGroup parent) {
		View mView = v;		
		if (mView == null) {
			LayoutInflater vi = (LayoutInflater) appContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = vi.inflate(id, null);		
		
			TextView text = (TextView) mView.findViewById(R.id.textview);
			// Log.i("TEST", "drop down array null and getting exception "
			// + getItem(position));
			if (getItem(position) != null) {
				text.setTypeface(faceBold);
				
					text.setTextColor(Color.parseColor("#cc3333"));
					text.setText(getItem(position));				
			}
		}

		return mView;
	}
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					if (autoCompleteObj instanceof AutoCompleteSearchAdapter)
						myArray = ((AutoCompleteSearchAdapter) autoCompleteObj)
								.autocomplete(constraint.toString());
					// Assign the data to the FilterResults
					filterResults.values = myArray;
					filterResults.count = myArray.size();
				}

				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results != null && results.count > 0) {
					// notifyDataSetInvalidated();
					notifyDataSetChanged();
					if (progressLayout != null)
						progressLayout.setVisibility(View.GONE);
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}
}