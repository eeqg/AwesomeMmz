package com.example.wp.awesomemmz.skill.font;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.skill.transition.MyTransition;
import com.example.wp.awesomemmz.skill.transition.MyVisibility;
import com.example.wp.resource.base.BaseActivity;

import java.util.List;
import java.util.Map;

public class TextFontActivity extends BaseActivity {

    private View tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_font);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvShow = findViewById(R.id.tvShow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            executeTransition();
        }

        final TextView tvDisplay = findViewById(R.id.tvDisplay);
        tvDisplay.setTypeface(getTypeface(FontType.NORMAL));

        Spinner fontTypeSpinner = findViewById(R.id.spinner);
        final FontType[] fontTypes = FontType.values();
        fontTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fontTypes));
        fontTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvDisplay.setTypeface(getTypeface(fontTypes[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public View getTartView() {
        return tvShow;
    }

    private Typeface getTypeface(FontType fontType) {
        Typeface typeface;
        try {
            typeface = Typeface.createFromAsset(getAssets(), fontType.getPath());
        } catch (Exception e) {
            promptMessage("catch exception.");
            typeface = Typeface.DEFAULT;
        }
        return typeface;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void executeTransition() {
//		postponeEnterTransition();
//		final View decorView = getWindow().getDecorView();
//		getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//			@Override
//			public boolean onPreDraw() {
//				decorView.getViewTreeObserver().removeOnPreDrawListener(this);
//				supportStartPostponedEnterTransition();
//				return true;
//			}
//		});

        MyTransition transition = new MyTransition();
//        Transition transition = new MyVisibility();
        transition.excludeTarget(android.R.id.statusBarBackground, true);
        transition.excludeTarget(android.R.id.navigationBarBackground, true);
        transition.addTarget("message");

        getWindow().setSharedElementEnterTransition(transition);

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
            }
        });
    }
}
