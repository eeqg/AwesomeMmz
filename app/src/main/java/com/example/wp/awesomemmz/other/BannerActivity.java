package com.example.wp.awesomemmz.other;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;

public class BannerActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
		
		setupTitleBar("Banner");
	}
}
