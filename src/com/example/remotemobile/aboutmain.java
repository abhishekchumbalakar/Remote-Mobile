package com.example.remotemobile;

 


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

public class aboutmain extends FragmentActivity {

	ViewPager viewPager=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_main);
		viewPager=(ViewPager)findViewById(R.id.pager);
		FragmentManager fragmentManager=getSupportFragmentManager();
		viewPager.setAdapter(new MyAdapter(fragmentManager));
	}

	

}
 

class MyAdapter extends FragmentStatePagerAdapter{

	public MyAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = null;
		if(i==0)
		{
			fragment=new about1();
		}
		if(i==1)
		{
			fragment=new about2();
			
		}
		if(i==2)
		{
			fragment=new about3();
		}
		if(i==3)
		{
			fragment=new about4();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		
		String title=new String();
		if(position==0)
		{
			return "MEMBER 1";
		}
		if(position==1)
		{
			return "MEMBER 2";
		}
		if(position==2)
		{
			return "MEMBER 3";
		}
		if(position==3)
		{
			return "MEMBER 4";
		}
		return null;		
	}
}