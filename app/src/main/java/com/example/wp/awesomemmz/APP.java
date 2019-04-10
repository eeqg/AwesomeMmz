package com.example.wp.awesomemmz;

import android.content.Context;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.example.wp.awesomemmz.common.BoxingGlideLoader;
import com.example.wp.awesomemmz.common.BoxingUcrop;
import com.example.wp.awesomemmz.common.PictureLayoutImageLoader;
import com.example.wp.resource.base.BaseApp;
import com.example.wp.resource.widget.picture_layout.PictureLayout;

/**
 * Created by wp on 2019/4/9.
 */
public class APP extends BaseApp {
	
	@Override
	public void onCreate() {
		super.onCreate();
		initPictureLayout();
		initBoxing();
	}
	
	private void initPictureLayout() {
		PictureLayout.setImageLoader(new PictureLayoutImageLoader());
	}
	
	private void initBoxing() {
		IBoxingMediaLoader loader = new BoxingGlideLoader();
		BoxingMediaLoader.getInstance().init(loader);
		BoxingCrop.getInstance().init(new BoxingUcrop());
	}
	
	@Override
	public void logout(Context context) {
	
	}
	
	@Override
	public void cleanTop(Context context) {
	
	}
	
	@Override
	public void requestLogin(Context context, int requestCode) {
	
	}
	
	@Override
	public void viewGoodsInfo(Context context, String goodsId) {
	
	}
}
