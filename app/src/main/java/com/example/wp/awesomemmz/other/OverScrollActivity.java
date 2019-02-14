package com.example.wp.awesomemmz.other;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.GlideImageLoader2;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.ColorUtil;
import com.example.wp.resource.widget.banner.Banner;

import java.util.Arrays;
import java.util.List;

public class OverScrollActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_over_scroll);
		
		setupTitleBar("Over Scroll View");
		
		TextView idTest = findViewById(R.id.idTest);
		idTest.setTextColor(ColorUtil.generateBeautifulColor());
		// observeBanner();
	}
	
	private void observeBanner() {
		Banner banner = findViewById(R.id.banner);
		
		String[] urls = getResources().getStringArray(R.array.test_num_url);
		List<String> images = Arrays.asList(urls);
		banner.setImages(images).setImageLoader(new GlideImageLoader2()).start();
	}
}
