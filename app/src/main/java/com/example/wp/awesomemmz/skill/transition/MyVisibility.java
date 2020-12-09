package com.example.wp.awesomemmz.skill.transition;

import android.animation.Animator;
import android.graphics.Rect;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.wp.resource.utils.LogUtils;

/**
 * Created by wp on 2020/12/8.
 */

public class MyVisibility extends Visibility {
    private static final String TOP = "top";
    private static final String HEIGHT = "height";

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view == null) return;
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

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        LogUtils.d("-----onAppear()--");
        return super.onAppear(sceneRoot, view, startValues, endValues);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        LogUtils.d("-----onDisappear()--");
        return super.onDisappear(sceneRoot, view, startValues, endValues);
    }
}
