package com.example.testandroid.marquee;

import android.app.Activity;
import android.os.Bundle;

import com.example.testandroid.R;
import com.example.testandroid.R.id;
import com.example.testandroid.R.layout;

public class MarqueeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marquee);
		
		ScrollTextView text = (ScrollTextView) findViewById(R.id.scrollTextView);
		text.setRndDuration(1000);
		text.startScroll();
	}	
}
