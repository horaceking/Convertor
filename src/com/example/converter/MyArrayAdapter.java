package com.example.converter;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter {

	private int currentPos;
	private int lastPos;
	private int resource;
	private String[] objects;
	public MyArrayAdapter(Context context, int resource, Object[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		currentPos=0;
		lastPos=0;
		this.resource = resource;
		this.objects = (String[])objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			convertView= LayoutInflater.from(super.getContext()).inflate(resource, null);
			((TextView)convertView).setText(objects[position]);
			
		}
		if(position == lastPos){
			((TextView)convertView).setBackgroundColor(color.transparent);
		}
		if(position == currentPos){
			((TextView)convertView).setBackgroundColor(Color.parseColor("#067270"));
		}
		return convertView;
	}
	
	public void setSelected(int pos){
	    Log.i("aaa","pos:"+ pos+" current:"+currentPos+" last:"+lastPos);
		if(currentPos == pos)
			return;
		lastPos = currentPos;
		currentPos = pos;
		super.notifyDataSetChanged();
	}
}
