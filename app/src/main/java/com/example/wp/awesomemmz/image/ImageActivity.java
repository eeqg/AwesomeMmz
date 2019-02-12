package com.example.wp.awesomemmz.image;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;

public class ImageActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		setupTitleBar("Image");
	}
}
