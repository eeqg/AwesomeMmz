package com.example.wp.awesomemmz.common;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wp.resource.utils.imageload.ImageLoader;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class GlideImageLoader implements ImageLoader, com.wp.picture.video.ImageLoader {

    private static GlideImageLoader INSTANCE;

    private GlideImageLoader() {
    }

    public static GlideImageLoader getInstance() {
        if (INSTANCE == null) {
            synchronized (GlideImageLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GlideImageLoader();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void load(@NonNull ImageView imageView, String imageUrl) {
        load(imageView, imageUrl, com.example.wp.resource.R.mipmap.ic_placeholder);
    }

    @Override
    public void load(@NonNull ImageView imageView, String imageUrl, @DrawableRes int defaultImage) {
        if (defaultImage == 0) {
            defaultImage = com.example.wp.resource.R.mipmap.ic_placeholder;
        }
        RequestOptions options = new RequestOptions()
                // .centerCrop()
                .placeholder(defaultImage)
                .error(defaultImage);
        loadReal(imageView, imageUrl, options);
    }

    public void loadBlur(ImageView imageView, String imageUrl) {
        loadBlur(imageView, imageUrl, 25, 1);
    }

    @Override
    public void loadBlur(@NonNull ImageView imageView, String imageUrl, int radius, int sampling) {
        RequestOptions requestOptions = bitmapTransform(new BlurTransformation(radius, sampling))
                .placeholder(com.example.wp.resource.R.mipmap.ic_placeholder)
                .error(com.example.wp.resource.R.mipmap.ic_placeholder);
        loadReal(imageView, imageUrl, requestOptions);
    }

    @Override
    public void loadCircle(@NonNull ImageView imageView, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(com.example.wp.resource.R.mipmap.ic_placeholder)
                .error(com.example.wp.resource.R.mipmap.ic_placeholder)
                .transform(new CircleCrop());
        loadReal(imageView, imageUrl, requestOptions);
    }

    @Override
    public void loadRound(@NonNull ImageView imageView, String imageUrl, int radius) {
        RequestOptions options = bitmapTransform(new RoundedCorners(radius))
                .placeholder(com.example.wp.resource.R.mipmap.ic_placeholder)
                .error(com.example.wp.resource.R.mipmap.ic_placeholder);
        loadReal(imageView, imageUrl, options);
    }

    public void loadTopRounded(ImageView imageView, String imageUrl, int radius) {
        loadRounded(imageView, imageUrl, radius, 0, RoundedCornersTransformation.CornerType.TOP);
    }

    public void loadBottomRounded(ImageView imageView, String imageUrl, int radius) {
        loadRounded(imageView, imageUrl, radius, 0, RoundedCornersTransformation.CornerType.BOTTOM);
    }

    public void loadRounded(ImageView imageView, String imageUrl, int radius, int margin,
                            RoundedCornersTransformation.CornerType cornerType) {
        RequestOptions options = bitmapTransform(new RoundedCornersTransformation(radius, margin, cornerType))
                .placeholder(com.example.wp.resource.R.mipmap.ic_placeholder)
                .error(com.example.wp.resource.R.mipmap.ic_placeholder);
        loadReal(imageView, imageUrl, options);
    }

    @Override
    public void load(@NonNull ImageView imageView, String imageUrl, Object transformation) {
        RequestOptions options = bitmapTransform((Transformation<Bitmap>) transformation)
                .placeholder(com.example.wp.resource.R.mipmap.ic_placeholder)
                .error(com.example.wp.resource.R.mipmap.ic_placeholder);
        loadReal(imageView, imageUrl, options);
    }

    private void loadReal(@NonNull ImageView imageView, String imageUrl, RequestOptions options) {
        if (imageUrl == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void displayThumb(@NonNull ImageView imageView, @NonNull String s) {
        load(imageView, s);
    }
}
