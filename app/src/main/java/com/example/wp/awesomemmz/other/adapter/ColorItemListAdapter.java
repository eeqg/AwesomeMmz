package com.example.wp.awesomemmz.other.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.utils.ColorUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by wp on 2019/2/13.
 */
public class ColorItemListAdapter extends RecyclerArrayAdapter {
	public ColorItemListAdapter(Context context) {
		super(context);
	}
	
	@Override
	public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
		return new ItemViewHolder(parent);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
	
	}
	
	private class ItemViewHolder extends BaseViewHolder {
		private View view;
		
		public ItemViewHolder(ViewGroup parent) {
			super(parent, R.layout.item_color_list);
			
			view = $(R.id.view);
			view.setBackgroundColor(ColorUtil.generateBeautifulColor());
		}
		
		@Override
		public void setData(Object data) {
		
		}
	}
}
