package com.example.wp.awesomemmz.other;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wp.awesomemmz.HeaderAdapter;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.GlideImageLoader;
import com.example.wp.awesomemmz.common.GlideImageLoader2;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.ColorUtil;
import com.example.wp.resource.utils.LogUtils;
import com.example.wp.resource.widget.CircleIndicator;
import com.example.wp.resource.widget.LoopViewPager;
import com.example.wp.resource.widget.banner.Banner;
import com.example.wp.resource.widget.banner.BannerConfig;
import com.example.wp.resource.widget.banner.listener.OnBannerListener;
import com.example.wp.resource.widget.banner.transformer.AccordionTransformer;
import com.example.wp.resource.widget.banner.transformer.BackgroundToForegroundTransformer;
import com.example.wp.resource.widget.banner.transformer.CubeInTransformer;
import com.example.wp.resource.widget.banner.transformer.CubeOutTransformer;
import com.example.wp.resource.widget.banner.transformer.DefaultTransformer;
import com.example.wp.resource.widget.banner.transformer.DepthPageTransformer;
import com.example.wp.resource.widget.banner.transformer.FlipHorizontalTransformer;
import com.example.wp.resource.widget.banner.transformer.FlipVerticalTransformer;
import com.example.wp.resource.widget.banner.transformer.ForegroundToBackgroundTransformer;
import com.example.wp.resource.widget.banner.transformer.RotateDownTransformer;
import com.example.wp.resource.widget.banner.transformer.RotateUpTransformer;
import com.example.wp.resource.widget.banner.transformer.ScaleInOutTransformer;
import com.example.wp.resource.widget.banner.transformer.StackTransformer;
import com.example.wp.resource.widget.banner.transformer.TabletTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomInTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomOutSlideTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomOutTranformer;
import com.wp.picture.banner.callback.BindViewCallBack;
import com.wp.picture.banner.callback.CreateViewCaller;
import com.wp.picture.banner.callback.OnClickBannerListener;
import com.wp.picture.widget.CommonViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends BaseActivity implements OnBannerListener {

    @BindView(R.id.bannerContainer2)
    View bannerContainer2;
    @BindView(R.id.banner2)
    Banner banner2;
    @BindView(R.id.banner3)
    com.wp.picture.banner.Banner banner3;
    @BindView(R.id.bannerContainer3)
    View bannerContainer3;
    @BindView(R.id.viewPager)
    CommonViewPager viewPager;

    Banner banner;
    String[] colorsStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);

        setupTitleBar("Banner");

        colorsStr = getResources().getStringArray(R.array.colorsStr);

        observeLooperView();
        observeBannerView();
        observerBannerBg();
        observeBannerPower();
        observeCommonViewPager();
    }

    private void observeLooperView() {
        LoopViewPager loopView = findViewById(R.id.loopView);
        loopView.setAdapter(new HeaderAdapter(this));
        CircleIndicator indicatorView = findViewById(R.id.indicatorView);
        indicatorView.setViewPager(loopView);
    }

    private void observeBannerView() {
        String[] urls = getResources().getStringArray(R.array.url);
        List<String> images = Arrays.asList(urls);

        String[] title = getResources().getStringArray(R.array.title);
        List<String> titles = Arrays.asList(title);

        banner = findViewById(R.id.banner);
        banner.setImages(images)
                .setBannerTitles(titles) //
                .setImageLoader(new GlideImageLoader2())
                .setOnBannerListener(this) //click
                .start();

        // start/stop.
        findViewById(R.id.tvStart).setOnClickListener(new View.OnClickListener() {
            private boolean isRunning = true;

            @Override
            public void onClick(View v) {
                if (banner.isRunning()) {
                    banner.stopAutoPlay();
                } else {
                    banner.startAutoPlay();
                }
                // isRunning = !isRunning;
            }
        });

        //切换动画
        Spinner spinnerStyle = findViewById(R.id.spinnerStyle);
        // spinnerStyle.setAdapter();
        spinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        banner.setBannerAnimation(DefaultTransformer.class);
                        break;
                    case 1:
                        banner.setBannerAnimation(AccordionTransformer.class);
                        break;
                    case 2:
                        banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
                        break;
                    case 3:
                        banner.setBannerAnimation(ForegroundToBackgroundTransformer.class);
                        break;
                    case 4:
                        banner.setBannerAnimation(CubeInTransformer.class);
                        break;
                    case 5:
                        banner.setBannerAnimation(CubeOutTransformer.class);
                        break;
                    case 6:
                        banner.setBannerAnimation(DepthPageTransformer.class);
                        break;
                    case 7:
                        banner.setBannerAnimation(FlipHorizontalTransformer.class);
                        break;
                    case 8:
                        banner.setBannerAnimation(FlipVerticalTransformer.class);
                        break;
                    case 9:
                        banner.setBannerAnimation(RotateDownTransformer.class);
                        break;
                    case 10:
                        banner.setBannerAnimation(RotateUpTransformer.class);
                        break;
                    case 11:
                        banner.setBannerAnimation(ScaleInOutTransformer.class);
                        break;
                    case 12:
                        banner.setBannerAnimation(StackTransformer.class);
                        break;
                    case 13:
                        banner.setBannerAnimation(TabletTransformer.class);
                        break;
                    case 14:
                        banner.setBannerAnimation(ZoomInTransformer.class);
                        break;
                    case 15:
                        banner.setBannerAnimation(ZoomOutTranformer.class);
                        break;
                    case 16:
                        banner.setBannerAnimation(ZoomOutSlideTransformer.class);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //指示器显示位置
        ((Spinner) findViewById(R.id.spinnerIndicator))
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                banner.setIndicatorGravity(BannerConfig.LEFT);
                                break;
                            case 1:
                                banner.setIndicatorGravity(BannerConfig.CENTER);
                                break;
                            case 2:
                                banner.setIndicatorGravity(BannerConfig.RIGHT);
                                break;
                        }
                        banner.start();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        //指示器显示方式
        ((Spinner) findViewById(R.id.spinnerIndicatorStyle))
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                banner.updateBannerStyle(BannerConfig.NOT_INDICATOR);
                                break;
                            case 1:
                                banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                                break;
                            case 2:
                                banner.updateBannerStyle(BannerConfig.NUM_INDICATOR);
                                break;
                            case 3:
                                banner.updateBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
                                break;
                            case 4:
                                banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                                break;
                            case 5:
                                banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }

    private void observerBannerBg() {
        String[] urls = getResources().getStringArray(R.array.url4);
        List<String> images = Arrays.asList(urls);
        banner2.setImages(images)
                .setImageLoader(new GlideImageLoader2())
                .setOnBannerListener(this) //click
                .start();
        // banner2.setOffscreenPageLimit(5);
        // banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            ValueAnimator valueAnimator;
            int dtColor;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int curColor = Color.parseColor(colorsStr[position]);
                int nextColor = Color.parseColor(colorsStr[(position + 1 == colorsStr.length ? 0 : position + 1)]);
                dtColor = ColorUtil.calculateGradientColor(curColor, nextColor, positionOffset);
                bannerContainer2.setBackgroundColor(dtColor);
            }

            @Override
            public void onPageSelected(final int position) {
                // bannerContainer2.setBackgroundColor(Color.parseColor(colorsStr[position]));

                ColorUtil.startGradientColorAnimator(dtColor, Color.parseColor(colorsStr[position]), bannerContainer2);

                // int curColor = Color.parseColor(colorsStr[position]);
                // Log.d("test_wp", "curColor = " + curColor);
                // final int curRed = Color.red(curColor);
                // final int curGreen = Color.green(curColor);
                // final int curBlue = Color.blue(curColor);
                // if (valueAnimator != null && valueAnimator.isRunning()) {
                // 	valueAnimator.end();
                // }
                // valueAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(200);
                // valueAnimator.setInterpolator(new DecelerateInterpolator());
                // valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                // 	@Override
                // 	public void onAnimationUpdate(ValueAnimator animation) {
                // 		float animatedValue = (float) animation.getAnimatedValue();
                // 		bannerContainer2.setBackgroundColor(Color.rgb((int) (dtRed + (curRed - dtRed) * animatedValue),
                // 				(int) (dtGreen + (curGreen - dtGreen) * animatedValue),
                // 				(int) (dtBlue + (curBlue - dtBlue) * animatedValue)));
                // 	}
                // });
                // valueAnimator.addListener(new Animator.AnimatorListener() {
                // 	@Override
                // 	public void onAnimationStart(Animator animation) {
                //
                // 	}
                //
                // 	@Override
                // 	public void onAnimationEnd(Animator animation) {
                // 		bannerContainer2.setBackgroundColor(Color.parseColor(colorsStr[position]));
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void observeBannerPower() {
        String[] urls = getResources().getStringArray(R.array.url);
        List<String> images = Arrays.asList(urls);
        List<String> images2 = new ArrayList<>();
        // images2.add("https://t00img.yangkeduo.com/goods/images/2018-10-08/7861a4b9c8d52448ceccafee8064ed88.jpeg");
        images2.addAll(images);
        // // 设置banner的尺寸
        // int screenWidth = ScreenUtils.getScreenWidth(this);
        // int bannerHeight = (int) (screenWidth * 0.5f);
        // RelativeLayout.LayoutParams bannerLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, bannerHeight);
        // banner3.setLayoutParams(bannerLp);
        // setLayoutParams(bannerLp);

        banner3.setViewIndex(1)
                .createView(CreateViewCaller.build())
                .bindView(new BindViewCallBack<View, String>() {
                    @Override
                    public void bindView(View frameLayout, String data, int position) {
                        FrameLayout view = (FrameLayout) CreateViewCaller.findFrameLayout(frameLayout);
                        view.removeAllViews();
                        View bannerRooter = LayoutInflater.from(mActivity).inflate(R.layout.view_home_banner, null);
                        view.addView(bannerRooter);
                        ImageView ivBanner = bannerRooter.findViewById(R.id.ivBanner);
                        GlideImageLoader.getInstance().load(ivBanner, data);
                    }
                })
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    int dtColor;

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        int curColor = Color.parseColor(colorsStr[position]);
                        int nextColor = Color.parseColor(colorsStr[(position + 1 == colorsStr.length ? 0 : position + 1)]);
                        dtColor = ColorUtil.calculateGradientColor(curColor, nextColor, positionOffset);
                        bannerContainer3.setBackgroundColor(dtColor);
                    }

                    @Override
                    public void onPageSelected(int position) {
                        ColorUtil.startGradientColorAnimator(dtColor, Color.parseColor(colorsStr[position]), bannerContainer3);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                })
                .setOnClickBannerListener(new OnClickBannerListener<View, String>() {
                    @Override
                    public void onClickBanner(View view, String data, int position) {
                        LogUtils.d("onClickBanner()---" + data);
                    }
                })
                .execute(images2);
    }

    private void observeCommonViewPager() {
        String[] urls = getResources().getStringArray(R.array.fruits);
        List<String> images = Arrays.asList(urls);

        viewPager
                .createItemView(new CommonViewPager.ViewCreator<View, String>() {
                    @Override
                    public View onCreateView(int position) {
                        return LayoutInflater.from(BannerActivity.this).inflate(R.layout.item_picture, null);
                    }

                    @Override
                    public void onBindView(View view, String data, int position) {
                        final ImageView ivPicture = view.findViewById(R.id.ivPicture);
                        GlideImageLoader.getInstance().load(ivPicture, data);
                    }
                })
                .setOnItemSelectedListener(new CommonViewPager.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(String data, int position) {

                    }
                })
                .setOnItemClickListener(new CommonViewPager.OnItemClickListener<View, String>() {
                    @Override
                    public void onItemClick(View view, String data, int position) {
                        promptMessage("" + position);
                    }
                })
                .setMultiPage(true)//一页显示多item
                .execute(images);
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }

    private void startGradientColorAnimator(final int startColor, final int endColor, View view) {
        final View targetView = view;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(200);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                targetView.setBackgroundColor(ColorUtil.calculateGradientColor(startColor, endColor, animatedValue));
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                targetView.setBackgroundColor(endColor);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
