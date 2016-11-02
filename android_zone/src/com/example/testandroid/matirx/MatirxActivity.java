package com.example.testandroid.matirx;

import com.example.testandroid.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MatirxActivity extends Activity implements OnClickListener{
	
	ImageView iv_matrix;
	Button btn_transfrom;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_matrix);
				
		btn_transfrom = (Button) findViewById(R.id.btn_transfrom);
		btn_transfrom.setOnClickListener(this);
		
		iv_matrix = (ImageView) findViewById(R.id.iv_matrix);
					
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		setScale();
	}
	
	private void preScale(){
		Matrix matrix = new Matrix();		
		matrix.preScale(0.5f, 0.5f);
		iv_matrix.setImageMatrix(matrix);
	}
	
	private void postScale(){
		Matrix matrix = new Matrix();		
		matrix.postScale(0.5f, 0.5f);
		iv_matrix.setImageMatrix(matrix);
	}
	
	private void setScale(){
		Matrix matrix = new Matrix();		
		matrix.setScale(0.5f, 0.5f);
		iv_matrix.setImageMatrix(matrix);
	}
	
}
