package com.example.wp.awesomemmz.custom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.widget.bezier.BezierView2;

public class BezierTestActivity2 extends AppCompatActivity {

    private BezierView2 bezierView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier2);

        int color;
        try {
            color = Color.parseColor("1234567999999");
        } catch (Exception e) {
            color = 0x30000000;
        }
        bezierView2 = findViewById(R.id.bezier);
        bezierView2.setBackgroundColor(color);

        bezierView2.setPointsNum(5)
                .setRadius(35)
                .setStrokeColor(Color.parseColor("#9022ff55"));
    }

    public void onLast(View view) {
        bezierView2.bounceLast();
    }

    public void onSet(View view) {
        bezierView2.bounceTo(3);
    }

    public void onNext(View view) {
        bezierView2.bounceNext();
    }
}
