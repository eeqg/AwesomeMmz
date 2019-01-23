package com.example.wp.resource.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by wp on 2019/1/23.
 */
public class BaseActivity extends AppCompatActivity {
	
	protected Activity mActivity;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.mActivity = this;
	}
	
	protected void promptMessage(int resId) {
		promptMessage(getString(resId));
	}
	
	protected void promptMessage(String msg) {
		Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
