package com.example.remotemobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class aboutus extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_aboutus);
		
		
		Thread timer= new Thread(){
			public void run(){
				
						try{
							sleep(1000);
						}
						catch(Exception e)
						{
							e.printStackTrace();
				
							}
						finally
						{
							Intent i=new Intent(aboutus.this,aboutmain.class);
							startActivity(i);
						}
			
		}
			
			
		
		};
		
		timer.start();	
		
	
		
	}
				
			
		
		

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

