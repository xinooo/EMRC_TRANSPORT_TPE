package com.emrc_transport_tpe;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {

	private ArrayList<View> aListView = new ArrayList<View>();

	MyListAdapter(ArrayList<View> list) {
		for (int i = 0; i < list.size(); i++)
			aListView.add(list.get(i));
	}

	public int getCount() {
		return aListView.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		return aListView.get(position);
	}
}