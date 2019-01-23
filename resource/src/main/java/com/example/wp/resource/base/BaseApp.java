package com.example.wp.resource.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class BaseApp extends Application {
	/** 版本号 */
	public static int VERSION_CODE = 1;
	/** 版本名 */
	public static String VERSION_NAME = "1.0.0";
	
	/** 屏幕宽度 */
	public static int SCREEN_WIDTH;
	/** 屏幕高度 */
	public static int SCREEN_HEIGHT;
	
	public static BaseApp INSTANCE;
	
	@Override
	public void onCreate() {
		super.onCreate();
		INSTANCE = this;
		
		initVersionInfo();
		initScreenSize();
	}
	
	/**
	 * 获取当前版本信息
	 */
	private void initVersionInfo() {
		try {
			// 获取内部版本号
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			VERSION_CODE = packageInfo.versionCode;
			VERSION_NAME = packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException ignore) {
		}
	}
	
	/**
	 * 获取屏幕大小
	 */
	private void initScreenSize() {
		final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		final Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);
		boolean isPortrait = displayMetrics.widthPixels < displayMetrics.heightPixels;
		SCREEN_WIDTH = isPortrait ? displayMetrics.widthPixels : displayMetrics.heightPixels;
		SCREEN_HEIGHT = isPortrait ? displayMetrics.heightPixels : displayMetrics.widthPixels;
	}
	
	/**
	 * 退出登录
	 *
	 * @param context 设备上下文环境
	 */
	public abstract void logout(Context context);
	
	/**
	 * 清空登录
	 *
	 * @param context 设备上下文环境
	 */
	public abstract void cleanTop(Context context);
	
	/**
	 * 请求登录
	 *
	 * @param context     设备上下文环境
	 * @param requestCode 请求码
	 */
	public abstract void requestLogin(Context context, int requestCode);
	
	/**
	 * 查看商品信息
	 *
	 * @param context 设备上下文环境
	 * @param goodsId 商品ID
	 */
	public abstract void viewGoodsInfo(Context context, String goodsId);
	
	/**
	 * toast提示
	 *
	 * @param text 提示内容
	 */
	public static void toast(String text) {
		toast(text, Gravity.CENTER);
	}
	
	/**
	 * toast提示
	 *
	 * @param text    提示内容
	 * @param gravity 显示位置
	 */
	public static void toast(String text, int gravity) {
		if (android.text.TextUtils.isEmpty(text)) {
			return;
		}
		Toast toast = Toast.makeText(INSTANCE, text, Toast.LENGTH_SHORT);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}
	
	/**
	 * toast提示
	 *
	 * @param resId 提示内容资源ID
	 */
	public static void toast(int resId) {
		toast(resId, Gravity.CENTER);
	}
	
	/**
	 * toast提示
	 *
	 * @param resId   提示内容资源ID
	 * @param gravity 显示位置
	 */
	public static void toast(int resId, int gravity) {
		Toast toast = Toast.makeText(INSTANCE, resId, Toast.LENGTH_SHORT);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}
}
