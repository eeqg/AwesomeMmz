package com.example.wp.awesomemmz.common;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wp.awesomemmz.R;
import com.example.wp.resource.utils.imageload.ImageLoader;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class GlideImageLoader implements ImageLoader {
	
	private Context mContext;
	
	public GlideImageLoader(Context context) {
		this.mContext = context;
	}
	
	@Override
	public void load(ImageView imageView, String imageUrl) {
		load(imageView, imageUrl, R.mipmap.ic_placeholder);
	}
	
	@Override
	public void load(ImageView imageView, String imageUrl, int defaultImage) {
		RequestOptions options = new RequestOptions()
				.placeholder(defaultImage)
				.error(defaultImage);
		loadReal(imageView, imageUrl, options);
	}
	
	public void loadBlur(ImageView imageView, String imageUrl) {
		loadBlur(imageView, imageUrl, 25, 1);
	}
	
	@Override
	public void loadBlur(ImageView imageView, String imageUrl, int radius, int sampling) {
		RequestOptions requestOptions = bitmapTransform(new BlurTransformation(radius, sampling))
				.placeholder(R.mipmap.ic_placeholder)
				.error(R.mipmap.ic_placeholder);
		loadReal(imageView, imageUrl, requestOptions);
	}
	
	@Override
	public void loadCircle(ImageView imageView, String imageUrl) {
		RequestOptions requestOptions = new RequestOptions()
				.placeholder(R.mipmap.ic_placeholder)
				.error(R.mipmap.ic_placeholder)
				.transform(new CircleCrop());
		loadReal(imageView, imageUrl, requestOptions);
	}
	
	@Override
	public void loadRound(ImageView imageView, String imageUrl, int radius) {
		RequestOptions options = bitmapTransform(new RoundedCorners(radius))
				.placeholder(R.mipmap.ic_placeholder)
				.error(R.mipmap.ic_placeholder);
		loadReal(imageView, imageUrl, options);
	}
	
	public void loadTopRounded(ImageView imageView, String imageUrl, int radius) {
		loadRounded(imageView, imageUrl, radius, 0, RoundedCornersTransformation.CornerType.TOP);
	}
	
	public void loadBottomRounded(ImageView imageView, String imageUrl, int radius) {
		loadRounded(imageView, imageUrl, radius, 0, RoundedCornersTransformation.CornerType.BOTTOM);
	}
	
	public void loadRounded(ImageView imageView, String imageUrl, int radius, int margin,
	                        RoundedCornersTransformation.CornerType cornerType) {
		RequestOptions options = bitmapTransform(new RoundedCornersTransformation(radius, margin, cornerType))
				.placeholder(R.mipmap.ic_placeholder)
				.error(R.mipmap.ic_placeholder);
		loadReal(imageView, imageUrl, options);
	}
	
	@Override
	public void load(ImageView imageView, String imageUrl, Object transformation) {
	
	}
	
	private void loadReal(ImageView imageView, String imageUrl, RequestOptions options) {
		Glide.with(mContext)
				.load(imageUrl)
				.apply(options)
				.into(imageView);
	}
}
