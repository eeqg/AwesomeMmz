package com.example.wp.awesomemmz.custom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.widget.bezier.BezierView2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BezierTestActivity2 extends AppCompatActivity {
	
	@BindView(R.id.bezier)
	BezierView2 bezierView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bezier2);
		
		ButterKnife.bind(this);
		
		bezierView2.setPointsNum(5)
				.setRadius(35)
				.setStrokeColor(Color.parseColor("#9022ff55"));
	}
	
	@OnClick({R.id.btnLast, R.id.btnNext, R.id.btnSet})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btnLast:
				bezierView2.bounceLast();
				break;
			case R.id.btnNext:
				bezierView2.bounceNext();
				break;
			case R.id.btnSet:
				bezierView2.bounceTo(3);
				break;
		}
	}
}
