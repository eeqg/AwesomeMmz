package com.example.wp.resource.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.Random;

public class ColorUtil {
	/**
	 * 生成漂亮的颜色
	 */
	public static int generateBeautifulColor() {
		Random random = new Random();
		//为了让生成的颜色不至于太黑或者太白，所以对3个颜色的值进行限定
		int red = random.nextInt(150) + 50;//50-200
		int green = random.nextInt(150) + 50;//50-200
		int blue = random.nextInt(150) + 50;//50-200
		return Color.rgb(red, green, blue);//使用r,g,b混合生成一种新的颜色
	}
	
	public static int calculateGradientColor(int startColor, int endColor, float offset) {
		int curRed = Color.red(startColor);
		int curGreen = Color.green(startColor);
		int curBlue = Color.blue(startColor);
		
		int nextRed = Color.red(endColor);
		int nextGreen = Color.green(endColor);
		int nextBlue = Color.blue(endColor);
		
		Log.d("test_wp", String.format("cur: %s, %s, %s", curRed, curGreen, curBlue));
		Log.d("test_wp", String.format("next: %s, %s, %s", nextRed, nextGreen, nextBlue));
		
		int dtRed = (int) (curRed + (nextRed - curRed) * offset);
		int dtGreen = (int) (curGreen + (nextGreen - curGreen) * offset);
		int dtBlue = (int) (curBlue + (nextBlue - curBlue) * offset);
		Log.d("test_wp", String.format("dt: %s, %s, %s", dtRed, dtGreen, dtBlue));
		return Color.rgb(dtRed, dtGreen, dtBlue);
	}
	
	public static void startGradientColorAnimator(final int startColor, final int endColor, View view) {
		final View targetView = view;
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(200);
		valueAnimator.setInterpolator(new DecelerateInterpolator());
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float animatedValue = (float) animation.getAnimatedValue();
				targetView.setBackgroundColor(ColorUtil.calculateGradientColor(startColor, endColor, animatedValue));
			}
		});
		valueAnimator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				targetView.setBackgroundColor(endColor);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			
			}
		});
	}
}
