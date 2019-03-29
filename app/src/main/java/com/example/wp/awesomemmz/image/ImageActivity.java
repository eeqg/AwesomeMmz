package com.example.wp.awesomemmz.image;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.CustomGlideTransform;
import com.example.wp.awesomemmz.common.GlideImageLoader;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.base.TitleBar;
import com.example.wp.resource.utils.LogUtils;
import com.example.wp.resource.utils.StatusBarUtil;
import com.example.wp.resource.widget.ShadowDrawable;
import com.example.wp.resource.widget.shadow.ShadowDrawableWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {
	
	@BindView(R.id.titleBarRoot)
	LinearLayout titleBarRoot;
	@BindView(R.id.ivBack)
	ImageView ivBack;
	@BindView(R.id.titleBar)
	View titleBar;
	@BindView(R.id.tvTitle)
	TextView tvTitle;
	@BindView(R.id.ivHead)
	ImageView ivHead;
	@BindView(R.id.scrollView)
	NestedScrollView scrollView;
	@BindView(R.id.ivShadow)
	View ivShadow;
	@BindView(R.id.ivShadow3)
	View ivShadow3;
	@BindView(R.id.ivSharp1)
	ImageView ivSharp1;
	@BindView(R.id.ivSharp2)
	ImageView ivSharp2;
	@BindView(R.id.ivSharp3)
	ImageView ivSharp3;
	@BindView(R.id.testView)
	View testView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTranslucentStatus();
		setContentView(R.layout.activity_image);
		ButterKnife.bind(this);
		
		observeBar();
		observeViewShadow();
		observeSharp();
	}
	
	private void observeBar() {
		titleBarRoot.addView(StatusBarUtil.createTranslucentStatusBarView(this, 0), 0);
		titleBar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		ivBack.setImageResource(R.mipmap.ic_back_white);
		tvTitle.setText("image");
		tvTitle.setTextColor(getResources().getColor(R.color.colorWhite));
		
		scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
			@Override
			public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				int height = ivHead.getMeasuredHeight();
				LogUtils.d("-----height = " + height);
				LogUtils.d("-----scrollY = " + scrollY);
				float percent = scrollY * 1.2F / height;
				if (percent > 1) {
					percent = 1;
				}
				
				int color = changeAlpha(getResources().getColor(R.color.colorTitleBar), percent);
				LogUtils.d("-----color = " + color);
				titleBarRoot.setBackgroundColor(color);
			}
		});
	}
	
	private void observeViewShadow() {
		ShadowDrawable.setShadowDrawable(ivShadow, Color.parseColor("#801122FF"), 50,
				Color.parseColor("#88FE5891"), 50, 0, 0);
		
		ShadowDrawableWrapper shadowDrawableWrapper = new ShadowDrawableWrapper(this,
				getResources().getDrawable(R.mipmap.image4), 10, 15, 15);
		ivShadow3.setBackgroundDrawable(shadowDrawableWrapper);
	}
	
	private void observeSharp() {
		GlideImageLoader.getInstance().load(ivSharp1, "http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg");
		GlideImageLoader.getInstance().load(ivSharp2, "http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg",
				new CustomGlideTransform(true, 0, 2, getResources().getColor(R.color.colorPrimary)));
		GlideImageLoader.getInstance().load(ivSharp3, "http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg",
				new CustomGlideTransform(false, 10, 2, getResources().getColor(R.color.colorPrimary)));
	}
	
	protected int changeAlpha(int color, float fraction) {
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		int alpha = (int) (Color.alpha(color) * fraction);
		return Color.argb(alpha, red, green, blue);
	}
}
