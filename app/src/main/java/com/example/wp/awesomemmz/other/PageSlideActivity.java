package com.example.wp.awesomemmz.other;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.MainHelper;
import com.example.wp.awesomemmz.other.adapter.ColorItemListAdapter;
import com.example.wp.resource.base.BaseActivity;

public class PageSlideActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_slide);
		
		setupTitleBar("Vertical slide pager");
		
		observeList();
	}
	
	private void observeList() {
		RecyclerView recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		ColorItemListAdapter colorItemListAdapter = new ColorItemListAdapter(this);
		recyclerView.setAdapter(colorItemListAdapter);
		
		colorItemListAdapter.addAll(MainHelper.getInstance().getTestData(12));
		colorItemListAdapter.notifyDataSetChanged();
	}
}
