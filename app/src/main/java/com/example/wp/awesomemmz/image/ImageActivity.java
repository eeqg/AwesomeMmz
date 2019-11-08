package com.example.wp.awesomemmz.image;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.CustomGlideTransform;
import com.example.wp.awesomemmz.common.GlideImageLoader;
import com.example.wp.awesomemmz.common.Picker;
import com.example.wp.awesomemmz.skill.aspect.CheckPermission;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.LogUtils;
import com.example.wp.resource.utils.StatusBarUtil;
import com.example.wp.resource.widget.ShadowDrawable;
import com.example.wp.resource.widget.shadow.ShadowDrawableWrapper;
import com.wp.picture.ninegrid.NineGridView;
import com.wp.picture.picker.PictureLayout;
import com.wp.picture.preview.PPView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {

    private final int CODE_CAMERA = 1;
    private final int CODE_PICK_MULTI = 2;

    @BindView(R.id.titleBarRoot)
    LinearLayout titleBarRoot;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.titleBar)
    View titleBar;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivHead)
    ImageView ivHead;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ivShadow)
    View ivShadow;
    @BindView(R.id.ivShadow3)
    View ivShadow3;
    @BindView(R.id.ivSharp1)
    ImageView ivSharp1;
    @BindView(R.id.ivSharp2)
    ImageView ivSharp2;
    @BindView(R.id.ivSharp3)
    ImageView ivSharp3;
    @BindView(R.id.testView)
    View testView;
    @BindView(R.id.ivTest)
    ImageView ivTest;
    @BindView(R.id.pictureLayout)
    PictureLayout pictureLayout;
    @BindView(R.id.btnCamera)
    Button btnCamera;
    @BindView(R.id.ivShowCamera)
    ImageView ivShowCamera;
    @BindView(R.id.nineGridView)
    NineGridView nineGridView;

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Uri uri = getIntent().getData();
        if (uri != null) {
            LogUtils.d("-----uri = " + uri.toString());
            promptMessage(uri.toString());
            String id = uri.getQueryParameter("id");
            // promptMessage("params: " + id);
        }

        observeBar();
        observeViewShadow();
        observeSharp();
        observePictureLayout();
        observeCamera();
        observeNineGridView();

        GlideImageLoader.getInstance().load(ivTest, "https://t00img.yangkeduo.com/goods/images/2019-03-20/14ab95e7-7cee-4c1a-acd6-cb94b8b4a951.jpg");
    }

    private void observeBar() {
        titleBarRoot.addView(StatusBarUtil.createTranslucentStatusBarView(this, 0), 0);
        titleBar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ivBack.setImageResource(R.mipmap.ic_back_white);
        tvTitle.setText("image");
        tvTitle.setTextColor(getResources().getColor(R.color.colorWhite));

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int height = ivHead.getMeasuredHeight();
                LogUtils.d("-----height = " + height);
                LogUtils.d("-----scrollY = " + scrollY);
                float percent = scrollY * 1.2F / height;
                if (percent > 1) {
                    percent = 1;
                }

                int color = changeAlpha(getResources().getColor(R.color.colorTitleBar), percent);
                LogUtils.d("-----color = " + color);
                titleBarRoot.setBackgroundColor(color);
            }
        });
    }

    private void observeViewShadow() {
        ShadowDrawable.setShadowDrawable(ivShadow, Color.parseColor("#801122FF"), 50,
                Color.parseColor("#88FE5891"), 50, 0, 0);

        ShadowDrawableWrapper shadowDrawableWrapper = new ShadowDrawableWrapper(this,
                getResources().getDrawable(R.mipmap.image4), 10, 15, 15);
        ivShadow3.setBackgroundDrawable(shadowDrawableWrapper);
    }

    private void observeSharp() {
        GlideImageLoader.getInstance().load(ivSharp1, "http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg");
        GlideImageLoader.getInstance().load(ivSharp2, "http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg",
                new CustomGlideTransform(true, 0, 2, getResources().getColor(R.color.colorPrimary)));
        GlideImageLoader.getInstance().load(ivSharp3, "http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg",
                new CustomGlideTransform(false, 10, 2, getResources().getColor(R.color.colorPrimary)));
    }

    protected int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    private void observePictureLayout() {
        pictureLayout.setOnPictureListener(new PictureLayout.OnPictureListener() {
            @Override
            public void onInsert() {
                // pictureLayout.addPictureUrl("http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg");
                Picker.pickMulti(ImageActivity.this, CODE_PICK_MULTI,
                        pictureLayout.getMaxCount() - pictureLayout.size());
            }

            @Override
            public void onEdit(int position, Uri pictureUri) {
                LogUtils.d("-----position = " + position);
                pictureLayout.removePictureUri(position);
            }

            @Override
            public void onSelect(int position, Uri pictureUri) {
                LogUtils.d("-----position = " + position);
                ArrayList<String> imgList = new ArrayList<>();
                for (Uri uri : pictureLayout.getPictureList()) {
                    imgList.add(uri.toString());
                }
                PPView.build().urlList(imgList).position(position).show(ImageActivity.this);
            }
        });
    }

    private void observeNineGridView() {
        String[] stringArray = getResources().getStringArray(R.array.test_num_url);
        List<String> images = Arrays.asList(stringArray);
        ArrayList<ImageInfoBean> imageInfo = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ImageInfoBean info = new ImageInfoBean();
            info.imgUrl = images.get(i);
            imageInfo.add(info);
            // break;
        }
        ImageInfoBean info1 = new ImageInfoBean();
        info1.imgUrl = "https://img.alicdn.com/bao/uploaded/i1/2683201295/O1CN01stv3RB1LR9Q5oVrSq_!!2683201295.jpg";
        imageInfo.add(0, info1);
        ImageInfoBean info0 = new ImageInfoBean();
        info0.imgUrl = "https://img.alicdn.com/bao/uploaded/i4/2683201295/O1CN01l7KUTs1LR9Q30cmhb_!!2683201295.jpg";
        info0.isVideo = true;
        info0.videoUrl = "https://cloud.video.taobao.com/play/u/2683201295/p/2/e/6/t/1/226176442207.mp4?appKey=38829";
        imageInfo.add(0, info0);
        nineGridView.setAdapter(new NineGridImageAdapter(this, imageInfo));
    }


    // @RequiresPermission(Manifest.permission.CAMERA)
    private void observeCamera() {
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @CheckPermission({Manifest.permission.CAMERA})
            @Override
            public void onClick(View v) {
                uri = Picker.pickCamera(ImageActivity.this, CODE_CAMERA);
                LogUtils.d("-----uri = " + uri);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODE_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        LogUtils.d("----uri = " + uri);
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        ivShowCamera.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CODE_PICK_MULTI:
                if (resultCode == RESULT_OK) {
                    final ArrayList<BaseMedia> medias = Boxing.getResult(data);
                    if (medias != null && medias.size() > 0) {
                        for (BaseMedia media : medias) {
                            LogUtils.d("-----" + media.getPath());
                            pictureLayout.addPictureUrl(media.getPath());
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
