package com.example.wp.awesomemmz.frame;

import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.utils.LogUtils;

public class LifecycleActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lifecycle);
		
		//AppCompatActivity和Fragment都实现了LifecycleOwner接口（Support Library 26.1.0之后的版本），所以可以直接拿来使用.
		//普通Activity类并没有实现LifecycleOwner接口, 要自己实现
		getLifecycle().addObserver(new TestLifeCycle());
		
		Chronometer chronometer = findViewById(R.id.chronometer);
		// chronometer.setFormat("elapsed : %s");
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				LogUtils.d("-----" + (SystemClock.elapsedRealtime() - chronometer.getBase()));
			}
		});
		chronometer.start();
	}
}
