package com.example.wp.awesomemmz.index;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.index.bean.ClassInfoBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.Locale;

/**
 * Created by wp on 2019/1/30.
 */
public class IndexAdapter extends RecyclerArrayAdapter<ClassInfoBean> {
	public IndexAdapter(Context context) {
		super(context);
	}
	
	@Override
	public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
		return new ItemViewHolder(parent);
	}
	
	private class ItemViewHolder extends BaseViewHolder<ClassInfoBean> {
		
		private TextView tvIndex, tvTitle;
		
		public ItemViewHolder(ViewGroup parent) {
			super(parent, R.layout.item_index_list);
			tvIndex = $(R.id.tvIndex);
			tvTitle = $(R.id.tvTitle);
		}
		
		@Override
		public void setData(ClassInfoBean data) {
			tvIndex.setText(String.format(Locale.CHINA, "%02d", getDataPosition() + 1));
			tvTitle.setText(getItem(getDataPosition()).title);
		}
	}
	
}
