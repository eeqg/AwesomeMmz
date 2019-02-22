package com.example.wp.awesomemmz.custom;

import android.os.Bundle;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.LaunchUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_view);
		ButterKnife.bind(this);
		setupTitleBar("CustomView");
		
		initView();
	}
	
	private void initView() {
	
	}
	
	@OnClick({R.id.btnBezier1, R.id.btnBezier2})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btnBezier1:
				LaunchUtil.launchActivity(this, BezierTestActivity.class);
				break;
			case R.id.btnBezier2:
				
				break;
		}
	}
}
