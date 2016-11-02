package com.example.testandroid.thread;

import com.example.testandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ThreadActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_threadtest);
						
		findViewById(R.id.notify).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		
		findViewById(R.id.notifyall).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}	
}
