package com.example.testandroid.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class CustomViewGroup extends ViewGroup {

	private final String Tag = "CustomViewGroup";

	public CustomViewGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int suggestWidth = getSuggestedMinimumWidth();
		int suggestHeight = getSuggestedMinimumHeight();

		int realWith = getDefaultSize(suggestWidth, widthMeasureSpec);
		int realHeight = getDefaultSize(suggestHeight, heightMeasureSpec);

		Log.i(Tag, "suggestWidth = " + suggestWidth + " suggestHeight = "+ suggestHeight);
		Log.i(Tag, "realWith = " + realWith + " realHeight = " + realHeight);
		setMeasuredDimension(300, 300);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		final int count = getChildCount();

		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				child.layout(0, 0, 500, 500);
			}
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float rawX =  event.getRawX();
		float 	x =  event.getX();
		
		Log.i(Tag, "rawX = " + rawX);
		Log.i(Tag, "rawX = " + rawX);
		Log.i(Tag, "x = " + x);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_CANCEL:

			break;
		default:
			break;
		}
		return true;		
	}
}
