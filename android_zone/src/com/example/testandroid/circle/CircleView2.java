package com.example.testandroid.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.testandroid.R;
import com.example.testandroid.common.AppUtils;

public class CircleView2 extends View{

	public  int R1 = 0;
	private int startAngle = 270;  
	private int sweepAngle = 90;
	public  double newX;
	public  double newY;  	
	private Paint mPaint2;
	private Paint mPaint;
	protected RectF arcElements;
	private Context mContext;
    
	public CircleView2(Context context) {  
        this(context, null);  
    }  
	
	public CircleView2(Context context, AttributeSet attrs) {
		super(context, attrs);		
		this.mContext=context;		        
		initPaint();
	}

	private void initPaint() {
		R1 = AppUtils.dip2px(mContext, 160);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        mPaint.setStyle(Paint.Style.STROKE);  
        mPaint.setStrokeWidth(5);
        mPaint.setColor(mContext.getResources().getColor(R.color.color_e94647));
       
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);  
        mPaint2.setStyle(Paint.Style.STROKE);  
	    mPaint2.setColor(mContext.getResources().getColor(R.color.color_e94647));
	    mPaint2.setStrokeWidth(15);
                                                		       
		arcElements = new RectF();	
		arcElements.left = 0;
		arcElements.top = 0;
		arcElements.right = R1*2;
		arcElements.bottom = R1*2;
	}

	@Override
	protected void onDraw(Canvas canvas) {		
	    canvas.drawCircle(R1, R1, R1, mPaint); 	
//	    canvas.drawRect(arcElements, mPaint);
        canvas.drawArc(arcElements, startAngle, sweepAngle, false, mPaint2);
	}
	
	public void setAngle(int angle){
		sweepAngle = angle;
		invalidate();
	}
}
