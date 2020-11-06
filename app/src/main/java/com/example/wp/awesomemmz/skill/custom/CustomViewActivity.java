package com.example.wp.awesomemmz.skill.custom;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.DrawableUtils;
import com.example.wp.resource.utils.LaunchUtil;
import com.example.wp.resource.widget.IndicatorProgressBar;

public class CustomViewActivity extends BaseActivity {

    public IndicatorProgressBar indicatorProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        setupTitleBar("CustomView");

        indicatorProgress = findViewById(R.id.indicatorProgress);

        initView();
    }

    private void initView() {
        indicatorProgress.setTitle("progress").setMaxProgress(100).setProgress(70);

        View view1 = findViewById(R.id.view1);
        View view2 = findViewById(R.id.view2);
        View view3 = findViewById(R.id.view3);
        View view4 = findViewById(R.id.view4);
        view1.setBackground(DrawableUtils.INSTANCE.getGradientDrawable(0, Color.DKGRAY, 4, Color.RED, 20, 80, 0));
        view2.setBackground(DrawableUtils.INSTANCE.getGradientDrawable(1, Color.YELLOW, 4, Color.RED, 0, 0, 150));
        view3.setBackground(DrawableUtils.INSTANCE.getGradientDrawable(new int[]{Color.RED, Color.GREEN, Color.BLUE}, 150));
        view4.setBackground(DrawableUtils.INSTANCE.getGradientDrawable(2, Color.DKGRAY, 8, Color.RED, 0, 99, 0));
    }

    public void onB1(View view) {
        LaunchUtil.launchActivity(this, BezierTestActivity.class);
    }

    public void onB2(View view) {
        LaunchUtil.launchActivity(this, BezierTestActivity2.class);
    }
}
