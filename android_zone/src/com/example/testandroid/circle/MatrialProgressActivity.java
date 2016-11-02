package com.example.testandroid.circle;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.example.testandroid.R;
import com.example.testandroid.R.id;
import com.example.testandroid.R.layout;

public class MatrialProgressActivity extends ActionBarActivity {
	int progress = 0;
	private Handler handler;
	CircleProgressBar progress1;
	CircleProgressBar progress2;
	CircleProgressBar progressWithArrow;
	CircleProgressBar progressWithoutBg;
	CircleProgressBar2 cp2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_5);
		progress1 = (CircleProgressBar) findViewById(R.id.progress1);
		progress2 = (CircleProgressBar) findViewById(R.id.progress2);
		progressWithArrow = (CircleProgressBar) findViewById(R.id.progressWithArrow);
		progressWithoutBg = (CircleProgressBar) findViewById(R.id.progressWithoutBg);
		cp2 = (CircleProgressBar2) findViewById(R.id.cp2);

		cp2.startRotationAni();

		// progress1.setColorSchemeResources(android.R.color.holo_blue_bright);
		progress2.setColorSchemeResources(android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		progress2.setProgress(20);

		progressWithArrow
				.setColorSchemeResources(android.R.color.holo_orange_light);
		progressWithoutBg
				.setColorSchemeResources(android.R.color.holo_red_light);

		handler = new Handler();
		/*
		 * for (int i = 0; i < 10; i++) { final int finalI = i;
		 * handler.postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { if(finalI *10>=90){
		 * progress1.setVisibility(View.VISIBLE);
		 * progress2.setVisibility(View.INVISIBLE); }else {
		 * progress2.setProgress(finalI * 10); } } },1000*(i+1)); }
		 */

	}
}
