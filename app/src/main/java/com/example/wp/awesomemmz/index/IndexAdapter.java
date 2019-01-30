package com.example.wp.awesomemmz.index;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by wp on 2019/1/30.
 */
public class IndexAdapter extends RecyclerArrayAdapter<String> {
	public IndexAdapter(Context context) {
		super(context);
	}
	
	@Override
	public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
		return new ItemViewHolder(parent);
	}
	
	private class ItemViewHolder extends BaseViewHolder<String> {
		
		private TextView tvIndex, tvTitle;
		
		public ItemViewHolder(ViewGroup parent) {
			super(parent, R.layout.item_index_list);
			tvIndex = $(R.id.tvIndex);
			tvTitle = $(R.id.tvTitle);
		}
		
		@Override
		public void setData(String data) {
			tvIndex.setText(String.valueOf(getDataPosition() + 1));
			tvTitle.setText(getItem(getDataPosition()));
		}
	}
	
}
