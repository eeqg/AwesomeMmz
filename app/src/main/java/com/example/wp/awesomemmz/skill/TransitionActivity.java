package com.example.wp.awesomemmz.skill;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.widget.Toast;

import com.example.wp.awesomemmz.APP;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.image.ImageActivity;
import com.example.wp.resource.utils.LogUtils;

public class TransitionActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transition);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			getWindow().setReenterTransition(new Fade());
//		}
	}
	
	public void oldClick(View view) {
		startActivity(new Intent(this, ImageActivity.class));
		overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void explodeClick(View view) {
		startActivity(new Intent(this, RichTextActivity.class),
				ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void slideClick(View view) {
		startActivity(new Intent(this, RxBusActivity.class),
				ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void fadeClick(View view) {
		startActivity(new Intent(this, BottomSheetBehaviorActivity.class),
				ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void customClick(View view) {

	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void transitionClick(View view) {
		startActivity(new Intent(this, ImageActivity.class),
				ActivityOptions.makeSceneTransitionAnimation(this, view, "myimage").toBundle());
	}

	@Override
	public void onActivityReenter(int resultCode, Intent data) {
		super.onActivityReenter(resultCode, data);
		LogUtils.d("----->>>onActivityReenter");
		APP.toast("reenter..");
	}
}
