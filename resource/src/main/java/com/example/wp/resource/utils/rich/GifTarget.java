package com.example.wp.resource.utils.rich;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;


/**
 * Created by Administrator on 2019/7/15
 * Description:
 */
public class GifTarget extends CustomTarget<GifDrawable> {

    private UrlDrawable mDrawable;
    private TextView mTextView;
    private GlideImageGetter imageGetter;

    public GifTarget(UrlDrawable urlDrawable, TextView textView, GlideImageGetter imageGetter) {
        super();
        mDrawable = urlDrawable;
        mTextView = textView;
        this.imageGetter = imageGetter;
    }

    @Override
    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
        Rect rect;
        if (resource.getIntrinsicWidth() > 100) {
            float width;
            float height;
            if (resource.getIntrinsicWidth() >= mTextView.getWidth()) {
                float downScale = (float) resource.getIntrinsicWidth() / mTextView.getWidth();
                width = (float) resource.getIntrinsicWidth() / downScale;
                height = (float) resource.getIntrinsicHeight() / downScale;
            } else {
                float multiplier = (float) mTextView.getWidth() / resource.getIntrinsicWidth();
                width = (float) resource.getIntrinsicWidth() *  multiplier;
                height = (float) resource.getIntrinsicHeight() *  multiplier;
            }
            rect = new Rect(0, 0, Math.round(width), Math.round(height));
        } else {
            rect = new Rect(0, 0, resource.getIntrinsicWidth() * 2,
                    resource.getIntrinsicHeight() * 2);
        }
        resource.setBounds(rect);

        mDrawable.setBounds(rect);
        mDrawable.setDrawable(resource);


        mDrawable.setCallback(imageGetter);
        resource.setLoopCount(-1);
        resource.start();

        mTextView.setText(mTextView.getText());
        mTextView.invalidate();
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }
}
