package com.example.wp.awesomemmz.skill;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.skill.widget.ConfirmButton;
import com.wp.picture.widget.CircleImageView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AnimationsActivity extends AppCompatActivity {
    private View tvSignName;
    private LinearLayout llContainer;
    private LayoutTransition mTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);

        observeLayoutAnimation();
        observeConfirmBtn();
    }

    private void observeConfirmBtn() {
        final View tvOthers = findViewById(R.id.tvOthers);
        final View tvOther1 = findViewById(R.id.tvOther1);
        final ConfirmButton confirmBtn = findViewById(R.id.confirmBtn);
        confirmBtn.setText("确认完成");
        confirmBtn.setDistance(200f);
        confirmBtn.setOnConfirmListener(new ConfirmButton.OnConfirmListener() {
            @Override
            public void onStart() {
                tvOthers.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onEnd() {
                tvOther1.setVisibility(View.VISIBLE);
            }
        });
        final View tvReset = findViewById(R.id.tvReset);
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOthers.setVisibility(View.VISIBLE);
                tvOther1.setVisibility(View.INVISIBLE);
                confirmBtn.reset();
            }
        });
    }

    private void observeLayoutAnimation() {
        final View clLa = findViewById(R.id.clLa);

        Disposable disposable = Observable.timer(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        clLa.setVisibility(View.VISIBLE);
                    }
                });

        tvSignName = findViewById(R.id.tvSignName);
        findViewById(R.id.llSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSignName.setVisibility(View.VISIBLE);
                collapseSing();
            }
        });
        collapseSing();

        llContainer = findViewById(R.id.llContainer);
        findViewById(R.id.ivAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircleImageView circleImageView = new CircleImageView(AnimationsActivity.this);
                circleImageView.setImageResource(R.mipmap.ic_test_1);
                circleImageView.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
                // circleImageView.setVisibility(View.GONE);
                llContainer.addView(circleImageView);
            }
        });
        findViewById(R.id.ivRemove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llContainer.removeViewAt(llContainer.getChildCount() - 1);
            }
        });

        mTransition = new LayoutTransition();
        llContainer.setLayoutTransition(mTransition);
        setTransition();
    }

    private void collapseSing() {
        Disposable disposable = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tvSignName.setVisibility(View.GONE);
                    }
                });
    }

    private void setTransition() {
        /**
         * 添加View时过渡动画效果
         */
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(null, "rotationY", 0, 90, 0);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(null, "alpha", 0, 1);
        ObjectAnimator transXAnimator = ObjectAnimator.ofFloat(null, "translationX", -100, 0);
        ObjectAnimator transYAnimator = ObjectAnimator.ofFloat(null, "translationY", -100, 0);
        // AnimatorSet animatorSet = new AnimatorSet();
        // animatorSet.playTogether(alphaAnimator);
        // animatorSet.play(alphaAnimator).after(rotationAnimator);
        // animatorSet.playSequentially(alphaAnimator, rotationAnimator);
        transXAnimator.setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
        mTransition.setAnimator(LayoutTransition.APPEARING, transXAnimator);

        // animatorSet.addListener(new Animator.AnimatorListener() {
        // 	@Override
        // 	public void onAnimationStart(Animator animation) {
        // 		llContainer.getChildAt(llContainer.getChildCount() - 1).setVisibility(View.VISIBLE);
        // 	}
        //
        // 	@Override
        // 	public void onAnimationEnd(Animator animation) {
        //
        // 	}
        //
        // 	@Override
        // 	public void onAnimationCancel(Animator animation) {
        //
        // 	}
        //
        // 	@Override
        // 	public void onAnimationRepeat(Animator animation) {
        //
        // 	}
        // });

        // ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(null, "scale", 0, 1)
        // 		.setDuration(600);
        // mTransition.setAnimator(LayoutTransition.APPEARING, scaleAnimator);

        /**
         * 移除View时过渡动画效果
         */
        // ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(null, "rotationX", 0, -90, 0);
        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(null, "alpha", 1, 0);
        removeAnimator.setDuration(mTransition.getDuration(LayoutTransition.DISAPPEARING));
        mTransition.setAnimator(LayoutTransition.DISAPPEARING, removeAnimator);

        /**
         * view 动画改变时，布局中的每个子view动画的时间间隔
         */
        mTransition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        mTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);


        /**
         *LayoutTransition.CHANGE_APPEARING和LayoutTransition.CHANGE_DISAPPEARING的过渡动画效果
         * 必须使用PropertyValuesHolder所构造的动画才会有效果，不然无效！使用ObjectAnimator是行不通的,
         * 发现这点时真特么恶心,但没想到更恶心的在后面,在测试效果时发现在构造动画时，”left”、”top”、”bottom”、”right”属性的
         * 变动是必须设置的,至少设置两个,不然动画无效,问题是我们即使这些属性不想变动!!!也得设置!!!
         * 我就问您恶不恶心!,因为这里不想变动,所以设置为(0,0)
         *
         */
        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 0);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 0);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 0);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 0);


        /**
         * view被添加时,其他子View的过渡动画效果
         */
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 1, 1.5f, 1);
        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhBottom, animator).
                setDuration(mTransition.getDuration(LayoutTransition.CHANGE_APPEARING));

        mTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);


        /**
         * view移除时，其他子View的过渡动画
         */
        PropertyValuesHolder pvhRotation =
                PropertyValuesHolder.ofFloat("scaleX", 1, 1.5f, 1);
        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhBottom, pvhRotation).
                setDuration(mTransition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));

        mTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
