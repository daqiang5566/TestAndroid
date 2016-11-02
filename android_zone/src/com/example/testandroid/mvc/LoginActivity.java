package com.example.testandroid.mvc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginActivity extends Activity implements OnClickListener{

	LoginModel mLoginModel = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		mLoginModel = new LoginModel(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mLoginModel.login();
	}	
}
