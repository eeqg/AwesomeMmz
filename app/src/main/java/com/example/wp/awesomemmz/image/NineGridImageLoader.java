package com.example.wp.awesomemmz.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.wp.awesomemmz.common.GlideImageLoader;
import com.example.wp.resource.widget.ninegrid.NineGridView;

public class NineGridImageLoader implements NineGridView.ImageLoader {
	
	@Override
	public void onDisplayImage(Context context, ImageView imageView, String url) {
		GlideImageLoader.getInstance().load(imageView, url);
	}
	
	@Override
	public Bitmap getCacheImage(String url) {
		return null;
	}
}
