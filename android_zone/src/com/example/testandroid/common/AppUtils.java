package com.example.testandroid.common;

import android.content.Context;

public class AppUtils {

	private AppUtils(){
		
	}
	
	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().densityDpi;
		return (int) (dipValue * (scale / 160));
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().densityDpi;
		return (int) ((pxValue * 160) / scale);
	}
}
