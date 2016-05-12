package com.example.converter;

import com.example.convertor.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public abstract class TypeFragment extends Fragment{
	
	private String[] Types;
	private String[] Measurements;
	private String[] Types1;
	private String[] Measurements1;
	private MyListAdapter adapter;
	protected float keyValue;
	public static final String ARG_PLANET_NUMBER = "00001";
	
	private ImageButton changeBtn;
	private TextView changeType1;
	private TextView changeType2;
	
	protected boolean isType1ToType2;
	private View containerView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,  
			ViewGroup container, Bundle savedInstanceState){
		
		Types = setTypeName();
				//getActivity().getResources().getStringArray(R.array.temperature);
		Measurements = setTypeMeasurement();
		//getActivity().getResources().getStringArray(R.array.temperature_m);
		
		
		View view = inflater.inflate(R.layout.fragment, container, false);
		containerView = view;
		ListView list = (ListView)view.findViewById(R.id.fragment_list);
		adapter = new MyListAdapter(Types,Measurements,
				getActivity(),list,this,getMaxMLength()*20);
		list.setAdapter(adapter);
		doSomethingOnCreate();
		return view;
	}
	
	abstract public void doSomethingOnCreate();
	abstract public int getMaxMLength();
	abstract public String[] setTypeName();
	abstract public String[] setTypeMeasurement();
	abstract protected void calculateKeyValue(int inputID,float inputValue);
	abstract public String [] caculate (int inputID, float inputValue, int count);
	public void setMaxMeasurement(int max){
		
	}
	
	
	public void showButton(String type1, String type2, String[] TypeName,String[] TypeMeasurement){
		LinearLayout changeBtnView = (LinearLayout)containerView.findViewById(R.id.change_btn_view);
		changeBtnView.setVisibility(View.VISIBLE);
		this.changeBtn = (ImageButton)changeBtnView.findViewById(R.id.change_btn);
		this.changeType1 = (TextView)changeBtnView.findViewById(R.id.change_type1);
		this.changeType2 = (TextView)changeBtnView.findViewById(R.id.change_type2);
		
		changeType1.setText(type1);
		changeType2.setText(type2);
		
		Types1 = TypeName;
		Measurements1 = TypeMeasurement;
		
		isType1ToType2 = true;
		this.changeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isType1ToType2 = !isType1ToType2;
				String temp;
				temp = changeType1.getText().toString();
				changeType1.setText(changeType2.getText());
				changeType2.setText(temp);
				if(isType1ToType2){
					adapter.updateValue(Types,Measurements);
				}else{
					adapter.updateValue(Types1,Measurements1);
				}
			}
			
		});
	}
		/*
		DecimalFormat df = new DecimalFormat("0.00");
		String[] strings = new String[count-1]; 
		for(int i = 0,j=0; i< count; i++){
			
			if(i!=inputID){
				strings[j++]=String.valueOf(df.format(kValue*1.8));
			}
		}
		return strings;
		*/
	
	
}
