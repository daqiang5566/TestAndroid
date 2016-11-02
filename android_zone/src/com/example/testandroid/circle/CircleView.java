package com.example.testandroid.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class CircleView extends View{

	public  final int R1 = 250;
	public  final int R2 = 50;
	
	//Ã¿Ò»¸ñ cos yµÄ²î¾à

	private  float width;
	private  float height;
  
	
	private   int newAngle=30;  //ÐÂµÄ½Ç¶È ¿ªÊ¼Ê±30
	private  int newAngle1;  //ÐÂµÄ½Ç¶È ¿ªÊ¼Ê±30
	
	public  double newX;
	public   double newY;   //¸øMainÌá¹©Î»ÖÃ ¿Õ¼äÒÆ¶¯µ½Õâ¸öÎ»ÖÃÉÏÃæÈ¥
	
	private Paint mPaint2;
	private boolean flag=false;
	private Paint mPaint3;
	private Paint mPaint;
	private Context mContext;
	private double newAngle11;
    
	public CircleView(Context context) {  
        this(context, null);  
    }  
	
	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.mContext=context;
		        
		initPaint();
	}

	/**
	 * ³õÊ¼»¯»­±Ê
	 */
	private void initPaint() {
		 // ÊµÀý»¯»­±Ê²¢´ò¿ª¿¹¾â³Ý  
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        mPaint.setStyle(Paint.Style.STROKE);  
        mPaint.setStrokeWidth(5);
        
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);  
        mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);  
        mPaint2.setColor(Color.BLUE);
        
       
        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);  
        
        mPaint3.setStrokeWidth(3);  
        mPaint3.setTextSize(80);  
        mPaint3.setColor(Color.CYAN);  
        mPaint3.setColor(Color.RED);  
        // ÏÂÃæÕâÐÐÊÇÊµÏÖË®Æ½¾ÓÖÐ£¬drawText¶ÔÓ¦¸ÄÎª´«ÈëtargetRect.centerX()  
        mPaint3.setTextAlign(Paint.Align.CENTER);  
        
        
        DisplayMetrics metric = new DisplayMetrics();
        
       ((CircleActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        
        width = R1;
        height = R1;        
        System.out.println(width+"---"+height);       
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		// »æÖÆÔ²»·  
	    canvas.drawCircle(R1, R1, R1, mPaint); 
		
	    //ÔÚÔ²»·30µÄÏà¶ÔÓÚ360 µÄÎ»ÖÃ Ë³Ê±Õë»­Ò»¸öÄ¬ÈÏÐ¡»úÆ÷ÈË
	    
	    System.out.println("ÏÖÔÚ½Ç¶È "+newAngle);
	    
	    canvas.drawText(newAngle+"",width, height,mPaint3);  
       // canvas.drawText(newAngle+"", targetRect.centerX(), baseline, mPaint3);  
	        
	    getNewLocation(); //¸ù¾ÝÅÐ¶Ï À´ÒÆ¶¯Ð¡Çò »ñµÃÐÂµÄÎ»ÖÃ
	    
	    //È·¶¨MainÖÐ¿Ø¼þµÄÎ»ÖÃ
	   
	    System.out.println("newX " +newX +"---------- newY "+newY);
	    
	    if(newAngle==90){
	    	mPaint2.setColor(Color.BLACK);
	    }else if(newAngle==180){
	    	mPaint2.setColor(Color.RED);
	    }else if(newAngle==270){
	    	mPaint2.setColor(Color.DKGRAY);
	    }else if(newAngle==360){
	    	mPaint2.setColor(Color.MAGENTA);
	    }
	   canvas.drawCircle((float)newX, (float)newY, R2, mPaint2); 
	}

	public  void getNewLocation() {
		/**
	     * 0-90µÄ±ä»¯¹æÂÉ
	     */
		if(newAngle==0){
			newX=width;
			newY=height-R1;
		}
		else if(newAngle==90){
			newX=width+R1;
			newY=height;
		}
		else if(newAngle==180){
			newX=width;
			newY=height+R1;
		}
		else if(newAngle==270){
			newX=width-R1;
			newY=height;
		}
		else if(newAngle==360){
			newX=width;
			newY=height-R1;
		}
		else if(newAngle>360){
			newAngle=360;
			newX=width;
			newY=height-R1;
		}
		else if(newAngle>0&&newAngle<90){
	    newX = width+ (R1*Math.sin(newAngle*Math.PI/180));
	    newY = height-(R1*Math.cos(newAngle*Math.PI/180));
	    }
	    
	    /**
	     * 90-180µÄ±ä»¯¹æÂÉ
	     */
	    else if(newAngle>90&&newAngle<180){
	    	newAngle1=180-newAngle;
	    	 newX=width+ (R1*Math.sin(newAngle1*Math.PI/180));
	    	 newY=height+(R1*Math.cos(newAngle1*Math.PI/180));
	    }
	    
	    /**
	     * 180-270µÄ±ä»¯¹æÂÉ
	     */
	    else if(newAngle>180&&newAngle<270){
	    	newAngle1=270-newAngle;
	    	 newX=width- (R1*Math.cos(newAngle1*Math.PI/180));
	    	 newY=height+(R1*Math.sin(newAngle1*Math.PI/180));
	    }
	    
	    /**
	     * 270-360µÄ±ä»¯¹æÂÉ
	     */
	    else if(newAngle>270&&newAngle<360){
	    	newAngle1=360-newAngle;
	    	 newX=width- (R1*Math.sin(newAngle1*Math.PI/180));
	    	 newY=height-(R1*Math.cos(newAngle1*Math.PI/180));
	    }
	}
	
	public void startRotation(int angle){
		newAngle = angle;
		invalidate();
	}
}
