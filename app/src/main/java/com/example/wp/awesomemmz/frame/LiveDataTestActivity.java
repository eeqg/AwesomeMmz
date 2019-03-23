package com.example.wp.awesomemmz.frame;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;

public class LiveDataTestActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_data_test);
		
		TestViewModel testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
		
		final TextView tvCount = findViewById(R.id.tvCount);
		
		testViewModel.getCountLiveData().observe(this, new Observer<Long>() {
			@Override
			public void onChanged(@Nullable Long aLong) {
				Log.d("test", "-----" + aLong);
				tvCount.setText("" + aLong);
			}
		});
		
		testViewModel.startCount();
	}
}
