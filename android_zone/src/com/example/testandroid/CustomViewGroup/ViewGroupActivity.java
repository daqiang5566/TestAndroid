package com.example.testandroid.CustomViewGroup;

import com.example.testandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ViewGroupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewgroup);
	}
}
