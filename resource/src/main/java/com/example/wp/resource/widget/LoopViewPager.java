package com.example.wp.resource.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class LoopViewPager extends ViewPager implements Handler.Callback {
	private static final int SCROLL = 1;
	
	private LoopPagerAdapter mPagerAdapter;
	private OnPageChangeListener loopPageChangeListener;  //内部定义的监听器
	private OnPageChangeListener mOnPageChangeListener;   //外部通过set传进来的
	private ArrayList<OnPageChangeListener> mOnPageChangeListeners;   //外部通过add传进来的
	
	private Handler mHandler = new Handler(this);
	
	public LoopViewPager(Context context) {
		this(context, null);
	}
	
	public LoopViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		loopPageChangeListener = new MyOnPageChangeListener();
		super.addOnPageChangeListener(loopPageChangeListener);
	}
	
	/**
	 * 重置定时器
	 */
	public void resetScroll() {
		mHandler.removeMessages(SCROLL);
		
		if (getAdapter() != null && getAdapter().getCount() > 1) {
			mHandler.sendEmptyMessageDelayed(SCROLL, 5000);
		}
	}
	
	/**
	 * 取消定时器
	 */
	public void cancelScroll() {
		mHandler.removeMessages(SCROLL);
	}
	
	@Override
	public void setAdapter(PagerAdapter adapter) {
		if (mPagerAdapter != null) {
			mPagerAdapter.mPagerAdapter.unregisterDataSetObserver(mObserver);
		}
		
		if (adapter == null) {
			mPagerAdapter = null;
		} else if (adapter instanceof LoopPagerAdapter) {
			mPagerAdapter = (LoopPagerAdapter) adapter;
		} else {
			adapter.registerDataSetObserver(mObserver);
			mPagerAdapter = new LoopPagerAdapter(adapter);
		}
		super.setAdapter(mPagerAdapter);
		
		if (mPagerAdapter != null) {
			int item = mPagerAdapter.getCount() / 2;
			int offset;
			int realCount = mPagerAdapter.getRealCount();
			if (realCount == 0) {
				offset = 0;
			} else {
				offset = item % mPagerAdapter.getRealCount();
			}
			super.setCurrentItem(item - offset);
		}
		resetScroll();
	}
	
	@Override
	public PagerAdapter getAdapter() {
		if (mPagerAdapter == null) {
			return null;
		}
		return mPagerAdapter.mPagerAdapter;
	}
	
	public LoopPagerAdapter getLoopAdapter() {
		return mPagerAdapter;
	}
	
	@Override
	public void setCurrentItem(int item) {
		int currentItem = super.getCurrentItem();
		int currentPosition = mPagerAdapter.getRealPosition(currentItem);
		int offset = item - currentPosition;
		super.setCurrentItem(currentItem + offset);
	}
	
	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		int currentItem = super.getCurrentItem();
		int currentPosition = mPagerAdapter.getRealPosition(currentItem);
		int offset = item - currentPosition;
		super.setCurrentItem(currentItem + offset, smoothScroll);
	}
	
	@Override
	public int getCurrentItem() {
		return mPagerAdapter.getRealPosition(super.getCurrentItem());
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				cancelScroll();
				break;
			case MotionEvent.ACTION_UP:
			default:
				resetScroll();
				break;
		}
		return super.onInterceptTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				cancelScroll();
				break;
			case MotionEvent.ACTION_UP:
			default:
				resetScroll();
				break;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (loopPageChangeListener != null) {
			super.addOnPageChangeListener(loopPageChangeListener);
		}
		resetScroll();
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (loopPageChangeListener != null) {
			super.removeOnPageChangeListener(loopPageChangeListener);
		}
		cancelScroll();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mOnPageChangeListener = listener;
	}
	
	@Override
	public void addOnPageChangeListener(OnPageChangeListener listener) {
		if (mOnPageChangeListeners == null) {
			mOnPageChangeListeners = new ArrayList<>();
		}
		mOnPageChangeListeners.add(listener);
	}
	
	@Override
	public void removeOnPageChangeListener(OnPageChangeListener listener) {
		if (mOnPageChangeListeners != null) {
			mOnPageChangeListeners.remove(listener);
		}
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		setCurrentItem(getCurrentItem() + 1, true);
		resetScroll();
		return true;
	}
	
	private class MyOnPageChangeListener implements OnPageChangeListener {
		
		@Override
		public void onPageSelected(int position) {
			int realPosition = mPagerAdapter == null ? 0 : mPagerAdapter.getRealPosition(position);
			
			//分发事件给外部传进来的监听
			if (mOnPageChangeListener != null) {
				mOnPageChangeListener.onPageSelected(realPosition);
			}
			if (mOnPageChangeListeners != null) {
				for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
					OnPageChangeListener listener = mOnPageChangeListeners.get(i);
					if (listener != null) {
						listener.onPageSelected(realPosition);
					}
				}
			}
		}
		
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			int realPosition = mPagerAdapter == null ? 0 : mPagerAdapter.getRealPosition(position);
			
			//分发事件给外部传进来的监听
			if (mOnPageChangeListener != null) {
				mOnPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
			}
			if (mOnPageChangeListeners != null) {
				for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
					OnPageChangeListener listener = mOnPageChangeListeners.get(i);
					if (listener != null) {
						listener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
					}
				}
			}
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
			//分发事件给外部传进来的监听
			if (mOnPageChangeListener != null) {
				mOnPageChangeListener.onPageScrollStateChanged(state);
			}
			if (mOnPageChangeListeners != null) {
				for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
					OnPageChangeListener listener = mOnPageChangeListeners.get(i);
					if (listener != null) {
						listener.onPageScrollStateChanged(state);
					}
				}
			}
		}
	}
	
	//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  -- - - - - -
	//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  -- - - - - -
	
	public static class LoopPagerAdapter extends PagerAdapter {
		private PagerAdapter mPagerAdapter;
		
		public LoopPagerAdapter(PagerAdapter pagerAdapter) {
			mPagerAdapter = pagerAdapter;
		}
		
		@Override
		public int getCount() {
			int realCount = getRealCount();
			if (realCount <= 1) {
				return realCount;
			} else {
				return Integer.MAX_VALUE;
			}
		}
		
		public int getRealCount() {
			return mPagerAdapter.getCount();
		}
		
		int getRealPosition(int position) {
			int realCount = getRealCount();
			if (realCount == 0) {
				return 0;
			}
			return position % realCount;
		}
		
		@Override
		public int getItemPosition(Object object) {
			return mPagerAdapter.getItemPosition(object);
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return mPagerAdapter.isViewFromObject(view, object);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return mPagerAdapter.instantiateItem(container, getRealPosition(position));
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			mPagerAdapter.destroyItem(container, getRealPosition(position), object);
		}
		
		@Override
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
			mPagerAdapter.setPrimaryItem(container, getRealPosition(position), object);
		}
		
		@Override
		public void startUpdate(ViewGroup container) {
			mPagerAdapter.startUpdate(container);
		}
		
		@Override
		public void finishUpdate(ViewGroup container) {
			mPagerAdapter.finishUpdate(container);
		}
		
		@Override
		public Parcelable saveState() {
			return mPagerAdapter.saveState();
		}
		
		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			mPagerAdapter.restoreState(state, loader);
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mPagerAdapter.getPageTitle(getRealPosition(position));
		}
		
		@Override
		public float getPageWidth(int position) {
			return mPagerAdapter.getPageWidth(getRealPosition(position));
		}
	}
	
	private DataSetObserver mObserver = new DataSetObserver() {
		@Override
		public void onChanged() {
			mPagerAdapter.notifyDataSetChanged();
			resetScroll();
		}
		
		@Override
		public void onInvalidated() {
			mPagerAdapter.notifyDataSetChanged();
			resetScroll();
		}
	};
}
