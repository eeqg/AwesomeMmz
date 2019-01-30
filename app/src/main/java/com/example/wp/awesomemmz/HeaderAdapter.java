package com.example.wp.awesomemmz;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 头布局的适配器
 */
public class HeaderAdapter extends PagerAdapter {
	
	private final Context context;
	public int[] images = new int[]{
			R.mipmap.ic_test_1, R.mipmap.ic_test_2, R.mipmap.ic_test_3,
			R.mipmap.ic_test_4, R.mipmap.ic_test_5};
	
	public HeaderAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView imageView = new ImageView(context.getApplicationContext());
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageResource(images[position]);
		container.addView(imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context.getApplicationContext(), "第" + position + "页", Toast.LENGTH_SHORT).show();
			}
		});
		return imageView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	
	@Override
	public int getCount() {
		return images.length;
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}