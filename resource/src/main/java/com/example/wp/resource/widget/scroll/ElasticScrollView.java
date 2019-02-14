package com.example.wp.resource.widget.scroll;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.example.wp.resource.utils.LogUtils;

/**
 * 多点触控的弹性ScrollView
 */
public class ElasticScrollView extends ScrollView {
	private boolean canScroll;
	private GestureDetector mGestureDetector;
	private Point mOriginPos = new Point();
	private OverScroller mOverScroller;
	
	public ElasticScrollView(Context context) {
		super(context);
		init(context);
	}
	
	public ElasticScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public ElasticScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
		mOverScroller = new OverScroller(context, interpolator);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		canScroll = true;
		setVerticalScrollBarEnabled(false);
	}
	
	private View mTargetView;
	private float mLastY;
	private Rect normal = new Rect();
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() > 0) {
			mTargetView = getChildAt(0);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mTargetView != null) {
			mOriginPos.x = mTargetView.getLeft();
			mOriginPos.y = mTargetView.getTop();
		}
	}
	
	class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (canScroll)
				if (Math.abs(distanceY) >= Math.abs(distanceX))
					canScroll = true;
				else
					canScroll = false;
			
			if (canScroll) {
				if (e1 != null && e1.getAction() == MotionEvent.ACTION_DOWN) {
					moveYY = 0;
					startY = e1.getY();
				}
			}
			return canScroll;
		}
	}
	
	private float startY, moveYY;
	private int activePointerId;
	/**
	 * A null/invalid pointer ID.
	 */
	private final int INVALID_POINTER = -1;
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		final int action = ev.getActionMasked();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				activePointerId = ev.getPointerId(0);
				startY = mLastY = ev.getY(0);
				break;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// LogUtils.d("test_wp", "onInterceptTouchEvent()--canChildScrollUp : " + canChildScrollUp(this));
		// LogUtils.d("test_wp", "onInterceptTouchEvent()--canChildScrollDown : " + canChildScrollDown(this));
		// float dy = ev.getY(0) - startY;
		// LogUtils.d("test_wp", "onInterceptTouchEvent()--dy : " + dy);
		// if ((!canChildScrollUp(this) && dy > 0)
		// 		|| (!canChildScrollDown(this) && dy < 0)) {
		// 	if (ev.getAction() == MotionEvent.ACTION_UP) {
		// 		canScroll = true;
		// 	}
		// 	return mGestureDetector.onTouchEvent(ev);
		// }
		
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// LogUtils.d("test_wp", "onTouchEvent()--canChildScrollUp : " + canChildScrollUp(this));
		// LogUtils.d("test_wp", "onTouchEvent()--canChildScrollDown : " + canChildScrollDown(this));
		float dy = event.getY(0) - startY;
		android.util.Log.d("test_wp", "onTouchEvent()--dy : " + dy);
		// if ((canChildScrollUp(this) && dy > 0)
		// 		|| (canChildScrollDown(this) && dy < 0)) {
		// 	return super.onTouchEvent(event);
		// }
		// android.util.Log.d("test_wp", "isTop() : " + isTop());
		// android.util.Log.d("test_wp", "isBottom() : " + isBottom());
		//
		// android.util.Log.d("test_wp", "getScrollY() : " + getScrollY());
		// android.util.Log.d("test_wp", "getHeight() : " + getHeight());
		// android.util.Log.d("test_wp", "computeVerticalScrollExtent() : " + computeVerticalScrollExtent());
		// android.util.Log.d("test_wp", "computeVerticalScrollOffset() : " + computeVerticalScrollOffset());
		// android.util.Log.d("test_wp", "computeVerticalScrollRange() : " + computeVerticalScrollRange());
		
		boolean blocked = false;
		if ((isTop() && dy >= 0) || (isBottom() && dy <= 0)) {
			blocked = true;
		}
		if (!blocked) {
			activePointerId = event.getPointerId(event.getActionIndex());
			mLastY = event.getY(event.getActionIndex());
			return super.onTouchEvent(event);
		}
		
		// android.util.Log.d("test_wp", "onTouchEvent()--mTargetView : " + mTargetView);
		if (mTargetView == null) {
			return false;
		}
		final int action = event.getActionMasked();
		final int actionIndex = event.getActionIndex();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				// 将新落下来那根手指作为活动手指
				activePointerId = event.getPointerId(actionIndex);
				mLastY = event.getY(actionIndex);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				if (activePointerId == event.getPointerId(actionIndex)) { // 如果松开的是活动手指
					final int newPointerIndex = actionIndex == 0 ? 1 : 0;
					activePointerId = event.getPointerId(newPointerIndex);
					mLastY = event.getY(newPointerIndex);
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				activePointerId = INVALID_POINTER;
				if (mOnPullListener != null && mTargetView != null) {
					// 往下拉的距离超过了100dp
					if (mTargetView.getTop() - mOriginPos.y > dp2px(100)) {
						mOnPullListener.onDownPull();
					} else if (mOriginPos.y - mTargetView.getTop() > dp2px(100)) { // 往上拉的距离超过了100dp
						mOnPullListener.onUpPull();
					}
				}
				if (!normal.isEmpty()) {
					mOverScroller.startScroll(mTargetView.getLeft(), mTargetView.getTop(), 0, mOriginPos.y - mTargetView.getTop(), 200);
					invalidate();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (activePointerId == INVALID_POINTER) {
					//					int activePointerIndex = event.getPointerId(0) == INVALID_POINTER ? 1 : 0;
					android.util.Log.d("ACTION_MOVE", "无效手指");
					activePointerId = event.getPointerId(actionIndex);
				}
				final int pointerIndex = event.findPointerIndex(activePointerId);
				float currentY = event.getY(pointerIndex);
				// 假如是下拉, currentY > perY, offset > 0
				Log.d("test_wp", "-----currentY: " + currentY);
				Log.d("test_wp", "-----mLastY: " + mLastY);
				int offset = (int) (currentY - mLastY);
				Log.d("test_wp", "-----offset: " + offset);
				mLastY = currentY;
				int deltaY = Math.abs(mTargetView.getTop() - mOriginPos.y);
				// Log.d("test_wp", "isNeedMove(): " + isNeedMove());
				// if (isNeedMove()) {
				if (normal.isEmpty()) {
					normal.set(mTargetView.getLeft(), mTargetView.getTop(), mTargetView.getRight(), mTargetView.getBottom());
				}
				Log.d("test_wp", "-----deltaY: " + deltaY);
				int offset1 = calcateNewOffset(deltaY, offset);
				Log.d("test_wp", "-----offset1: " + offset1);
				ViewCompat.offsetTopAndBottom(mTargetView, offset1);
				if (mOnPullListener != null) {
					mOnPullListener.onPullOffset(mTargetView.getTop());
				}
				// }
			default:
				break;
		}
		
		return true;
	}
	
	/**
	 * 判断ScrollView能否继续下拉, false表示已经到了最顶端, 无法继续下拉
	 *
	 * @param view
	 * @return
	 */
	public boolean canChildScrollUp(View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			if (view instanceof AbsListView) {
				final AbsListView absListView = (AbsListView) view;
				return absListView.getChildCount() > 0
						&& (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
						.getTop() < absListView.getPaddingTop());
			} else {
				return ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0;
			}
		} else {
			return ViewCompat.canScrollVertically(view, -1);
		}
	}
	
	/**
	 * 判断View是否可以继续上拉, false表示已经达到了最低端, 无法再继续上拉
	 *
	 * @return canChildScrollDown
	 */
	public boolean canChildScrollDown(View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			if (view instanceof AbsListView) {
				final AbsListView absListView = (AbsListView) view;
				return absListView.getChildCount() > 0 && absListView.getAdapter() != null
						&& (absListView.getLastVisiblePosition() < absListView.getAdapter().getCount() - 1 || absListView.getChildAt(absListView.getChildCount() - 1)
						.getBottom() < absListView.getPaddingBottom());
			} else {
				return ViewCompat.canScrollVertically(view, 1) || view.getScrollY() > 0;
			}
		} else {
			return ViewCompat.canScrollVertically(view, 1);
		}
	}
	
	public boolean isTop() {
		if (Build.VERSION.SDK_INT >= 14) {
			return !canScrollVertically(-1);
		} else {
			return computeVerticalScrollOffset() <= 0;
		}
	}
	
	public boolean isBottom() {
		if (Build.VERSION.SDK_INT >= 14) {
			return !canScrollVertically(1);
		} else {
			return computeVerticalScrollOffset() + computeVerticalScrollExtent() >= computeVerticalScrollRange();
		}
	}
	
	/**
	 * 每隔多少距离就开始增大阻力系数, 数值越小阻力就增大的越快
	 */
	private final int LENGTH = 150;
	/**
	 * 阻力系数, 越大越难拉
	 */
	private int mFraction = 2;
	
	private int calcateNewOffset(int deltaY, int offset) {
		int newOffset = offset / (mFraction + deltaY / LENGTH);
		if (newOffset == 0) {
			newOffset = offset >= 0 ? 1 : -1;
		}
		return newOffset;
	}
	
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mOverScroller.computeScrollOffset()) {
			int offset = mOverScroller.getCurrY() - mTargetView.getTop();
			ViewCompat.offsetTopAndBottom(mTargetView, offset);
			if (mOnPullListener != null) {
				mOnPullListener.onPullOffset(mTargetView.getTop());
			}
			invalidate();
		}
		
	}
	
	public boolean isNeedMove() {
		// int offset = mTargetView.getMeasuredHeight() - getHeight();
		// int scrollY = getScrollY();
		// Log.d("test_wp", "-----offset= "+offset);
		// Log.d("test_wp", "-----scrollY= "+scrollY);
		// if (scrollY == 0 || scrollY == offset) {
		// 	return true;
		// }
		// return false;
		
		return isTop() || isBottom();
	}
	
	public interface OnPullListener {
		void onDownPull();
		
		void onUpPull();
		
		void onPullOffset(int offset);
	}
	
	private OnPullListener mOnPullListener;
	
	public void setOnPullListener(OnPullListener listener) {
		mOnPullListener = listener;
	}
	
	private float dp2px(float value) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
	}
}