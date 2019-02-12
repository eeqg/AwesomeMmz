package com.example.wp.awesomemmz.other;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wp.awesomemmz.HeaderAdapter;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.GlideImageLoader;
import com.example.wp.awesomemmz.common.GlideImageLoader2;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.widget.CircleIndicator;
import com.example.wp.resource.widget.LoopViewPager;
import com.example.wp.resource.widget.banner.Banner;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class BannerActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
		
		setupTitleBar("Banner");
		
		observeLooperView();
		observeBannerView();
	}
	
	private void observeLooperView() {
		LoopViewPager loopView = findViewById(R.id.loopView);
		loopView.setAdapter(new HeaderAdapter(this));
		CircleIndicator indicatorView = findViewById(R.id.indicatorView);
		indicatorView.setViewPager(loopView);
	}
	
	private void observeBannerView() {
		String[] urls = getResources().getStringArray(R.array.url);
		List<String> images = Arrays.asList(urls);
		
		Banner banner = (Banner) findViewById(R.id.banner);
		banner.setImages(images).setImageLoader(new GlideImageLoader2()).start();
	}
}
