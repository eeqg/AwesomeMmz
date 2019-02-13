package com.example.wp.resource.widget.scroll;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

public class SwitchPageView extends ViewGroup {
	/** 无效操作点 */
	private static final int INVALID_POINTER = -1;
	/** 阻力比例 */
	private static final float RESISTANCE_RATIO = 0.7f;
	
	private static final int SIZE = 300;
	
	private int mCurrentIndex = 0;
	private int mAdvanceIndex = -1;
	
	private int mTouchSlop;
	private int mActivePointerId = INVALID_POINTER;
	private int mLastMotionY;
	private boolean mIsBeingDragged;
	
	private int mCurrentPosition;
	private Interpolator mInterpolator;
	
	private SmoothScroller mSmoothScroller;
	
	private OnSwitchPageListener mOnSwitchPageListener;
	
	public SwitchPageView(Context context) {
		this(context, null, 0);
	}
	
	public SwitchPageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public SwitchPageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		
		mInterpolator = new LinearInterpolator() {
			@Override
			public float getInterpolation(float input) {
				return input * 0.8f;
			}
		};
		
		mSmoothScroller = new SmoothScroller();
	}
	
	public void setOnSwitchPageListener(OnSwitchPageListener listener) {
		mOnSwitchPageListener = listener;
	}
	
	/**
	 * 显示第一页
	 */
	public void showPrimaryPage() {
		if (mCurrentIndex == getChildCount() - 1) {
			mSmoothScroller.smoothScroll(mCurrentPosition, mCurrentIndex - 1);
		}
	}
	
	/**
	 * 显示第二页
	 */
	public void showSecondaryPage() {
		if (mCurrentIndex == 0) {
			mSmoothScroller.smoothScroll(mCurrentPosition, mCurrentIndex + 1);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (!mSmoothScroller.isFinished()) {
			return true;
		}
		
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				mActivePointerId = event.getPointerId(0);
				mLastMotionY = getMotionEventY(event, mActivePointerId);
				break;
			case MotionEvent.ACTION_MOVE:
				final int activePointerIndex = event.findPointerIndex(mActivePointerId);
				if (activePointerIndex == -1) {
					break;
				}
				
				final int y = getMotionEventY(event, activePointerIndex);
				int deltaY = y - mLastMotionY;
				
				if (!mIsBeingDragged && Math.abs(deltaY) > mTouchSlop) {
					if (deltaY > 0) {// 上滑
						if (!canChildScrollUp()) {
							mIsBeingDragged = true;
							mAdvanceIndex = mCurrentIndex - 1;
							mLastMotionY = y;
						}
					} else {
						if (!canChildScrollDown()) {
							mIsBeingDragged = true;
							mAdvanceIndex = mCurrentIndex + 1;
							mLastMotionY = y;
						}
					}
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mIsBeingDragged = false;
				mActivePointerId = INVALID_POINTER;
				break;
		}
		
		return mIsBeingDragged;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!mSmoothScroller.isFinished()) {
			return true;
		}
		
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				mActivePointerId = event.getPointerId(0);
				mLastMotionY = getMotionEventY(event, mActivePointerId);
				break;
			case MotionEvent.ACTION_MOVE:
				final int activePointerIndex = event.findPointerIndex(mActivePointerId);
				if (activePointerIndex == -1) {
					break;
				}
				
				final int y = getMotionEventY(event, activePointerIndex);
				int deltaY = y - mLastMotionY;
				
				if (!mIsBeingDragged && Math.abs(deltaY) > mTouchSlop) {
					if (deltaY > 0) {// 上滑
						if (!canChildScrollUp()) {
							mIsBeingDragged = true;
							mAdvanceIndex = mCurrentIndex - 1;
						}
					} else {
						if (!canChildScrollDown()) {
							mIsBeingDragged = true;
							mAdvanceIndex = mCurrentIndex + 1;
						}
					}
				}
				if (mIsBeingDragged) {
					movePosition(deltaY);
					mLastMotionY = y;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mIsBeingDragged = false;
				mActivePointerId = INVALID_POINTER;
				
				int indexPosition = getMeasuredHeight() * mCurrentIndex;
				if (Math.abs(indexPosition + mCurrentPosition) < SIZE) {
					mSmoothScroller.smoothScroll(mCurrentPosition, mCurrentIndex);
				} else {
					mSmoothScroller.smoothScroll(mCurrentPosition, mAdvanceIndex);
				}
				break;
		}
		
		return true;
	}
	
	private int getMotionEventY(MotionEvent event, int activePointerId) {
		final int pointerIndex = event.findPointerIndex(activePointerId);
		if (pointerIndex < 0) {
			return -1;
		}
		return (int) event.getY(pointerIndex);
	}
	
	protected boolean canChildScrollUp() {
		if (mCurrentIndex == 0) {
			return true;
		}
		View target = getChildAt(mCurrentIndex);
		return ViewCompat.canScrollVertically(target, -1);
	}
	
	protected boolean canChildScrollDown() {
		if (mCurrentIndex == getChildCount() - 1) {
			return true;
		}
		View target = getChildAt(mCurrentIndex);
		return ViewCompat.canScrollVertically(target, 1);
	}
	
	private void movePosition(int delta) {
		int startPosition;
		int endPosition;
		if (mCurrentIndex < mAdvanceIndex) {
			startPosition = -mCurrentIndex * getMeasuredHeight();
			endPosition = -mAdvanceIndex * getMeasuredHeight();
		} else {
			startPosition = -mAdvanceIndex * getMeasuredHeight();
			endPosition = -mCurrentIndex * getMeasuredHeight();
		}
		
		float ratio = 1.0f * (-mCurrentIndex * getMeasuredHeight() - mCurrentPosition) / getMeasuredHeight();
		int absDelta = Math.abs(delta);
		absDelta = (int) (absDelta * RESISTANCE_RATIO * (1f - (mInterpolator.getInterpolation(ratio))));
		if (absDelta == 0) {
			absDelta = 1;
		}
		if (delta < 0) {
			delta = -absDelta;
		} else if (delta > 0) {
			delta = absDelta;
		}
		
		int toPosition = mCurrentPosition + delta;
		if (toPosition > startPosition) {
			toPosition = startPosition;
		} else if (toPosition < endPosition) {
			toPosition = endPosition;
		}
		
		offsetPosition(toPosition);
	}
	
	private void offsetPosition(int toPosition) {
		int lastPosition = mCurrentPosition;
		mCurrentPosition = toPosition;
		int offset = toPosition - lastPosition;
		
		int childCount = getChildCount();
		for (int index = 0; index < childCount; index++) {
			View view = getChildAt(index);
			view.offsetTopAndBottom(offset);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		int childTop = 0;
		for (int index = 0; index < childCount; index++) {
			View child = getChildAt(index);
			child.layout(l, childTop + mCurrentPosition, r, childTop + mCurrentPosition + child.getMeasuredHeight());
			childTop += child.getMeasuredHeight();
		}
	}
	
	private class SmoothScroller implements Runnable {
		private Scroller mScroller;
		private boolean isRunning = false;
		
		private int mToIndex;
		
		SmoothScroller() {
			mScroller = new Scroller(getContext());
		}
		
		void smoothScroll(int currentPosition, int toIndex) {
			mToIndex = toIndex;
			int distance = -toIndex * getMeasuredHeight() - currentPosition;
			if (distance == 0) {
				finish();
				return;
			}
			removeCallbacks(this);
			
			if (!mScroller.isFinished()) {
				mScroller.forceFinished(true);
			}
			mScroller.startScroll(0, currentPosition, 0, distance);
			post(this);
			isRunning = true;
		}
		
		void finish() {
			if (isRunning) {
				removeCallbacks(this);
				isRunning = false;
			}
			
			mCurrentIndex = mToIndex;
			if (mOnSwitchPageListener != null) {
				mOnSwitchPageListener.onSwitch(mCurrentIndex);
			}
		}
		
		boolean isFinished() {
			return !isRunning;
		}
		
		@Override
		public void run() {
			boolean isFinish = !mScroller.computeScrollOffset() || mScroller.isFinished();
			int toPosition = mScroller.getCurrY();
			offsetPosition(toPosition);
			
			if (isFinish) {
				finish();
			} else {
				post(this);
			}
		}
	}
	
	public interface OnSwitchPageListener {
		
		void onSwitch(int index);
	}
}
