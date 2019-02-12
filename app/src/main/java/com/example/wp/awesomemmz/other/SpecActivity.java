package com.example.wp.awesomemmz.other;

import android.os.Bundle;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;

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
	}
}
