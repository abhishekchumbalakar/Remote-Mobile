package com.example.remotemobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.TextView;

public class password extends setting implements RemoteBuddyCommon {

	private static SharedPreferences password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_password);
		  password = getSharedPreferences(PREFS_NAME, 0);
		  TextView keyword = (TextView)findViewById(R.id.password);
			keyword.setText(password.getString(KEYWORD, null));
			keyword.addTextChangedListener(new KeywordWatcherListener());
	
		
}
	
	private class KeywordWatcherListener implements TextWatcher 
    {

		// After every type on keyword EditView we store the value. 
		
		public void afterTextChanged(Editable s) 
		{
			String keyword = s.toString();
			SharedPreferences.Editor editor = password.edit();
			
			//If empty save KEYWORD as 'null' for validation purposes
			editor.putString(KEYWORD, keyword.equals("")?null:keyword);
			editor.commit();
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) 
		{
				//Do nothing
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,int count) 
		{
				//Do nothing
		}
		
	}
}
