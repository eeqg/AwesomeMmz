package com.example.wp.resource.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.wp.resource.R;

/**
 * Created by wp on 2019/1/23.
 */
public class BaseActivity extends AppCompatActivity {
	
	protected Activity mActivity;
	
	protected TitleBar titleBar;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.mActivity = this;
	}
	
	protected TitleBar setupTitleBar(int resId) {
		return setupTitleBar(getResources().getString(resId));
	}
	
	protected TitleBar setupTitleBar(String title) {
		titleBar = findViewById(R.id.titleBar);
		if (titleBar != null) {
			titleBar.setBack(this)
					.setTitle(title)
					.showDivider(true);
		}
		return titleBar;
	}
	
	protected void promptMessage(int resId) {
		promptMessage(getString(resId));
	}
	
	protected void promptMessage(String msg) {
		Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
