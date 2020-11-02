package com.example.wp.awesomemmz.skill.custom;

import android.os.Bundle;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;
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
    }

    public void onB1(View view) {
        LaunchUtil.launchActivity(this, BezierTestActivity.class);
    }

    public void onB2(View view) {
        LaunchUtil.launchActivity(this, BezierTestActivity2.class);
    }
}
