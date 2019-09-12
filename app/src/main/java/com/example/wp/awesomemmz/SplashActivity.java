package com.example.wp.awesomemmz;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wp.awesomemmz.skill.font.FontType;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.LaunchUtil;

public class SplashActivity extends BaseActivity {
	
	ImageView ivBg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		setTranslucentStatus();
		
		ivBg = findViewById(R.id.ivBg);
		TextView tvTitle = findViewById(R.id.tvTitle);
		tvTitle.setTypeface(getTypeface(FontType.LOBSTER));
		
		startBgAnimation();
	}
	
	private void startBgAnimation() {
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivBg, "scaleX", 1f, 1.18f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivBg, "scaleY", 1f, 1.18f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(scaleX, scaleY);
		animatorSet.setDuration(2000);
		animatorSet.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				LaunchUtil.launchActivity(mActivity, MainActivity.class);
				finish();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			
			}
		});
		animatorSet.start();
	}
	
	private Typeface getTypeface(FontType fontType) {
		Typeface typeface;
		try {
			typeface = Typeface.createFromAsset(getAssets(), fontType.getPath());
		} catch (Exception e) {
			promptMessage("catch exception.");
			typeface = Typeface.DEFAULT;
		}
		return typeface;
	}
}
