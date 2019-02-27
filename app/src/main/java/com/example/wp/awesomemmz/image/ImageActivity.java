package com.example.wp.awesomemmz.image;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.widget.ShadowDrawable;
import com.example.wp.resource.widget.shadow.ShadowDrawableWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {
	
	@BindView(R.id.ivShadow)
	View ivShadow;
	@BindView(R.id.ivShadow3)
	View ivShadow3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		ButterKnife.bind(this);
		setupTitleBar("Image");
		
		observeViewShadow();
	}
	
	private void observeViewShadow() {
		ShadowDrawable.setShadowDrawable(ivShadow, Color.parseColor("#801122FF"), 50,
				Color.parseColor("#88FE5891"), 50, 0, 0);
		
		ShadowDrawableWrapper shadowDrawableWrapper = new ShadowDrawableWrapper(this,
				getResources().getDrawable(R.mipmap.image4), 10, 15, 15);
		ivShadow3.setBackgroundDrawable(shadowDrawableWrapper);
	}
}
