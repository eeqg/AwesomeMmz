package com.example.wp.awesomemmz.skill.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.wp.resource.utils.LogUtils;

public class MyTransition extends Transition {
    private static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String TOP = "top";
    private static final String HEIGHT = "height";

    private long mPositionDuration = 300;
    private long mSizeDuration = 300;

    private TimeInterpolator mPositionInterpolator = new FastOutLinearInInterpolator();
    private TimeInterpolator mSizeInterpolator = new FastOutSlowInInterpolator();

    private View targetView;

    public MyTransition() {
        LogUtils.d("-----MyTransition()");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        LogUtils.d("-----MyTransition()--2");
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view == null) return;

        LogUtils.d("-----captureValues(): visibility: " + view.getVisibility());
        LogUtils.d("-----captureValues(): getParent: " + view.getParent());
        transitionValues.values.put(PROPNAME_VISIBILITY, view.getVisibility());
        transitionValues.values.put(PROPNAME_PARENT, view.getParent());

        int top = 0;

        Rect rect = new Rect();
        view.getHitRect(rect);
        top = rect.top;

//        top = view.getTop();

//        int[] location = new int[2];
//        view.getLocationOnScreen(location);
//        top = location[1];

        transitionValues.values.put(TOP, top);
        transitionValues.values.put(HEIGHT, view.getHeight());

        Log.d("-----captureValues(): ", "start:" + top + ";" + view.getHeight());
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        LogUtils.d("-----captureStartValues()-- ");
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        LogUtils.d("-----captureEndValues()-- ");
        captureValues(transitionValues);
    }

    private boolean fadeIn(TransitionValues startValues, TransitionValues endValues) {
        boolean fadeIn = false;
        int startVisibility = -1;
        ViewGroup startParent = null;
        if (startValues != null && startValues.values.containsKey(PROPNAME_VISIBILITY)) {
            startVisibility = (int) startValues.values.get(PROPNAME_VISIBILITY);
            startParent = (ViewGroup) startValues.values.get(PROPNAME_PARENT);
        }
        int endVisibility = -1;
        ViewGroup endParent = null;
        if (endValues != null && endValues.values.containsKey(PROPNAME_VISIBILITY)) {
            endVisibility = (int) endValues.values.get(PROPNAME_VISIBILITY);
            endParent = (ViewGroup) endValues.values.get(PROPNAME_PARENT);
        }
        if (startValues != null && endValues != null) {
            if (startVisibility != endVisibility) {
                if (startVisibility == View.VISIBLE) {
                    fadeIn = false;
                } else if (endVisibility == View.VISIBLE) {
                    fadeIn = true;
                }
                // no visibilityChange if going between INVISIBLE and GONE
            } else if (startParent != endParent) {
                if (endParent == null) {
                    fadeIn = false;
                } else if (startParent == null) {
                    fadeIn = true;
                }
            }
        } else if (startValues == null && endVisibility == View.VISIBLE) {
            fadeIn = true;
        } else if (endValues == null && startVisibility == View.VISIBLE) {
            fadeIn = false;
        }

        return fadeIn;
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        LogUtils.d("-----createAnimator--this: " + this);
        LogUtils.d("-----createAnimator()--sceneRoot = " + sceneRoot);
        if (startValues == null || endValues == null) {
            return null;
        }

        LogUtils.d("-----createAnimator()--start: view = " + startValues.view);
        LogUtils.d("-----createAnimator()--start: getVisibility = " + startValues.view.getVisibility());
        LogUtils.d("-----createAnimator()--start: getParent = " + startValues.view.getParent());
        LogUtils.d("-----createAnimator()--end: view = " + endValues.view);
        LogUtils.d("-----createAnimator()--end: getVisibility = " + endValues.view.getVisibility());
        LogUtils.d("-----createAnimator()--end: getParent = " + endValues.view.getParent());

        targetView = endValues.view;

//        boolean fadeIn = fadeIn(startValues, endValues);
//        LogUtils.d("-----createAnimator()--fadeIn = " + fadeIn);
//        if (fadeIn) {
//            targetView = endValues.view;
//        } else {
//            targetView = startValues.view;
//        }

        LogUtils.d("-----createAnimator()--targetView = " + targetView);

        final int startTop = (int) startValues.values.get(TOP);
        final int startHeight = (int) startValues.values.get(HEIGHT);
        final int endTop = (int) endValues.values.get(TOP);
        final int endHeight = (int) endValues.values.get(HEIGHT);

        LogUtils.d("-----createAnimator()--# = " + String.format("startTop=%s, endTop=%s", startTop, endTop));

        ViewCompat.setTranslationY(targetView, startTop);
        targetView.getLayoutParams().height = startHeight;
        targetView.requestLayout();

        ValueAnimator positionAnimator = ValueAnimator.ofInt(startTop, endTop);
        if (mPositionDuration > 0) {
            positionAnimator.setDuration(mPositionDuration);
        }
        positionAnimator.setInterpolator(mPositionInterpolator);

        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                ViewCompat.setTranslationY(targetView, current);
            }
        });

        ValueAnimator sizeAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        if (mSizeDuration > 0) {
            sizeAnimator.setDuration(mSizeDuration);
        }
        sizeAnimator.setInterpolator(mSizeInterpolator);

        sizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                targetView.getLayoutParams().height = current;
                targetView.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500).play(sizeAnimator).after(positionAnimator);

        return set;
    }

    public void setPositionDuration(long duration) {
        mPositionDuration = duration;
    }

    public void setSizeDuration(long duration) {
        mSizeDuration = duration;
    }

    public void setPositionInterpolator(TimeInterpolator interpolator) {
        mPositionInterpolator = interpolator;
    }

    public void setSizeInterpolator(TimeInterpolator interpolator) {
        mSizeInterpolator = interpolator;
    }
}