package com.example.testandroid.testevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.testandroid.common.TouchEventUtil;

public class TestEventChild extends LinearLayout{

	public TestEventChild(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TestEventChild(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TestEventChild(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.i("TestEvent", "child->dispatchTouchEvent " + TouchEventUtil.getTouchAction(ev.getAction()));
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.i("TestEvent", "child->onInterceptTouchEvent " + TouchEventUtil.getTouchAction(ev.getAction()));
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.i("TestEvent", "child->onTouchEvent " + TouchEventUtil.getTouchAction(ev.getAction()));
		return false;
	}
}
