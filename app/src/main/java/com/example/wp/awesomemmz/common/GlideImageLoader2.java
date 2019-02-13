package com.example.wp.awesomemmz.common;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wp.resource.widget.banner.loader.ImageLoader;

/**
 * Created by wp on 2019/2/12.
 */
public class GlideImageLoader2 extends ImageLoader {
	@Override
	public void displayImage(Context context, Object path, ImageView imageView) {
		new GlideImageLoader(context).load(imageView, (String) path);
	}
}
