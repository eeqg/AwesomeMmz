package com.example.wp.awesomemmz;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wp.resource.base.BaseTintStatusBarActivity;
import com.example.wp.resource.utils.ScreenUtils;
import com.example.wp.resource.widget.CircleIndicator;
import com.example.wp.resource.widget.LoopViewPager;
import com.example.wp.resource.widget.PagerSlidingTabStrip;

public class MainActivity extends BaseTintStatusBarActivity {
	
	LoopViewPager loopView;
	
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
		
		observeAppBar();
		
		observeHeadView();
		observeTab();
	}
	
	private void observeHeadView() {
		loopView = findViewById(R.id.loopView);
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
	
	/**
	 * 渐变toolbar背景
	 */
	private void observeAppBar() {
		View viewStatusFix = findViewById(R.id.viewStatusFix);
		viewStatusFix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ScreenUtils.getStatusHeight(this)));
		
		AppBarLayout appBar = findViewById(R.id.appBar);
		final Toolbar toolbar = findViewById(R.id.toolbar);
		appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				//verticalOffset始终为0以下的负数
				float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
				
				int color = changeAlpha(getResources().getColor(R.color.colorTitleBar), percent);
				toolbar.setBackgroundColor(color);
				// StatusBarUtil.setColor(MainActivity.this, color, 0);
			}
		});
	}
}
