package com.example.remotemobile;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class setting extends Activity {

private List<set> myset= new ArrayList<set>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting);
		
		populatesetlist();
		populateListview();
		registerCliclCallback();
	}

	private void registerCliclCallback() {
		// TODO Auto-generated method stub
		
		ListView list=(ListView)findViewById(R.id.listView1);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				if(arg2==0)
				{
					Intent i=new Intent(setting.this,options.class);
					startActivity(i);	
				}
				if(arg2==1)
				{
					Intent i=new Intent(setting.this,help.class);
					startActivity(i);
				}
				
				if(arg2==2)
				{
					Intent i=new Intent(setting.this,aboutus.class);
					startActivity(i);
				}
				if(arg2==3)
				{
					finish();	
				}
			}
		});
	}

	private void populateListview() {
		
		ArrayAdapter<set> adapter= new MyListAdapter();
		ListView list =(ListView)findViewById(R.id.listView1);
		list.setAdapter(adapter);
		
	}

	private void populatesetlist() {
		// TODO Auto-generated method stub
		myset.add(new set("Password", R.drawable.passwordop, "hello"));
		myset.add(new set("Help", R.drawable.helpso4, "hello"));
		myset.add(new set("About Developers", R.drawable.aboutusop2, "hello"));
		myset.add(new set("Home", R.drawable.homefi, "hello"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class MyListAdapter extends ArrayAdapter<set>{
		public MyListAdapter(){
			super(setting.this,R.layout.item_view,myset);
		}

		/* (non-Javadoc)
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//return super.getView(position, convertView, parent);
			
			View itemView=convertView;
			if(itemView==null){
				itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
			}
			
			set currentset=myset.get(position);
			
			ImageView imageview =(ImageView)itemView.findViewById(R.id.img_passwd);
			
			imageview.setImageResource(currentset.getIconid());
			
			TextView maketext=(TextView)itemView.findViewById(R.id.txt_password);
			maketext.setText(currentset.getMake());
			
			return itemView;		
		}	
	}
}