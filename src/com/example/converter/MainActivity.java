package com.example.converter;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import com.example.convertor.R;
import com.sileria.android.Kit;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class MainActivity extends FragmentActivity{
	//private variables
	
	private String[] mTypes;
	private int currentPos;
	@SuppressWarnings("deprecation")
	private SlidingDrawer mDrawer;
	private ListView mDrawerList;
	private MyArrayAdapter adapter;
	private LinearLayout mask;
	//-------------------------------------------------------------
	
	
	//Set back key performance
	@Override 
	public void onBackPressed() {
		if(mDrawer.isOpened()){
			mDrawer.animateClose();
		}else
			super.onBackPressed(); 
	} 
	
	
	//-----------------------------------------------------------------
	
	
	//Set drawer list listener
	public class DrawerItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			selectItem(position);
		}

	}
	//------------------------------------------------------------------------
	
	
	//When select a item from list, change fragment and close the drawer
	
	private void selectItem(int position) {  
	    // Create a new fragment and specify the planet to show based on position
		Fragment fragment;
		switch(position){
		case 0:
			fragment = new Temperature();  
			break;
		case 1:
			fragment = new Temperature();  
			break;
		case 2:
			fragment = new Length();  
			break;
		case 3:
			fragment = new Temperature();  
			break;
		case 4:
			fragment = new Temperature();  
			break;
		default:
			fragment = new Temperature();
			break;
				
		};
	     
	    Bundle args = new Bundle();  
	    args.putInt(TypeFragment.ARG_PLANET_NUMBER, position);  
	    fragment.setArguments(args);
	  
	    // Insert the fragment by replacing any existing fragment  
	    FragmentManager fragmentManager = getSupportFragmentManager();  
	    fragmentManager.beginTransaction()
	                  .replace(R.id.content_frame, fragment)  
	                  .commit();  
	    
	    // Highlight the selected item, update the title, and close the drawer  
	    mDrawerList.setItemChecked(position, true);
	    mDrawer.animateClose(); 
	    adapter.setSelected(position);
	    currentPos = position;
	}  
	  
	
	//--------------------------------------------------------------------------


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mask = (LinearLayout) this.findViewById(R.id.mask);
		mDrawer = (SlidingDrawer) this.findViewById(R.id.slidingdrawer);
		mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener(){

			@Override
			public void onDrawerOpened() {
				// TODO Auto-generated method stub
				mask.setVisibility(View.VISIBLE);
			}
			
		});
		mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener(){

			@Override
			public void onDrawerClosed() {
				// TODO Auto-generated method stub
				mask.setVisibility(View.GONE);
			}
			
		});
		mask.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDrawer.animateClose();
			}
			
		});

		mTypes = this.getResources().getStringArray(R.array.type_array);
		mDrawerList = (ListView) this.findViewById(R.id.right_drawer);
		adapter = new MyArrayAdapter(this,R.layout.drawer_layout_item,mTypes);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		//mDrawer.animateOpen();
		//selectItem(0);
		
		currentPos = 0;
	  
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
