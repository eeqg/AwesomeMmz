package com.example.wp.awesomemmz.other;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wp.awesomemmz.HeaderAdapter;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.GlideImageLoader2;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.LogUtils;
import com.example.wp.resource.widget.CircleIndicator;
import com.example.wp.resource.widget.LoopViewPager;
import com.example.wp.resource.widget.banner.Banner;
import com.example.wp.resource.widget.banner.BannerConfig;
import com.example.wp.resource.widget.banner.listener.OnBannerListener;
import com.example.wp.resource.widget.banner.transformer.AccordionTransformer;
import com.example.wp.resource.widget.banner.transformer.BackgroundToForegroundTransformer;
import com.example.wp.resource.widget.banner.transformer.CubeInTransformer;
import com.example.wp.resource.widget.banner.transformer.CubeOutTransformer;
import com.example.wp.resource.widget.banner.transformer.DefaultTransformer;
import com.example.wp.resource.widget.banner.transformer.DepthPageTransformer;
import com.example.wp.resource.widget.banner.transformer.FlipHorizontalTransformer;
import com.example.wp.resource.widget.banner.transformer.FlipVerticalTransformer;
import com.example.wp.resource.widget.banner.transformer.ForegroundToBackgroundTransformer;
import com.example.wp.resource.widget.banner.transformer.RotateDownTransformer;
import com.example.wp.resource.widget.banner.transformer.RotateUpTransformer;
import com.example.wp.resource.widget.banner.transformer.ScaleInOutTransformer;
import com.example.wp.resource.widget.banner.transformer.StackTransformer;
import com.example.wp.resource.widget.banner.transformer.TabletTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomInTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomOutSlideTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomOutTranformer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends BaseActivity implements OnBannerListener {
	
	@BindView(R.id.bannerContainer2)
	View bannerContainer2;
	@BindView(R.id.banner2)
	Banner banner2;
	
	Banner banner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
		ButterKnife.bind(this);
		
		setupTitleBar("Banner");
		
		observeLooperView();
		observeBannerView();
		observerBannerBg();
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
		
		String[] title = getResources().getStringArray(R.array.title);
		List<String> titles = Arrays.asList(title);
		
		banner = findViewById(R.id.banner);
		banner.setImages(images)
				.setBannerTitles(titles) //
				.setImageLoader(new GlideImageLoader2())
				.setOnBannerListener(this) //click
				.start();
		
		// start/stop.
		findViewById(R.id.tvStart).setOnClickListener(new View.OnClickListener() {
			private boolean isRunning = true;
			
			@Override
			public void onClick(View v) {
				if (isRunning) {
					banner.stopAutoPlay();
				} else {
					banner.startAutoPlay();
				}
				isRunning = !isRunning;
			}
		});
		
		//切换动画
		Spinner spinnerStyle = findViewById(R.id.spinnerStyle);
		// spinnerStyle.setAdapter();
		spinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 0:
						banner.setBannerAnimation(DefaultTransformer.class);
						break;
					case 1:
						banner.setBannerAnimation(AccordionTransformer.class);
						break;
					case 2:
						banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
						break;
					case 3:
						banner.setBannerAnimation(ForegroundToBackgroundTransformer.class);
						break;
					case 4:
						banner.setBannerAnimation(CubeInTransformer.class);
						break;
					case 5:
						banner.setBannerAnimation(CubeOutTransformer.class);
						break;
					case 6:
						banner.setBannerAnimation(DepthPageTransformer.class);
						break;
					case 7:
						banner.setBannerAnimation(FlipHorizontalTransformer.class);
						break;
					case 8:
						banner.setBannerAnimation(FlipVerticalTransformer.class);
						break;
					case 9:
						banner.setBannerAnimation(RotateDownTransformer.class);
						break;
					case 10:
						banner.setBannerAnimation(RotateUpTransformer.class);
						break;
					case 11:
						banner.setBannerAnimation(ScaleInOutTransformer.class);
						break;
					case 12:
						banner.setBannerAnimation(StackTransformer.class);
						break;
					case 13:
						banner.setBannerAnimation(TabletTransformer.class);
						break;
					case 14:
						banner.setBannerAnimation(ZoomInTransformer.class);
						break;
					case 15:
						banner.setBannerAnimation(ZoomOutTranformer.class);
						break;
					case 16:
						banner.setBannerAnimation(ZoomOutSlideTransformer.class);
						break;
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			
			}
		});
		
		//指示器显示位置
		((Spinner) findViewById(R.id.spinnerIndicator))
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
							case 0:
								banner.setIndicatorGravity(BannerConfig.LEFT);
								break;
							case 1:
								banner.setIndicatorGravity(BannerConfig.CENTER);
								break;
							case 2:
								banner.setIndicatorGravity(BannerConfig.RIGHT);
								break;
						}
						banner.start();
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					
					}
				});
		
		//指示器显示方式
		((Spinner) findViewById(R.id.spinnerIndicatorStyle))
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
							case 0:
								banner.updateBannerStyle(BannerConfig.NOT_INDICATOR);
								break;
							case 1:
								banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR);
								break;
							case 2:
								banner.updateBannerStyle(BannerConfig.NUM_INDICATOR);
								break;
							case 3:
								banner.updateBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
								break;
							case 4:
								banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
								break;
							case 5:
								banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
								break;
						}
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					
					}
				});
	}
	
	private void observerBannerBg() {
		String[] urls = getResources().getStringArray(R.array.url4);
		List<String> images = Arrays.asList(urls);
		banner2.setImages(images)
				.setImageLoader(new GlideImageLoader2())
				.setOnBannerListener(this) //click
				.start();
		
		final String[] colorsStr = getResources().getStringArray(R.array.colorsStr);
		
		banner2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				Log.d("test_wp", "-------------------");
				Log.d("test_wp", "positionOffset = "+positionOffset);
				int curColor = Color.parseColor(colorsStr[position]);
				Log.d("test_wp", "curColor = "+curColor);
				int curRed = Color.red(curColor);
				int curGreen = Color.green(curColor);
				int curBlue = Color.blue(curColor);
				int nextColor = Color.parseColor(colorsStr[(position + 1 == colorsStr.length ? 0 : position + 1)]);
				Log.d("test_wp", "nextColor = "+nextColor);
				int nextRed = Color.red(nextColor);
				int nextGreen = Color.green(nextColor);
				int nextBlue = Color.blue(nextColor);
				
				Log.d("test_wp", String.format("cur: %s, %s, %s", curRed, curGreen, curBlue));
				Log.d("test_wp", String.format("next: %s, %s, %s", nextRed, nextGreen, nextBlue));
				
				int dtRed = (int) (curRed + (nextRed - curRed) * positionOffset);
				int dtGreen = (int) (curGreen + (nextGreen - curGreen) * positionOffset);
				int dtBlue = (int) (curBlue + (nextBlue - curBlue) * positionOffset);
				Log.d("test_wp", String.format("dt: %s, %s, %s", dtRed, dtGreen, dtBlue));
				bannerContainer2.setBackgroundColor(Color.rgb(dtRed, dtGreen, dtBlue));
			}
			
			@Override
			public void onPageSelected(int position) {
				bannerContainer2.setBackgroundColor(Color.parseColor(colorsStr[position]));
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			
			}
		});
	}
	
	@Override
	public void OnBannerClick(int position) {
		Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
	}
}
