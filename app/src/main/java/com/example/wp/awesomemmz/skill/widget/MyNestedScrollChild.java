package com.example.wp.awesomemmz.skill.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.utils.LogUtils;

/**
 * 具体使用操作流程
 * 在move事件处理时,先通过dispatchNestedPreScroll将整个的滑动距离dx,dy传递给父View,
 * 然后父View选择性处理一部分距离,将处理了的距离储存在consumed数组中,其中consumed[0]为x轴处理距离,consumed[1]为y轴处理距离.
 * 然后子View根据自己的需要处理剩余的距离.
 * 如果子View未能将剩余距离消耗掉,通过dispatchNestedScroll将剩余的滑动通过参数dxUnconsumed,dyUnconsumed交给父View处理.
 * 一般来说dispatchNestedPreScroll和dispatchNestedScroll只有一个会得到实际上的使用.
 */
public class MyNestedScrollChild extends LinearLayout implements NestedScrollingChild2 {
    private NestedScrollingChildHelper mNestedScrollingChildHelper;
    private int[] offset = new int[2];
    private int[] consumed = new int[2];
    private TextView scrollText;
    private int showHeight;
    private int lastY;
    private boolean scrollTop = false;

    public MyNestedScrollChild(Context context) {
        super(context);
    }

    public MyNestedScrollChild(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(context.getResources().getColor(R.color.colorTextHint));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        scrollText = (TextView) getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        showHeight = getMeasuredHeight();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean reachTop() {
        return scrollTop;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) (event.getRawY());
                int dy = y - lastY;
                lastY = y;
                if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
                        && dispatchNestedPreScroll(0, dy, consumed, offset)) {
                    LogUtils.d("----consumed = "+ consumed[1], "---offsetWindow = "+ offset[1]);
                    //int remain = dy - offset[1];
                    int remain = dy - consumed[1];
                    if (remain != 0) {
                        scrollBy(0, -remain);
                    }
                } else {
                    scrollBy(0, -dy);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                stopNestedScroll();
                break;
        }
        return true;
    }

    //限制滚动范围
    @Override
    public void scrollTo(int x, int y) {
        int maxY = getMeasuredHeight() - showHeight;
        if (y > maxY) {
            y = maxY;
            scrollTop = false;
        } else if (y < 0) {
            y = 0;
            scrollTop = true;
        } else {
            scrollTop = false;
        }
        super.scrollTo(x, y);
    }

    public NestedScrollingChildHelper getNestedScrollingChildHelper() {
        if (mNestedScrollingChildHelper == null) {
            mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
            mNestedScrollingChildHelper.setNestedScrollingEnabled(true);
        }
        return mNestedScrollingChildHelper;
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return getNestedScrollingChildHelper().startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        getNestedScrollingChildHelper().stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return getNestedScrollingChildHelper().hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return getNestedScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    /**
     * 在子View的onInterceptTouchEvent或者onTouch中，调用该方法通知父View滑动的距离
     * @param dx  x轴上滑动的距离
     * @param dy  y轴上滑动的距离
     * @param consumed 父view消费掉的scroll长度
     * @param offsetInWindow   子View的窗体偏移量
     * @return 支持的嵌套的父View 是否处理了 滑动事件
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return getNestedScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getNestedScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getNestedScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getNestedScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getNestedScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getNestedScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                                        @Nullable int[] offsetInWindow) {
        return getNestedScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        return getNestedScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getNestedScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getNestedScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

}