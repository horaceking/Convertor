package com.example.converter;

import java.text.DecimalFormat;

import com.example.convertor.R;

public class Temperature extends TypeFragment{

	//Set the names with this type
	@Override
	public String[] setTypeName(){
		return getActivity().getResources().getStringArray(R.array.temperature);
	}

	//Set the measurements with this type
	@Override
	public String[] setTypeMeasurement(){
		return getActivity().getResources().getStringArray(R.array.temperature_m);

	}

	//set the caculate method, 
	//inputID is the position of the view, start from 0
	//kValue is the current value of the editText
	//count is the number of the views
	//returns all values, just order by the position

	@Override
	public String[] caculate(int inputID, float inputValue, int count) {
		// TODO Auto-generated method stub
		calculateKeyValue(inputID, inputValue);
		DecimalFormat df = new DecimalFormat("0.00");
		String[] strings = new String[count]; 
		if(isType1ToType2){
		for(int i = 0; i< count; i++){
			if(inputID != i){
				switch(i){
				case 0:{strings[i]=String.valueOf(df.format(keyValue - 273.15));break;}
				case 1:{strings[i]=String.valueOf(df.format((keyValue-273.15)*1.8+32));break;}
				case 2:{strings[i]=String.valueOf(df.format(keyValue));break;}
				case 3:{strings[i]=String.valueOf(df.format(keyValue*1.8));break;}
				case 4:{strings[i]=String.valueOf(df.format((keyValue - 273.15)*0.8));break;}
				}
			}
		}
		}else{
			for(int i = 0; i< count; i++){
				if(inputID != i){
					switch(i){
					case 0:{strings[i]=String.valueOf(df.format(keyValue));break;}
					case 1:{strings[i]=String.valueOf(df.format(keyValue));break;}
					case 2:{strings[i]=String.valueOf(df.format(keyValue));break;}
					case 3:{strings[i]=String.valueOf(df.format(keyValue));break;}
					case 4:{strings[i]=String.valueOf(df.format(keyValue));break;}
					}
				}
			}
		}
		return strings;
	}

	@Override
	protected void calculateKeyValue(int inputID, float inputValue) {
		// TODO Auto-generated method stub
		switch(inputID){
		case 0:{ keyValue = (float) (inputValue + 273.15);break;}
		case 1:{ keyValue = (float) ((inputValue-32)/1.8 + 273.15);break;}
		case 2:{ keyValue = inputValue;break;}
		case 3:{ keyValue = (float) (inputValue/1.8);break;}
		case 4:{ keyValue = (float) (inputValue*1.25 + 273.15);break;}
		}
	}

	@Override
	public int getMaxMLength() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public void doSomethingOnCreate() {
		// TODO Auto-generated method stub
		
	}

}
