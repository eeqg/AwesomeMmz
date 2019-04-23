package com.example.wp.awesomemmz.custom;

import android.os.Bundle;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.LaunchUtil;
import com.example.wp.resource.widget.IndicatorProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {
	
	@BindView(R.id.indicatorProgress)
	public IndicatorProgressBar indicatorProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_view);
		ButterKnife.bind(this);
		setupTitleBar("CustomView");
		
		initView();
	}
	
	private void initView() {
		indicatorProgress.setTitle("progress").setMaxProgress(100).setProgress(70);
	}
	
	@OnClick({R.id.btnBezier1, R.id.btnBezier2})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btnBezier1:
				LaunchUtil.launchActivity(this, BezierTestActivity.class);
				break;
			case R.id.btnBezier2:
				LaunchUtil.launchActivity(this, BezierTestActivity2.class);
				break;
		}
	}
}
