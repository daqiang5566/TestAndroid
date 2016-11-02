package com.example.testandroid.pulltorefresh;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.testandroid.R;

//这个类你不用管，别人写好的，这是拉动效果类
@SuppressLint("NewApi")
public class PullToRefreshView extends LinearLayout {

	private static final String TAG = "PullToRefreshView";
	private String preferenceString = "PullToRefreshView";

	// 上下文
	private Context context;

	// refresh states
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final int REFRESHING = 4;
	// pull state
	private static final int PULL_UP_STATE = 0;
	private static final int PULL_DOWN_STATE = 1;

	private boolean isLocation = true;
	private boolean isHideHeader = false;
	private boolean isHideFooter = false;
	private boolean isStartAnimation = true;
	private long refreshTime = 0;

	/**
	 * last y
	 */
	private int mLastMotionY;
	
	private View mScaleBg;
	/**
	 * header view
	 */
	private View mHeaderView;
	/**
	 * footer view
	 */
	private View mFooterView;
	/**
	 * list or grid
	 */
	private AbsListView mAdapterView;
	/**
	 * scrollview
	 */
	private ScrollView mScrollView;
	/**
	 * header view height
	 */
	private int mHeaderViewHeight;
	/**
	 * footer view height
	 */
	private int mFooterViewHeight;
	/**
	 * header view image
	 */
	private ImageView mHeaderImageView;
	/**
	 * footer view image
	 */
	private ImageView mFooterImageView;
	/**
	 * header text image
	 */
	private TextView mHeaderTextView;

	/**
	 * footer text image
	 */
	private TextView mFooterTextView;

	/**
	 * header refresh time
	 */
	private TextView mHeaderRefreshTime;
	/**
	 * header progress bar
	 */
	private ProgressBar mHeaderProgressBar;
	/**
	 * footer progress bar
	 */
	private ProgressBar mFooterProgressBar;
	/**
	 * layout inflater
	 */
	private LayoutInflater mInflater;
	/**
	 * header view current state
	 */
	private int mHeaderState;
	/**
	 * footer view current state
	 */
	private int mFooterState;
	/**
	 * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
	 */
	private int mPullState;
	/**
	 * 变为向下的箭头,改变箭头方向
	 */
	private RotateAnimation mFlipAnimation;
	/**
	 * 变为逆向的箭头,旋转
	 */
	private RotateAnimation mReverseFlipAnimation;
	/**
	 * footer refresh listener
	 */
	private OnFooterRefreshListener mOnFooterRefreshListener;
	/**
	 * footer refresh listener
	 */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;

	/**
	 * Firstitem refresh listener
	 */
	private OnFirstVisibleListener onFirstVisibleListener;

	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public PullToRefreshView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	/**
	 * init
	 * 
	 * @description
	 * @param context
	 * @author willm
	 */

	private void init() {
		// Load all of the animations we need in code rather than through XML
		mFlipAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		mFlipAnimation.setDuration(250);
		mFlipAnimation.setFillAfter(true);
		mReverseFlipAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(250);
		mReverseFlipAnimation.setFillAfter(true);

		mInflater = LayoutInflater.from(getContext());
		// header view 在此添加,保证是第一个添加到linearlayout的最上端
		addHeaderView();
	}

	private void addHeaderView() {
		// header view
		mHeaderView = mInflater.inflate(R.layout.refresh_header, this, false);
		mHeaderImageView = (ImageView) mHeaderView
				.findViewById(R.id.pull_to_refresh_image);
		mHeaderProgressBar = (ProgressBar) mHeaderView
				.findViewById(R.id.pull_to_refresh_progress);
		mHeaderTextView = (TextView) mHeaderView
				.findViewById(R.id.pull_to_refresh_text);
		mHeaderRefreshTime = (TextView) mHeaderView
				.findViewById(R.id.pull_to_refresh_time);

		// header layout
		measureView(mHeaderView);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mHeaderViewHeight);
		// 设置topMargin的值为负的header View高度,即将其隐藏在最上方
		params.topMargin = -(mHeaderViewHeight);
		// mHeaderView.setLayoutParams(params1);
		addView(mHeaderView, params);
	}

	private void addFooterView() {
		// footer view
		mFooterView = mInflater.inflate(R.layout.refresh_footer, this, false);
		mFooterImageView = (ImageView) mFooterView.findViewById(R.id.pull_to_load_image);
		mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pull_to_load_progress);
		mFooterTextView = (TextView) mFooterView.findViewById(R.id.pull_to_refresh_text);

		// footer layout
		measureView(mFooterView);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,mFooterViewHeight);
		
		Log.i(TAG, "height = " + this.getHeight());
		
		// int top = getHeight();
		params.topMargin = -mFooterViewHeight;//在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
		// getHeight()什么时候会赋值,稍候再研究一下
		// 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
		
		addView(mFooterView, params);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// footer view 在此添加保证添加到linearlayout中的最后
		Log.i(TAG, "onFinishInflate");		
		//addFooterView();
		initContentAdapterView();
	}
		
/*	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.i(TAG, "onMeasure");
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		Log.i(TAG, "onLayout");
	}*/

	/**
	 * init AbsListView like ListView,GridView and so on;or init ScrollView
	 * 
	 * @description willm
	 */
	private void initContentAdapterView() {
		
		int count = getChildCount();
		
//		if (count < 3) {
//			throw new IllegalArgumentException(
//					"this layout must contain 3 child views,and AbsListView or ScrollView must in the second position!");
//		}
		
		View view = null;
		
		for (int i = 0; i < count; ++i) {
			view = getChildAt(i);
			if (view instanceof AbsListView) {
				mAdapterView = (AbsListView) view;
				
			}
			
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AbsListView or ScrollView in this layout!");
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		Log.i(TAG, "onInterceptTouchEvent");
		int y = (int) e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 首先拦截down事件,记录y坐标
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaY > 0 是向下运动,< 0是向上运动
			int deltaY = y - mLastMotionY;
			if(mFooterView == null){
				addFooterView();
			}
			
			boolean isScroll = isRefreshViewScroll(deltaY);
			Log.i(TAG, "isScroll = " + isScroll);			
			if (isScroll) {
				return true;
			}			
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	/*
	 * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
	 * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
	 */
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i(TAG, "onTouchEvent");
		int y = (int) event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// onInterceptTouchEvent已经记录
			// mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = (y - mLastMotionY) / 2;
			if (mPullState == PULL_DOWN_STATE) {
				// PullToRefreshView执行下拉
				Log.i(TAG, " pull down!parent view move!");
				headerPrepareToRefresh(deltaY);
				// setHeaderPadding(-mHeaderViewHeight);
			} else if (mPullState == PULL_UP_STATE) {
				// PullToRefreshView执行上拉
				Log.i(TAG, "pull up!parent view move!");
				footerPrepareToRefresh(deltaY);
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					// 开始刷新
					headerRefreshing();
				} else {
					// 还没有执行刷新，重新隐藏
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			} else if (mPullState == PULL_UP_STATE) {
				if (Math.abs(topMargin) >= mHeaderViewHeight
						+ mFooterViewHeight) {
					// 开始执行footer 刷新
					footerRefreshing();
				} else {
					// 还没有执行刷新，重新隐藏
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 是否应该到了父View,即PullToRefreshView滑动
	 * 
	 * @param deltaY
	 *            , deltaY > 0 是向下运动,< 0是向上运动
	 * @return
	 */

	private boolean isRefreshViewScroll(int deltaY) {
		if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
			return false;
		}
		
		Log.i(TAG, " deltaY = " + deltaY);
		
		// 对于ListView和GridView
		if (mAdapterView != null) {
			// 子view(ListView or GridView)滑动到最顶端
			if (deltaY > 0) {

				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					// 如果mAdapterView中没有数据,不拦截
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0 && child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				
				if (mAdapterView.getFirstVisiblePosition() == 0 && Math.abs(top - padding) <= 8) {// 这里之前用3可以判断,但现在不行,还没找到原因
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				View lastChild = mAdapterView.getChildAt(mAdapterView.getChildCount() - 1);
				if (lastChild == null) {
					// 如果mAdapterView中没有数据,不拦截
					mPullState = PULL_UP_STATE;
					return true;
				}
				// 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
				// 等于父View的高度说明mAdapterView已经滑动到最后
				int height = getHeight();
				int lastChildBottom = lastChild.getBottom();
				int childCount = mAdapterView.getCount();
				int lastVisiblePos = mAdapterView.getLastVisiblePosition();
				
				Log.i(TAG, "height = " + height + " lastChildBottom = " + lastChildBottom + 
						" childCount = " + childCount + " lastVisiblePos = " + lastVisiblePos);
				
				if (lastChild.getBottom() <= getHeight() && mAdapterView.getLastVisiblePosition() == mAdapterView.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		
		// 对于ScrollView
		if (mScrollView != null) {
			// 子scroll view滑动到最顶端
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0 && child.getMeasuredHeight() <= getHeight() + mScrollView.getScrollY()) {
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	/**
	 * header 准备刷新,手指移动过程,还没有释放
	 * 
	 * @param deltaY,手指滑动的距离
	 */
	
	private void headerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
		mHeaderProgressBar.setVisibility(View.GONE);
		mHeaderImageView.setVisibility(View.VISIBLE);

		if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(mFlipAnimation);
			mHeaderTextView
					.setText(R.string.self_pull_to_refresh_release_label);
			mHeaderState = RELEASE_TO_REFRESH;
		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(mFlipAnimation);
			// mHeaderImageView.
			mHeaderTextView.setText(R.string.self_pull_to_refresh_pull_label);
			mHeaderRefreshTime.setVisibility(View.VISIBLE);
			mHeaderState = PULL_TO_REFRESH;
		}
		mHeaderRefreshTime.setVisibility(View.GONE);
	}

	/**
	 * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
	 * 高度是一样，都是通过修改header view的topmargin的值来达到
	 * 
	 * @param deltaY
	 *            ,手指滑动的距离
	 */
	private void footerPrepareToRefresh(int deltaY) {
		Log.i(TAG, "footerPrepareToRefresh deltaY = " + deltaY);
		
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 如果header view topMargin 的绝对值大于或等于header + footer 的高度
		// 说明footer view 完全显示出来了，修改footer view 的提示状态
		mFooterTextView.setVisibility(View.VISIBLE);
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight) && mFooterState != RELEASE_TO_REFRESH) {
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(mFlipAnimation);
			mFooterState = RELEASE_TO_REFRESH;
			mFooterTextView.setText(R.string.self_pull_to_refresh_footer_release_label);
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(mFlipAnimation);
			mFooterState = PULL_TO_REFRESH;
			mFooterTextView.setText(R.string.self_pull_to_refresh_footer_pull_label);
		}
	}

	/**
	 * 修改Header view top margin的值
	 * 
	 * @description
	 * @param deltaY
	 * @return willm 2012/12/4/12:06
	 */
	@SuppressLint("NewApi")
	private int changingHeaderViewTopMargin(int deltaY) {

		LayoutParams params = (LayoutParams) mFooterView.getLayoutParams();
		float newTopMargin = 200;
/*
		// 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
		// 表示如果是在上拉后一段距离,然后直接下拉
		if (deltaY > 0 && mPullState == PULL_UP_STATE
				&& Math.abs(params.topMargin) <= mHeaderViewHeight) {
			return params.topMargin;
		}
		// 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
		if (deltaY < 0 && mPullState == PULL_DOWN_STATE
				&& Math.abs(params.topMargin) >= mHeaderViewHeight) {
			return params.topMargin;
		}*/
		params.bottomMargin = (int) newTopMargin;
		mFooterView.setLayoutParams(params);

		invalidate();
		return params.topMargin;
	}

	/**
	 * header refreshing
	 * 
	 * @description willm 2012/12/4 上午 12:06
	 */
	private void headerRefreshing() {
		mHeaderState = REFRESHING;
		setHeaderTopMargin(0);
		mHeaderImageView.setVisibility(View.GONE);
		mHeaderImageView.clearAnimation();
		mHeaderImageView.setImageDrawable(null);
		mHeaderProgressBar.setVisibility(View.VISIBLE);
		mHeaderTextView.setText(R.string.self_pull_to_refresh_refreshing_label);
		mHeaderRefreshTime.setVisibility(View.GONE);

		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this);
		}

		if (isHideHeader) {
			onHeaderRefreshComplete();
		}
	}

	/**
	 * footer refreshing
	 * 
	 * @description willm 2012/12/4 上午 12:07
	 */
	private void footerRefreshing() {
		mFooterState = REFRESHING;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		mFooterImageView.setVisibility(View.GONE);
		mFooterImageView.clearAnimation();
		mFooterImageView.setImageDrawable(null);
		mFooterProgressBar.setVisibility(View.VISIBLE);

		mFooterTextView.setText(R.string.self_pull_to_refresh_footer_refreshing_label);
		if (mOnFooterRefreshListener != null) {
			mOnFooterRefreshListener.onFooterRefresh(this);
		}

		if (isHideFooter) {
			onFooterRefreshComplete();
		}
	}

	/**
	 * 设置header view 的topMargin的值
	 * 
	 * @description
	 * @param topMargin
	 *            ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
	 *            willm 2012/12/4 上午 12:07
	 */
	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		if (!isStartAnimation) {
			params.topMargin = topMargin;
			mHeaderView.setLayoutParams(params);
			if (mScaleBg != null) {
				mScaleBg.setScaleX(1.0f);
				mScaleBg.setScaleY(1.0f);
			}
			// invalidate();
		} else {
			ValueAnimator valueAnimator = ValueAnimator.ofInt(params.topMargin,
					topMargin);
			// 利用AnimatorUpdateListener监听动画的执行
			valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator va) {
					// 得到动画当前执行的值
					Integer animatedValue = (Integer) va.getAnimatedValue();
					LayoutParams marginLayoutParams = (LayoutParams) mHeaderView
							.getLayoutParams();
					marginLayoutParams.topMargin = animatedValue;
					// 不断修改Button的Margin值
					mHeaderView.setLayoutParams(marginLayoutParams);
					// invalidate();
				}
			});
			valueAnimator.setDuration(200);
			valueAnimator.setInterpolator(new LinearInterpolator());
			valueAnimator.setTarget(mHeaderView);
			valueAnimator.start();

			if (mScaleBg != null) {
				PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(
						"scaleX", mScaleBg.getScaleX(), 1);
				PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat(
						"scaleY", mScaleBg.getScaleY(), 1);
				ObjectAnimator scaleAnimator = ObjectAnimator
						.ofPropertyValuesHolder(mScaleBg, pvhY, pvhZ);
				scaleAnimator.setDuration(200);
				scaleAnimator.setInterpolator(new LinearInterpolator());
				scaleAnimator.start();
			}
		}
	}

	/***
	 * 设置是否定位
	 * 
	 * @description
	 * @param topMargin
	 *            isNeed 为true打开，false为不定位。
	 * */

	public void setLocationNeed(boolean isNeed) {
		isLocation = isNeed;
	}

	/**
	 * 隐藏头部
	 * **/
	public void setHideHeader(boolean isHided) {
		if (mHeaderView != null) {
			isHideHeader = isHided;
			mHeaderView.setVisibility(isHided ? View.INVISIBLE : View.VISIBLE);
		}
	}

	/**
	 * 隐藏底部
	 * **/
	public void setHideFooter(boolean isHided) {
		if (mFooterView != null) {
			isHideFooter = isHided;
			mFooterView.setVisibility(isHided ? View.INVISIBLE : View.VISIBLE);
		}
	}

	/**
	 * header view 完成更新后恢复初始状态
	 * 
	 * @description willm 2012/12/4 上午 12:07
	 */
	public boolean onHeaderRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
		mHeaderProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mHeaderImageView.setVisibility(View.GONE);
		mHeaderProgressBar.setVisibility(View.INVISIBLE);
		mHeaderRefreshTime.setVisibility(View.GONE);
		mHeaderState = PULL_TO_REFRESH;
		return true;
	}

	public void setRefreshing() {

		headerRefreshing();
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void onHeaderRefreshComplete(CharSequence lastUpdated) {
		onHeaderRefreshComplete();
	}

	/**
	 * footer view 完成更新后恢复初始状态
	 * 
	 * @return
	 */
	public boolean onFooterRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mFooterImageView.setVisibility(View.VISIBLE);
		mFooterImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
		mFooterProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mFooterState = PULL_TO_REFRESH;
		mFooterTextView.setVisibility(View.INVISIBLE);
		mFooterProgressBar.setVisibility(View.INVISIBLE);
		mFooterImageView.setVisibility(View.INVISIBLE);
		return true;
	}

	public void setScaleBg(View scaleBg) {
		this.mScaleBg = scaleBg;
	}

	/**
	 * 获取当前header view 的topMargin
	 * 
	 * @description
	 * @return willm 2012/12/4 上午 12:08
	 */
	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}

	/**
	 * set headerRefreshListener
	 * 
	 * @description
	 * @param headerRefreshListener
	 *            willm 2012/12/4 上午 12:08
	 */
	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener) {
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	public void setOnFooterRefreshListener(
			OnFooterRefreshListener footerRefreshListener) {
		mOnFooterRefreshListener = footerRefreshListener;
	}

	public void setOnFirstVisibleListener(
			OnFirstVisibleListener onfirstVisibleListener) {
		onFirstVisibleListener = onfirstVisibleListener;
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid footer
	 * view should be refreshed.
	 */
	public interface OnFooterRefreshListener {
		public void onFooterRefresh(PullToRefreshView view);
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid header
	 * view should be refreshed.
	 */
	public interface OnHeaderRefreshListener {
		public void onHeaderRefresh(PullToRefreshView view);
	}

	/**
	 * Interface definition for a callback to be invoked when firstitem occurs
	 * view should be refreshed.
	 */

	public interface OnFirstVisibleListener {
		public void onFirstVisible(int firstVisibleItem, int visibleItemCount,
				AbsListView view);
	}

}
