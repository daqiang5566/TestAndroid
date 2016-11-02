package com.example.testandroid.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomView extends View{

	private final String Tag = "CustomView";
	private static final int DEFAULT_VIEW_WIDTH = 100;
	private static final int DEFAULT_VIEW_HEIGHT = 100;
		
	public CustomView(Context context) {
        super(context);
    }
 
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);     
    }
   
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {       
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
       int suggestWidth  =  getSuggestedMinimumWidth();
       int suggestHeight =  getSuggestedMinimumHeight();
       
       int realWith = getDefaultSize(suggestWidth, widthMeasureSpec);
       int realHeight = getDefaultSize(suggestHeight, heightMeasureSpec);

       Log.i(Tag, "suggestWidth = " + suggestWidth + " suggestHeight = " + suggestHeight);      
       Log.i(Tag, "realWith = " + realWith + " realHeight = " + realHeight);
         
       final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
       final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
       final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
       final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
       
       Log.i(Tag, "widthMode = " + widthMode + " heightMode = " + heightMode);      
       Log.i(Tag, "widthSize = " + widthSize + " heightSize = " + heightSize);
       
       int width  = measureDimension(DEFAULT_VIEW_WIDTH, widthMeasureSpec);
       int height = measureDimension(DEFAULT_VIEW_HEIGHT, heightMeasureSpec);
       
       setMeasuredDimension(width, height);      
    }
    
    protected int measureDimension( int defaultSize, int measureSpec ) {
        
        int result = defaultSize;
       
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
               
        //1. layout给出了确定的值，比如：100dp
        //2. layout使用的是match_parent，但父控件的size已经可以确定了，比如设置的是具体的值或者match_parent
        if (specMode == MeasureSpec.EXACTLY) {     
            result = specSize; //建议：result直接使用确定值
        }
        //1. layout使用的是wrap_content
        //2. layout使用的是match_parent,但父控件使用的是确定的值或者wrap_content
        else if (specMode == MeasureSpec.AT_MOST) {           
            result = Math.min(defaultSize, specSize); //建议：result不能大于specSize
        }
        //UNSPECIFIED,没有任何限制，所以可以设置任何大小
        //多半出现在自定义的父控件的情况下，期望由自控件自行决定大小
        else {     
            result = defaultSize;
        }
       
        return result;
    }
}
