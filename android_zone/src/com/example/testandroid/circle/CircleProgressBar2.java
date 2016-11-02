package com.example.testandroid.circle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CircleProgressBar2 extends ImageView {

	private Drawable mDrawable;
	RotateAnimation rtAni;
	
	public CircleProgressBar2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
//		mDrawable  = new MaterialProgressDrawable(getContext(), this);
//		super.setImageDrawable(mDrawable);
	}

	public CircleProgressBar2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
//		mDrawable  = new MaterialProgressDrawable(getContext(), this);
//		super.setImageDrawable(mDrawable);
	}

	public CircleProgressBar2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
//		mDrawable  = new MaterialProgressDrawable(getContext(), this);
//		super.setImageDrawable(mDrawable);
	}
	
	public void startRotationAni(){
		
		rtAni = new RotateAnimation(0f,360f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF,0.5f); 
		rtAni.setRepeatCount(-1);
		rtAni.setDuration(1000);
		rtAni.setRepeatMode(Animation.INFINITE);
		LinearInterpolator lin = new LinearInterpolator();   
		rtAni.setInterpolator(lin);
		this.setAnimation(rtAni);
	}
}
