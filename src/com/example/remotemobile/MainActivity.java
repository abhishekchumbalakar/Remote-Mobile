package com.example.remotemobile;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageButton img,img2;
	ImageView g1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		g1=(ImageView)findViewById(R.id.img_ph);
		
		g1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			g1.setBackgroundResource(R.drawable.red);

			Thread timer= new Thread(){
				public void run(){
					
							try{
								sleep(500);
								g1.setBackgroundResource(R.drawable.white);
							}
							catch(Exception e)
							{
								e.printStackTrace();
					
								}
							finally
							{
								
								Intent i=new Intent(MainActivity.this,setting.class);
								startActivity(i);	
								
							}
				
			}
				
				
			
			};
			
			timer.start();	
			
				
			}
			
		});
	
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}