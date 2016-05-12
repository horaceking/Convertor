package com.example.converter;

import com.example.convertor.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{
	private String[] names;
	private String[] measurements;
	private ListView myList;
	private LayoutInflater mInflater;
	private String nullValue;
	private TypeFragment fragment;
	private String[] values;
	private boolean isCalc=false;
	private Activity context;
	private int maxMeasurementLength;
	
	
	public MyListAdapter(String[] names, String[] measurements, 
			FragmentActivity fragmentActivity, ListView list, TypeFragment fragment,int maxMLength) {
		this.maxMeasurementLength = maxMLength;
		this.context = fragmentActivity;
		this.names = names;
		this.measurements = measurements;
		this.myList = list;
		this.fragment = fragment;
		this.values = new String[names.length];
		nullValue = "";
		for(int i = 0 ; i < values.length; i++){
			values[i] = nullValue;
		}
		this.mInflater = LayoutInflater.from(fragmentActivity);
	}
	private int lastFocus = -1;
	private View lastFocusView = null;

	class MyOnClickListener implements OnClickListener{
		private int pos;
		public MyOnClickListener(int pos){
			this.pos = pos;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(pos!=lastFocus){
				//isCalc=true;
				((EditText)v).selectAll();
				lastFocus = pos;
				lastFocusView = (View)(v.getParent());
				//isCalc = false;
			}
			
		}
		
	}
	class MyOnFocusChangeListener implements OnFocusChangeListener{
		private int pos;
		
		public MyOnFocusChangeListener(int pos){
			this.pos = pos;
		}

		@Override
		public void onFocusChange(View view, boolean hasFocus) {
			// TODO Auto-generated method stub
			EditText text = (EditText) view;
			if(hasFocus){
				if(lastFocus!=pos){
					if(lastFocusView !=null){
						lastFocusView.setBackgroundResource(R.drawable.item_bg);
					}
					view = (View)(view.getParent());
					view.setBackgroundResource(R.drawable.item_bg_selected);
					lastFocusView = view;
					lastFocus = pos;
				}
				
			}else{
				String s = text.getText().toString();
				if(s.equals("")){
					isCalc=true;
					if(values[pos]==""){
						text.setText(nullValue);
					}else{
						text.setText(values[pos]);
					}
					isCalc=false;
				}
			}
		}
    	
    }
	
	
	public void setNullValue(String s){
		this.nullValue = s;
	}
	
	class OnTextChanged implements TextWatcher{
		
		private float kValue;
		private int inputID;
		
		public OnTextChanged(int inputID){
			this.inputID = inputID;
		}
		
		public void setToNull(){
			int offset = myList.getFirstVisiblePosition();
			for(int i = 0; i< names.length; i++){
				View view = myList.getChildAt(i-offset);
				
				if(view!=null&&i!=inputID){
					ViewHolder holder = (ViewHolder) view.getTag();
					values[i] = "";
					holder.getValue().setText("");
				}else{
					values[i] = "";
				}
			}
			
			isCalc = false;
		}
		public void calculateAllValues(){
			String[] targetvalues = fragment.caculate(inputID, kValue, getCount());
			int offset = myList.getFirstVisiblePosition();
			for(int i = 0; i< names.length; i++){
				View view = myList.getChildAt(i-offset);
				ViewHolder holder = (ViewHolder) view.getTag();
				values[i] = targetvalues[i];
				if(view!=null&&i!=inputID){
					holder.getValue().setText(targetvalues[i]);
					Log.i("aaa","change text at "+i +" to "+targetvalues[i]);
				}else if(i == inputID){
					values[i]=holder.getValue().getText().toString();
				}
			}
			
			isCalc = false;
		};
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if(!isCalc){
				isCalc = true;
				try{
				kValue = Float.valueOf(s.toString());
				calculateAllValues();
				}catch(Exception e){
					//isCalc = false;
					setToNull();
				}
				
			}
				
			
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	class ViewHolder {
		private TextView name;
		private TextView measurement;
		private EditText value;
		
		public TextView getName() {
			return name;
		}
		public void setName(TextView name) {
			this.name = name;
		}
		public TextView getMeasurement() {
			return measurement;
		}
		public void setMeasurement(TextView measurement) {
			this.measurement = measurement;
		}
		
		public EditText getValue() {
			return value;
		}
		public void setValue(EditText value) {
			this.value = value;
		}

	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return myList.getChildAt(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater
					.inflate(R.layout.fragment_list_item, null);
			
			holder.setName((TextView) convertView
					.findViewById(R.id.name));
			holder.setMeasurement((TextView) convertView
					.findViewById(R.id.measurement));
			holder.setValue((EditText) convertView
					.findViewById(R.id.value));
	        holder.getValue().addTextChangedListener(new OnTextChanged(position));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		while(!isCalc){
			this.isCalc = true;
		}
	        holder.getName().setText(names[position]);
	        holder.getMeasurement().setText(measurements[position]);
	        holder.getValue().setText(values[position]);
	        Log.i("aaa", "set value at" + position);
	        holder.getValue().setOnFocusChangeListener(new MyOnFocusChangeListener(position));
	        holder.getValue().setOnClickListener(new MyOnClickListener(position));
	        
	        if(context.getAssets()!=null){
		        Typeface typeFace = Typeface.createFromAsset(context.getAssets(),"fonts/coprgtb.ttf");
		        holder.getValue().setTypeface(typeFace);
		        holder.getName().setTypeface(typeFace);
		        }
	        //
	        holder.getMeasurement().setWidth(maxMeasurementLength);
	        //
	        this.isCalc = false;
		    return convertView;
	}

	public void updateValue(String[] TypeName,String[] TypeMeasurement) {
		// TODO Auto-generated method stub
		float kvalue;
		String value;
		try{
			value = values[0];
			kvalue = Float.valueOf(values[0]);
			this.values = fragment.caculate(0, kvalue, getCount());
			values[0] = value;
			Log.i("aaa", "changed");
			
		}catch(Exception e){
			Log.i("aaa", e.toString()+"   "+values[0]);
		}
		this.names = TypeName;
		this.measurements = TypeMeasurement;
		this.notifyDataSetChanged();
	} 
}
