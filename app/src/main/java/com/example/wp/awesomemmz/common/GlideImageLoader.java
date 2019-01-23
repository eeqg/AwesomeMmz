package com.example.wp.awesomemmz.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wp.awesomemmz.R;
import com.example.wp.resource.utils.imageload.ImageLoader;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class GlideImageLoader implements ImageLoader {
	
	private Context mContext;
	
	public GlideImageLoader(Context context) {
		this.mContext = context;
	}
	
	@Override
	public void load(ImageView imageView, Object imageUrl) {
		load(imageView, imageUrl, R.mipmap.ic_placeholder);
	}
	
	@Override
	public void load(ImageView imageView, Object imageUrl, int defaultImage) {
		RequestOptions options = new RequestOptions()
				.placeholder(defaultImage)
				.error(defaultImage);
		Glide.with(mContext).load(imageUrl).apply(options).into(imageView);
	}
	
	@Override
	public void loadBlur(ImageView imageView, Object imageUrl) {
		Glide.with(mContext).load(imageUrl)
				.apply(bitmapTransform(new BlurTransformation(25)))
				.into(imageView);
	}
	
	@Override
	public void loadCircle(ImageView imageView, Object imageUrl) {
		Glide.with(mContext)
				.load(imageUrl)
				.apply(bitmapTransform(new CropCircleTransformation()))
				.into(imageView);
	}
	
	@Override
	public void loadRound(ImageView imageView, Object imageUrl) {
		//设置图片圆角角度
		RoundedCorners roundedCorners = new RoundedCorners(6);
		//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
		RequestOptions options = bitmapTransform(roundedCorners).override(300, 300);
		
		Glide.with(mContext).load(imageUrl).apply(options).into(imageView);
	}
	
	@Override
	public void load(ImageView imageView, Object imageUrl, Transformation<Bitmap> transformation) {
		Glide.with(mContext).load(imageUrl)
				.apply(bitmapTransform(transformation))
				.into(imageView);
	}
	
	private void load(ImageView imageView, String imageUrl, int defaultImage, Transformation<Bitmap> transformation) {
		RequestOptions options = null;
		if (transformation != null) {
			options = bitmapTransform(transformation);
		}
		options.placeholder(defaultImage)
				.error(defaultImage);
		Glide.with(mContext).load(imageUrl).apply(options).into(imageView);
	}
}
