package com.example.wp.awesomemmz.skill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wp.awesomemmz.APP;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.image.ImageActivity;
import com.example.wp.awesomemmz.skill.aspect.DoubleClick;
import com.example.wp.resource.utils.LaunchUtil;
import com.example.wp.resource.utils.LogUtils;

public class AspectTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspect_test);

        findViewById(R.id.view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("-----viewClick1");
                LaunchUtil.launchActivity(getBaseContext(), ImageActivity.class);
            }
        });

        findViewById(R.id.view2).setOnClickListener(new View.OnClickListener() {
            @DoubleClick
            @Override
            public void onClick(View v) {
                LogUtils.d("-----viewClick2");
            }
        });

        findViewById(R.id.view3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 2 / 0;
            }
        });

        getHeight();
    }

    private int getHeight(){
        return APP.SCREEN_HEIGHT;
    }
}
