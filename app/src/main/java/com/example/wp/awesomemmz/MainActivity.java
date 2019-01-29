package com.example.wp.awesomemmz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.base.TitleBar;

public class MainActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TitleBar titleBar = findViewById(R.id.titleBar);
		titleBar.setBack(this)
				.setTitle(R.string.index)
				.setTextAction(R.string.edit, R.color.colorAccent, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						promptMessage("action");
					}
				})
				.showDivider();
	}
}
