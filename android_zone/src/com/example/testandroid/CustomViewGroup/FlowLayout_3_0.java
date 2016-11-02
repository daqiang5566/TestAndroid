package com.example.testandroid.CustomViewGroup;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.testandroid.R;
/**
 * 流布局LinearLayout
 * @author admin
 *
 */
public class FlowLayout_3_0 extends ViewGroup{
	
	private int margin_left = 0;
	private int margin_right = 0;
	private int margin_top = 0;
	private int margin_bottom = 0;

	public FlowLayout_3_0(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData(context, attrs, defStyleAttr);
	}

	public FlowLayout_3_0(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public FlowLayout_3_0(Context context) {
		this(context, null);
	}
	
	private void initData(Context context, AttributeSet attrs, int defStyleAttr){
		final Resources.Theme theme = context.getTheme();
		TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.FlowLayout_3_0, defStyleAttr, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
            case R.styleable.FlowLayout_3_0_childMarginLeft:
            	this.margin_left = a.getDimensionPixelSize(attr, margin_left);
            	break;
            case R.styleable.FlowLayout_3_0_childMarginRight:
            	this.margin_right = a.getDimensionPixelSize(attr, margin_right);
            	break;
            case R.styleable.FlowLayout_3_0_childMarginTop:
            	this.margin_top = a.getDimensionPixelSize(attr, margin_top);
            	break;
            case R.styleable.FlowLayout_3_0_childMarginBottom:
            	this.margin_bottom = a.getDimensionPixelSize(attr, margin_bottom);
            	break;
            }
		}
        a.recycle();
	}
	
	/**
	 * 设置子view的margin
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 */
	public void setChildMargin(int left, int right, int top, int bottom){
		this.margin_left = left;
		this.margin_right = right;
		this.margin_top = top;
		this.margin_bottom = bottom;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.i("FlowLayout_3_0", "onLayout = "+ " l = " + l + " t = " + t + " r = " + r +" b =" + b);		
		final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        boolean isDispersed = false;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width + margin_left + margin_right;
                y = row * (height + margin_top + margin_bottom) + (height + margin_top + margin_bottom);
                if (x > maxWidth) {
                    x = width + margin_left + margin_right;
                    row++;
                    y = row * (height + margin_top + margin_bottom) + (height + margin_top + margin_bottom);
                }
                
                child.layout(x - width - margin_right, y - height - margin_bottom, 
                		x - margin_right , y - margin_bottom);
            }
        }
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);	
		int maxHeight = MeasureSpec.getSize(heightMeasureSpec);		
		Log.i("FlowLayout_3_0", "onMeasure maxWidth = " + maxWidth + "maxHeight = " + maxHeight);		
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;

        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                //此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
        		Log.i("FlowLayout_3_0", index + " = width = " + width + "height = " + height);
        		
                x += width + (margin_left + margin_right);
                y = row * (height + margin_top + margin_bottom) + (height + margin_top + margin_bottom);
                if (x > maxWidth) {
                    x = width + (margin_left + margin_right);
                    row++;
                    y = row * (height + margin_top + margin_bottom) + (height + margin_top + margin_bottom);
                }
            }
        }
        // 设置容器所需的宽度和高度
		Log.i("FlowLayout_3_0", "maxWidth = " + maxWidth + "y = " + y);
        setMeasuredDimension(maxWidth, y);
	}

}
