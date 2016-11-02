package com.example.testandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class LayerListActivity extends Activity{
	
	ImageView iv_layer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layer_list);
		iv_layer = (ImageView) findViewById(R.id.iv_layer);		
		iv_layer.getDrawable().setLevel(5000);
	}
}
