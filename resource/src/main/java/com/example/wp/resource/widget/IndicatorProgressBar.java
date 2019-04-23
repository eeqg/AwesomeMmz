package com.example.wp.resource.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wp.resource.R;

/**
 * Created by wp on 2018/9/12.
 */
public class IndicatorProgressBar extends FrameLayout {
	private ProgressBar progressView;
	private TextView tvValue;
	private TextView tvTitle, tvMaxProgress;
	private String progressValue;
	private int progress;
	
	public IndicatorProgressBar(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		
		initView();
	}
	
	private void initView() {
		View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_indicator_progress, this);
		tvValue = (TextView) rootView.findViewById(R.id.tvValue);
		progressView = (ProgressBar) rootView.findViewById(R.id.progress);
		tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
		tvMaxProgress = (TextView) rootView.findViewById(R.id.tvMaxProgress);
		
		tvValue.setText("0");
		progressView.setProgress(0);
	}
	
	public IndicatorProgressBar setMaxProgress(int maxProgress) {
		progressView.setMax(maxProgress);
		tvMaxProgress.setText(formatFloatNum(String.valueOf(maxProgress)));
		return this;
	}
	
	public IndicatorProgressBar setMaxProgress(String maxProgress) {
		float v = Float.parseFloat(maxProgress);
		return this.setMaxProgress((int) v);
	}
	
	public IndicatorProgressBar setTitle(String title) {
		tvTitle.setText(title);
		return this;
	}
	
	public void setProgress(String progress) {
		this.progressValue = progress;
		float v = Float.parseFloat(progress);
		setProgress((int) v);
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
		progressView.post(new Runnable() {
			@Override
			public void run() {
				locateIndicator(true);
			}
		});
	}
	
	private void locateIndicator(boolean smooth) {
		if (smooth) {
			startAnimation();
		} else {
			updateView(progress);
		}
	}
	
	private void startAnimation() {
		ValueAnimator animator = ValueAnimator.ofInt(0, progress)
				.setDuration(2000);
		animator.setInterpolator(new AccelerateInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int animatedValue = (int) animation.getAnimatedValue();
				updateView(animatedValue);
			}
		});
		animator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
			
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			
			}
		});
		animator.start();
	}
	
	private void updateView(int curProgress) {
		int max = progressView.getMax();
		float margePer = curProgress * 1F / max;
		if (margePer > 1) {
			margePer = 1F;
		}
		int left = progressView.getLeft();
		int valueMeasuredWidth = tvValue.getMeasuredWidth();
		int leftMarge = (int) (margePer * progressView.getMeasuredWidth() + left - valueMeasuredWidth / 2);
		if (leftMarge < 0) {
			leftMarge = 0;
		}
		
		progressView.setProgress(curProgress);
		LinearLayout.LayoutParams valueLayoutParams = (LinearLayout.LayoutParams) tvValue.getLayoutParams();
		valueLayoutParams.leftMargin = leftMarge;
		tvValue.setLayoutParams(valueLayoutParams);
		tvValue.setText(String.valueOf(curProgress));
	}
	
	public static String formatFloatNum(String numStr) {
		if (numStr != null && numStr.indexOf(".") > 0) {
			numStr = numStr.replaceAll("0+?$", "");//去掉后面无用的零
			numStr = numStr.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
		}
		return numStr;
	}
}
