package com.example.testandroid.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MvpActivity extends Activity implements MvpContract.TestMvpView,OnClickListener{

	private MvpContract.TestMvpPresenter mPresenter;
	private TextView tv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		mPresenter = new  TestMvpPresenterImpl(this);
	}
		
	@Override
	public void updateTitle(String title) {
		// TODO Auto-generated method stub
		tv_title.setText(title);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mPresenter.getTitle();
	}	
}
