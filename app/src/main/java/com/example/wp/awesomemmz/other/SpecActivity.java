package com.example.wp.awesomemmz.other;

import android.os.Bundle;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.index.ProductActivity;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.LaunchUtil;

public class SpecActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spec);
		
		setupTitleBar("SpecView")
				.setTextAction(R.string.edit, R.color.colorAccent, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						promptMessage("action");
					}
				});
		
		findViewById(R.id.btnSpecRecycler)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						LaunchUtil.launchActivity(SpecActivity.this, ProductActivity.class);
					}
				});
	}
}
