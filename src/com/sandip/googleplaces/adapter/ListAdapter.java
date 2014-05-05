package com.sandip.googleplaces.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandip.googleplaces.MainActivity;
import com.sandip.googleplaces.R;

public class ListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<String> data;
	private static LayoutInflater inflater = null;
	ImageView firstStar, secondStar, thirdStar, fourthStar, fifthStar;
	Typeface faceBold, faceNormal;

	public ListAdapter(Activity a, ArrayList<String> name) {
		activity = a;
		data = name;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		faceBold = Typeface.createFromAsset(activity.getAssets(),
				"fonts/eau_sans_bold.otf");		

	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (activity instanceof MainActivity) {
			if (convertView == null)
				vi = inflater.inflate(R.layout.list_row, null);

			TextView placeName = (TextView) vi.findViewById(R.id.title); // title
			placeName.setTypeface(faceBold);
			placeName.setText(data.get(position));			
		}
		return vi;
	}

	
}