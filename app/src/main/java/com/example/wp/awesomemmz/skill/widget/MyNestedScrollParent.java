package com.example.wp.awesomemmz.skill.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.utils.LogUtils;

public class MyNestedScrollParent extends LinearLayout implements NestedScrollingParent2 {
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private MyNestedScrollChild scrollChildView;
    private View headerLayout;
    private ImageView foodIV;
    private TextView titleTV;
    private int imageHeight;
    private int titleHeight;
    private int imageMargin;
    private int scrollY = 0;

    public MyNestedScrollParent(Context context) {
        this(context, null);
    }

    public MyNestedScrollParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerLayout = getChildAt(0);
        scrollChildView = (MyNestedScrollChild) getChildAt(1);
        foodIV = headerLayout.findViewById(R.id.foodIV);
        titleTV = headerLayout.findViewById(R.id.titleTV);
        foodIV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageHeight = foodIV.getHeight();
            }
        });
        titleTV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                titleHeight = titleTV.getHeight();
            }
        });
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        if (target instanceof MyNestedScrollChild) {
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mNestedScrollingParentHelper.onStopNestedScroll(target, type);
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        LogUtils.d("----->>>target: " + target);
        LogUtils.d("-----dy = " + dy);
        int maxTranslateY = imageHeight;
        LogUtils.d("-----imageHeight = " + imageHeight);

        boolean canScroll = false;
        if (Math.abs(scrollY) < maxTranslateY) {
            canScroll = true;
        } else {
            LogUtils.d("-----canChildScrollUp : " + scrollChildView.reachTop());
            if (scrollChildView.reachTop() && dy > 0) {
                canScroll = true;
            }
        }

        LogUtils.d("-----canScroll: " + canScroll);
        if (canScroll) {
            consumed[1] = dy;

            scrollY = (int) (headerLayout.getTranslationY() + dy);
            if (scrollY > 0) {
                scrollY = 0;
            } else if (Math.abs(scrollY) > maxTranslateY) {
                scrollY = -maxTranslateY;
            }
            LogUtils.d("-----scrollY = " + scrollY);
            headerLayout.setTranslationY(scrollY);
            scrollChildView.setTranslationY(scrollY);
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return false;
    }

}