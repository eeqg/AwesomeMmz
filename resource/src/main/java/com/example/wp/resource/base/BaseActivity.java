package com.example.wp.resource.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
	
	protected void setTranslucentStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
