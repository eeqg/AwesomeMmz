package com.example.wp.awesomemmz;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.base.TitleBar;
import com.example.wp.resource.utils.LogUtils;
import com.example.wp.resource.widget.CircleIndicator;
import com.example.wp.resource.widget.LoopViewPager;
import com.example.wp.resource.widget.PagerSlidingTabStrip;

public class MainActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// TitleBar titleBar = findViewById(R.id.titleBar);
		// titleBar.setBack(this)
		// 		.setTitle(R.string.index)
		// 		.setTextAction(R.string.edit, R.color.colorAccent, new View.OnClickListener() {
		// 			@Override
		// 			public void onClick(View v) {
		// 				promptMessage("action");
		// 			}
		// 		})
		// 		.showDivider();
		
		observeHeadView();
		observeTab();
	}
	
	private void observeHeadView() {
		LoopViewPager loopView = findViewById(R.id.loopView);
		loopView.setAdapter(new HeaderAdapter(this));
		CircleIndicator indicatorView = findViewById(R.id.indicatorView);
		indicatorView.setViewPager(loopView);
	}
	
	private void observeTab() {
		PagerSlidingTabStrip tabs = findViewById(R.id.tabs);
		ViewPager viewPager = findViewById(R.id.viewPager);
		PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		tabs.setViewPager(viewPager);
	}
}
