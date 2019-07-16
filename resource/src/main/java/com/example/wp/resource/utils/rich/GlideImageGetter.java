package com.example.wp.resource.utils.rich;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wp.resource.R;

/**
 * Created by Administrator on 2019/7/15
 * Description:
 */
public class GlideImageGetter implements Html.ImageGetter, Drawable.Callback {

    private final Context mContext;

    private final TextView mTextView;


    public static GlideImageGetter get(View view) {
        return (GlideImageGetter) view.getTag(R.id.drawable_callback_tag);
    }


    public GlideImageGetter(Context context, TextView textView) {
        this.mContext = context;
        this.mTextView = textView;
        mTextView.setTag(R.id.drawable_callback_tag, this);
    }

    @Override
    public Drawable getDrawable(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        if (url.endsWith(".gif")) {
            Glide.with(mContext)
                    .asGif()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new GifTarget(urlDrawable, mTextView, get(mTextView)));
        } else {
            Glide.with(mContext)
                    .asDrawable()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new DrawableTarget(urlDrawable, mTextView));
        }
        return urlDrawable;
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        mTextView.invalidate();
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {

    }

}