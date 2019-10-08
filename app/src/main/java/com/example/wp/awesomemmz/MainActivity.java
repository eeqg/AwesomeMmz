package com.example.wp.awesomemmz;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wp.awesomemmz.skill.aspect.CheckPermission;
import com.example.wp.awesomemmz.skill.aspect.DebugTrace;
import com.example.wp.awesomemmz.skill.aspect.NeedMonitor;
import com.example.wp.resource.base.BaseTintStatusBarActivity;
import com.example.wp.resource.utils.CommonUtil;
import com.example.wp.resource.utils.LogUtils;
import com.example.wp.resource.utils.ScreenUtils;
import com.example.wp.resource.widget.CircleIndicator;
import com.example.wp.resource.widget.LoopViewPager;
import com.example.wp.resource.widget.PagerSlidingTabStrip;
import com.example.wp.resource.widget.bezier.BezierIndicatorView;

public class MainActivity extends BaseTintStatusBarActivity {

    LoopViewPager loopView;

    @DebugTrace
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.d("-----> onCreate: " + savedInstanceState);
        observeAppBar();

        observeHeadView();
        observeTab();

        boolean enabled = CommonUtil.isNotificationEnabled(this);
        LogUtils.d("-----enabled = " + enabled);
        if (!enabled) {
            CommonUtil.launchNotificationSettings(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.d("-----> onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.d("-----> onRestoreInstanceState");
    }

    @NeedMonitor(value = "observeHeadView")
    private void observeHeadView() {
        loopView = findViewById(R.id.loopView);
        loopView.setAdapter(new HeaderAdapter(this));
        // CircleIndicator indicatorView = findViewById(R.id.indicatorView);
        // indicatorView.setViewPager(loopView);
        BezierIndicatorView indicatorView2 = findViewById(R.id.indicatorView2);
        indicatorView2.attachToViewpager(loopView);
    }

    // @CheckPermission(permission = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION})
    private void observeTab() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // PagerSlidingTabStrip tabs = findViewById(R.id.tabs);
        // tabs.setViewPager(viewPager);
        // tabs.setIndicatorinFollower(true);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 渐变toolbar背景
     */
    private void observeAppBar() {
        View viewStatusFix = findViewById(R.id.viewStatusFix);
        viewStatusFix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtils.getStatusHeight(this)));

        AppBarLayout appBar = findViewById(R.id.appBar);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset始终为0以下的负数
                float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());

                int color = changeAlpha(getResources().getColor(R.color.colorTitleBar), percent);
                toolbar.setBackgroundColor(color);
                // StatusBarUtil.setColor(MainActivity.this, color, 0);
            }
        });
    }
}
