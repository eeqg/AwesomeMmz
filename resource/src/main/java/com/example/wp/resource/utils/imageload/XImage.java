package com.example.wp.resource.utils.imageload;


import android.widget.ImageView;

public class XImage implements ImageLoader {
	
	private static ImageLoader imageLoader;
	private static XImage xImage;
	
	public static void init(ImageLoader loader) {
		imageLoader = loader;
	}
	
	public static XImage getInstance() {
		if (imageLoader == null) {
			throw new NullPointerException("Call XFrame.initXImageLoader(ImageLoader loader) within your Application onCreate() method." +
					"Or extends XApplication");
		}
		if (xImage == null) {
			xImage = new XImage();
		}
		return xImage;
	}
	
	@Override
	public void load(ImageView imageView, String imageUrl) {
		imageLoader.load(imageView, imageUrl);
	}
	
	@Override
	public void load(ImageView imageView, String imageUrl, int defaultImage) {
		imageLoader.load(imageView, imageUrl, defaultImage);
	}
	
	@Override
	public void loadBlur(ImageView imageView, String imageUrl, int radius, int sampling) {
		imageLoader.loadBlur(imageView, imageUrl, radius, sampling);
	}
	
	@Override
	public void loadCircle(ImageView imageView, String imageUrl) {
		imageLoader.loadCircle(imageView, imageUrl);
	}
	
	@Override
	public void loadRound(ImageView imageView, String imageUrl, int radius) {
		imageLoader.loadRound(imageView, imageUrl, radius);
	}
	
	@Override
	public void load(ImageView imageView, String imageUrl, Object transformation) {
	
	}
}
