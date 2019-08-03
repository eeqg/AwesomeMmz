package com.example.wp.awesomemmz.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.GlideImageLoader;
import com.example.wp.resource.base.BaseApp;
import com.example.wp.resource.utils.LogUtils;
import com.example.wp.resource.widget.ninegrid.NineGridView;
import com.example.wp.resource.widget.ninegrid.NineGridViewAdapter;

import java.util.List;

/**
 * Created by wp on 2019/8/3.
 */
public class NineGridImageAdapter extends NineGridViewAdapter<ImageInfoBean> {
	public NineGridImageAdapter(Context context, List<ImageInfoBean> imageInfo) {
		super(context, imageInfo);
	}
	
	@Override
	protected View onCreateView(Context context) {
		LogUtils.d("-----onCreateView()--");
		return View.inflate(context, R.layout.item_nine_grid, null);
	}
	
	@Override
	protected void onBindView(Context context, ViewGroup parent, int position) {
		// LogUtils.d("-----onBindView()--" + position);
		ImageView ivPicture = parent.findViewById(R.id.ivPicture);
		ImageInfoBean imageInfoBean = getImageInfo().get(position);
		// LogUtils.d("-----onBindView()--" + imageInfoBean.imgUrl);
		GlideImageLoader.getInstance().load(ivPicture, imageInfoBean.imgUrl);
		
		View maskerView = parent.findViewById(R.id.viewMask);
		maskerView.setVisibility(position == 0 || position == 8 ? View.VISIBLE : View.GONE);
		View tvMoreNum = parent.findViewById(R.id.tvMoreNum);
		tvMoreNum.setVisibility(position == 8 ? View.VISIBLE : View.GONE);
		View ivPlay = parent.findViewById(R.id.ivPlay);
		ivPlay.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
	}
	
	@Override
	protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List imageInfo) {
		BaseApp.toast("" + index);
	}
}
