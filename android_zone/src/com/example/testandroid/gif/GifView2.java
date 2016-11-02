package com.example.testandroid.gif;
import java.util.Timer;
import java.util.TimerTask;

import com.example.testandroid.R;
import com.example.testandroid.R.styleable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class GifView2 extends View {

	/**
	 * 默认�?�?
	 */
	private static final int DEFAULT_MOVIE_DURATION = 1000;

	private int mMovieResourceId;

	private Movie mMovie;

	private long mMovieStart;

	private int mCurrentAnimationTime = 0;

	private float mLeft;

	private float mTop;

	private float mScale;

	private int mMeasuredMovieWidth;

	private int mMeasuredMovieHeight;

	private boolean mVisible = true;

	private volatile boolean mPaused = false;
	
	//动画播放时间timer
	private Timer mTimer = null;
	
	//动画完成播放器
	private OnGifCompletionListener mOnGifCompletionListener = null;

	public GifView2(Context context) {
		this(context, null);
	}

	public GifView2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GifView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setViewAttributes(context, attrs, defStyle);
	}

	@SuppressLint("NewApi")
	private void setViewAttributes(Context context, AttributeSet attrs,
			int defStyle) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		// 从描述文件中读出gif的�?，创建出Movie实例
		final TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.GifView2);
		mMovieResourceId = array.getResourceId(R.styleable.GifView2_gif, -1);
		mPaused = array.getBoolean(R.styleable.GifView2_paused, false);
		array.recycle();
		if (mMovieResourceId != -1) {
			mMovie = Movie.decodeStream(getResources().openRawResource(
					mMovieResourceId));
		}
	}

	/**
	 * 设置gif图资�?
	 * 
	 * @param movieResId
	 */
	public void setMovieResource(int movieResId) {
		this.mMovieResourceId = movieResId;
		mMovie = Movie.decodeStream(getResources().openRawResource(
				mMovieResourceId));
		requestLayout();
	}

	public void setMovie(Movie movie) {
		this.mMovie = movie;
		requestLayout();
	}

	public Movie getMovie() {
		return mMovie;
	}

	public void setMovieTime(int time) {
		mCurrentAnimationTime = time;
		invalidate();
	}

	/**
	 * 设置暂停
	 * 
	 * @param paused
	 */
	public void setPaused(boolean paused) {
		this.mPaused = paused;
		if (!paused) {
			mMovieStart = android.os.SystemClock.uptimeMillis()
					- mCurrentAnimationTime;
		}
		invalidate();
	}
	
	
	/**
	 *设置动画播放完成的handler
	 */
	
	protected Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(mOnGifCompletionListener != null){
				mOnGifCompletionListener.onCompletion();
			}
		}
	};
	
	/**
	 *设置gif的播放时长
	 *
	 * @param second 单位秒
	 */
	public void setPlayTime(long second ,final OnGifCompletionListener listener){
		if(mTimer == null){
			mTimer = new Timer();
			mOnGifCompletionListener = listener;			
			mTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = mHandler.obtainMessage();
					mHandler.sendMessage(msg);
				}
			}, second*1000);	
		}	
	}

	/**
	 * 判断gif图是否停止了
	 * 
	 * @return
	 */
	public boolean isPaused() {
		return this.mPaused;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mMovie != null) {
			int movieWidth = mMovie.width();
			int movieHeight = mMovie.height();
			int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
			float scaleW = (float) movieWidth / (float) maximumWidth;
			mScale = 1f / scaleW;
			mMeasuredMovieWidth = maximumWidth;
			mMeasuredMovieHeight = (int) (movieHeight * mScale);
			setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);
		} else {
			setMeasuredDimension(getSuggestedMinimumWidth(),
					getSuggestedMinimumHeight());
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mLeft = (getWidth() - mMeasuredMovieWidth) / 2f;
		mTop = (getHeight() - mMeasuredMovieHeight) / 2f;
		mVisible = getVisibility() == View.VISIBLE;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (mMovie != null) {
			if (!mPaused) {
				updateAnimationTime();
				drawMovieFrame(canvas);
				invalidateView();
			} else {
				drawMovieFrame(canvas);
			}
		}
	}

	@SuppressLint("NewApi")
	private void invalidateView() {
		if (mVisible) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				postInvalidateOnAnimation();
			} else {
				invalidate();
			}
		}
	}

	private void updateAnimationTime() {
		long now = android.os.SystemClock.uptimeMillis();
		// 如果第一帧，记录起始时间
		if (mMovieStart == 0) {
			mMovieStart = now;
		}
		// 取出动画的时�?
		int dur = mMovie.duration();
		if (dur == 0) {
			dur = DEFAULT_MOVIE_DURATION;
		}
		// 算出�?��显示第几�?
		mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
	}

	private void drawMovieFrame(Canvas canvas) {
		// 设置要显示的帧，绘制即可
		mMovie.setTime(mCurrentAnimationTime);
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.scale(mScale, mScale);
		mMovie.draw(canvas, mLeft / mScale, mTop / mScale);
		canvas.restore();
	}

	@SuppressLint("NewApi")
	@Override
	public void onScreenStateChanged(int screenState) {
		super.onScreenStateChanged(screenState);
		mVisible = screenState == SCREEN_STATE_ON;
		invalidateView();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		mVisible = visibility == View.VISIBLE;
		invalidateView();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		mVisible = visibility == View.VISIBLE;
		invalidateView();
	}

	public interface OnGifCompletionListener{
		 void onCompletion();
	}	
}
