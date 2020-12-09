package com.example.wp.awesomemmz.skill;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.wp.awesomemmz.APP;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.image.ImageActivity;
import com.example.wp.awesomemmz.skill.font.TextFontActivity;
import com.example.wp.resource.utils.LogUtils;
import com.transitionseverywhere.ArcMotion;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.List;
import java.util.Map;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            observeTransitionManager();
        }
    }

    boolean visible = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RtlHardcoded")
    private void observeTransitionManager() {
        final ViewGroup sceneRoot0 = findViewById(R.id.sceneRoot0);
        final ViewGroup sceneRoot = findViewById(R.id.sceneRoot);
        final TextView tvAwesome = findViewById(R.id.tvAwesome);
        final ViewGroup sceneRoot2 = findViewById(R.id.sceneRoot2);
        final TextView viewTarget2 = findViewById(R.id.viewTarget2);
        final ViewGroup sceneRoot3 = findViewById(R.id.sceneRoot3);
        final View viewTarget6 = findViewById(R.id.viewTarget6);
        final View view99 = findViewById(R.id.view99);

        findViewById(R.id.btnDefault).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //android.transition.TransitionManager.beginDelayedTransition(sceneRoot0);
                TransitionManager.beginDelayedTransition(sceneRoot);
                visible = !visible;
                tvAwesome.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
        findViewById(R.id.btnSlide).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransitionSet transitionSet = new TransitionSet()
                        .addTransition(new Slide(Gravity.RIGHT))
                        .addTransition(new ChangeBounds())
                        .setInterpolator(new FastOutLinearInInterpolator());
                TransitionManager.beginDelayedTransition(sceneRoot0, transitionSet);
                visible = !visible;
                tvAwesome.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
        findViewById(R.id.btnScale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionSet transitionSet = new TransitionSet()
                        .addTransition(new Scale(0.2f))
                        .addTransition(new ChangeBounds())
                        .setInterpolator(new FastOutLinearInInterpolator());
                TransitionManager.beginDelayedTransition(sceneRoot, transitionSet);
                visible = !visible;
                tvAwesome.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });

        findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionSet transitionSet = new TransitionSet()
                        .addTransition(new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN))
                        .addTransition(new ChangeBounds())
                        //.setInterpolator(new FastOutLinearInInterpolator())
                        ;
                TransitionManager.beginDelayedTransition(sceneRoot, transitionSet);
                visible = !visible;
                tvAwesome.setText(visible ? "Transitions are awesome!" : "text changed...");
            }
        });

        findViewById(R.id.btnColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(sceneRoot2, new Recolor());
                visible = !visible;
                viewTarget2.setTextColor(visible ? Color.RED : Color.YELLOW);
                viewTarget2.setBackground(new ColorDrawable(visible ? Color.YELLOW : Color.RED));
            }
        });

        findViewById(R.id.btnRotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(sceneRoot0, new Rotate());
                visible = !visible;
                viewTarget2.setRotation(visible ? 0 : 135);
            }
        });

        findViewById(R.id.btnExplode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Rect rect = new Rect();
                viewTarget2.getGlobalVisibleRect(rect);
                Transition transition = new Explode()
                        .setDuration(1000)
                        .setEpicenterCallback(new Transition.EpicenterCallback() {
                            @Override
                            public Rect onGetEpicenter(Transition transition) {
                                return rect;
                            }
                        })
                        //.excludeTarget(view99, true)
                        ;
                TransitionManager.beginDelayedTransition(sceneRoot2, transition);
                sceneRoot2.removeAllViews();
            }
        });

        findViewById(R.id.btnPath).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transition transition = new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500);
                TransitionManager.beginDelayedTransition(sceneRoot0, transition);

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewTarget6.getLayoutParams();
                visible = !visible;
                layoutParams.gravity = visible ? (Gravity.LEFT | Gravity.TOP) :
                        (Gravity.BOTTOM | Gravity.RIGHT);
                viewTarget6.setLayoutParams(layoutParams);
            }
        });
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
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
            }
        });
        startActivity(new Intent(this, TextFontActivity.class),
                ActivityOptions.makeSceneTransitionAnimation(this, view, view.getTransitionName()).toBundle());
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
