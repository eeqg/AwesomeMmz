package com.example.wp.awesomemmz.skill;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.wp.awesomemmz.APP;
import com.example.wp.awesomemmz.R;

public class CoordinateActivity extends AppCompatActivity {

    AppBarLayout appBar;
    NestedScrollView scrollView;
    CoordinatorLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);

        appBar = findViewById(R.id.appBar);
        scrollView = findViewById(R.id.scrollView);

        layoutParams = (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
        layoutParams.setMargins(30, 0, 30, 0);
        scrollView.setLayoutParams(layoutParams);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float a = (float) 30 / appBarLayout.getTotalScrollRange();
                int side = (int) Math.rint(a * i + 30);
                layoutParams.setMargins(side, 0, side, 0);
                scrollView.setLayoutParams(layoutParams);
                if (Math.abs(i) > 0) {
                    float alpha = (float) Math.abs(i) / appBarLayout.getTotalScrollRange();
                    appBarLayout.setAlpha(alpha);
                    scrollView.getBackground().mutate().setAlpha(Math.round(alpha * 255));
                } else {
                    appBarLayout.setAlpha(0);
                    scrollView.getBackground().mutate().setAlpha(0);
                }
            }
        });
    }

    public void onClick(View view){
        APP.toast("clicked...");
    }
}
