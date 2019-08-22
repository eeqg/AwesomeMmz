package com.example.wp.awesomemmz.skill;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;

import com.example.wp.awesomemmz.APP;
import com.example.wp.awesomemmz.R;
import com.example.wp.resource.common.RxBus;

import io.reactivex.functions.Consumer;

public class RxBusActivity extends AppCompatActivity implements RxBusFragment.OnFragmentInteractionListener {
	
	@SuppressLint("CheckResult")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setEnterTransition(new Slide().setDuration(400));
			getWindow().setExitTransition(new Slide().setDuration(400));
		}
		
		setContentView(R.layout.activity_rx_bus);
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.flContent, RxBusFragment.newInstance("", "")).commitAllowingStateLoss();
		
		findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MsgEvent msgEvent = new MsgEvent(1, "100");
				RxBus.getInstance().post(msgEvent);
			}
		});
		findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MsgEvent msgEvent = new MsgEvent(2, "200");
				RxBus.getInstance().post(msgEvent);
			}
		});
		
		RxBus.getInstance().toObservable(this, MsgEvent.class)
				.subscribe(new Consumer<MsgEvent>() {
					@Override
					public void accept(MsgEvent msgEvent) throws Exception {
						// APP.toast("-----feedback msg: " + msgEvent.getValue());
					}
				});
	}
	
	@Override
	public void onFragmentInteraction(Uri uri) {
	
	}
}
