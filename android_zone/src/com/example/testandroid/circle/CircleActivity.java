package com.example.testandroid.circle;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final CircleView circleView = new CircleView(this);	
		final CircleView2 circleView2 = new CircleView2(this);	
		
		Animation ani = new Animation(){
			@Override
			protected void applyTransformation(float interpolatedTime,Transformation t) {
				// TODO Auto-generated method stub
				super.applyTransformation(interpolatedTime, t);
				int angle = (int) ((1-interpolatedTime)*360);
				System.out.println("interpolatedTime = "+ interpolatedTime + "angle = " + angle);
				circleView.startRotation(angle);				
			}
		};
		
		//ani.setDuration(2000);
		//circleView.startAnimation(ani);
		setContentView(circleView2);
	}
	
	class CircleAnimation extends Animation {  
	    private int r=100;//旋转半径  
	    private int angle=360;//旋转角度  
	              
	    public CircleAnimation(){
	    	
	    }  
	              
	    public CircleAnimation(int r,int angle){  
	        this.r = r;  
	        this.angle = angle;  
	    }  
	      
	    @Override    
	    public void initialize(int width, int height, int parentWidth, int parentHeight) {    
	        super.initialize(width, height, parentWidth, parentHeight);    
	    }  
	      
	    @Override  
	    protected void applyTransformation(float interpolatedTime,Transformation t) {  
	        int dx=(int)(r*Math.cos(interpolatedTime*Math.PI*angle/180));  
	        int dy=(int)(r*Math.sin(interpolatedTime*Math.PI*angle/180));           
	        t.getMatrix().setTranslate(dx,dy);  
	    }  
	}  
	
	/** 
	 * 左右抖动 
	 * @author reyo 
	 * 
	 */  
	class ShakeAnimation extends Animation {  
	    private int shakeTimes=7;//摇摆次数  
	    private int shakeRange=50;//摇摆幅度  
	              
	    public ShakeAnimation(){  
	                  
	    }  
	              
	    public ShakeAnimation(int shakeTimes,int shakeRange){  
	        this.shakeTimes = shakeTimes;  
	        this.shakeRange = shakeRange;  
	    }  
	    
	    @Override    
	    public void initialize(int width, int height, int parentWidth, int parentHeight) {    
	        super.initialize(width, height, parentWidth, parentHeight);    
	    }  
	      
	    @Override  
	    protected void applyTransformation(float interpolatedTime,Transformation t) {  
	        int dx=(int)(shakeRange*Math.sin(interpolatedTime*Math.PI*shakeTimes));  
	        int dy=0;  
	        t.getMatrix().setTranslate(dx,dy);  
	    }  
	}  
}
