package com.example.remotemobile;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class options extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_options);
         
        TabHost tabHost = getTabHost();
         
  
        TabSpec photospec = tabHost.newTabSpec("PASSWORD");
        // setting Title and Icon for the Tab
        photospec.setIndicator("PASSWORD");
        
        Intent photosIntent = new Intent(this, password.class);
        photospec.setContent(photosIntent);
         
       
        TabSpec songspec = tabHost.newTabSpec("HELP");        
        songspec.setIndicator("NEED HELP?");
        Intent songsIntent = new Intent(this, help.class);
        songspec.setContent(songsIntent);
         
      
       
         
       
        tabHost.addTab(photospec); 
        tabHost.addTab(songspec); 
       
    }
    
    
}


