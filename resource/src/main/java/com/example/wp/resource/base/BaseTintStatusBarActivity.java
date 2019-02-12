package com.example.wp.resource.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.example.wp.resource.utils.SystemBarTintManager;

/**
 * Created by wp on 2019/2/12.
 */
public class BaseTintStatusBarActivity extends BaseActivity {
	
	protected SystemBarTintManager tintManager;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//设置上方状态栏透明
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		} else {
			tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(android.R.color.transparent);
		}
		
	}
	
	/**
	 * 根据百分比改变颜色透明度
	 */
	protected int changeAlpha(int color, float fraction) {
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		int alpha = (int) (Color.alpha(color) * fraction);
		return Color.argb(alpha, red, green, blue);
	}
	
	@TargetApi(19)
	protected void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}