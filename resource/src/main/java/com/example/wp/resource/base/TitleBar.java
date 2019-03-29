package com.example.wp.resource.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wp.resource.R;

/**
 * Created by wp on 2019/1/23.
 */
public class TitleBar extends FrameLayout {
	private View titleBar;
	private TextView tvTitle;
	
	public TitleBar(@NonNull Context context) {
		this(context, null);
	}
	
	public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		initView();
	}
	
	public void initView() {
		inflate(getContext(), R.layout.include_base_title_bar, this);
		
		titleBar = findViewById(R.id.titleBar);
		tvTitle = findViewById(R.id.tvTitle);
	}
	
	public TitleBar setBack(Activity activity) {
		setBack(activity, 0);
		return this;
	}
	
	public TitleBar setBack(final Activity activity, int resId) {
		if (resId == 0) {
			resId = R.mipmap.ic_back_black;
		}
		
		ImageView ivBack = findViewById(R.id.ivBack);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setImageResource(resId);
		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activity.finish();
			}
		});
		return this;
	}
	
	public TitleBar setTitle(String title) {
		return setTitle(title, -1);
	}
	
	public TitleBar setTitle(int resId) {
		return setTitle(resId, -1);
	}
	
	public TitleBar setTitle(String title, int colorId) {
		tvTitle.setText(title);
		if (colorId != -1) {
			tvTitle.setTextColor(getResources().getColor(colorId));
		}
		return this;
	}
	
	public TitleBar setTitle(int resId, int colorId) {
		return setTitle(getResources().getString(resId), colorId);
	}
	
	public TitleBar setTextAction(int resId, View.OnClickListener listener) {
		setTextAction(resId, -1, listener);
		return this;
	}
	
	public TitleBar setTextAction(int resId, int colorId, View.OnClickListener listener) {
		TextView tvAction1 = findViewById(R.id.tvAction1);
		tvAction1.setVisibility(VISIBLE);
		tvAction1.setText(getResources().getString(resId));
		if (colorId != -1) {
			tvAction1.setTextColor(getResources().getColor(colorId));
		}
		tvAction1.setOnClickListener(listener);
		return this;
	}
	
	public TitleBar setTextAction2(int resId, View.OnClickListener listener) {
		setTextAction2(resId, -1, listener);
		return this;
	}
	
	public TitleBar setTextAction2(int resId, int colorId, View.OnClickListener listener) {
		TextView tvAction2 = findViewById(R.id.tvAction2);
		tvAction2.setVisibility(VISIBLE);
		tvAction2.setText(getResources().getString(resId));
		if (colorId != -1) {
			tvAction2.setTextColor(getResources().getColor(colorId));
		}
		tvAction2.setOnClickListener(listener);
		return this;
	}
	
	public TitleBar setIconAction(int resId, View.OnClickListener listener) {
		ImageView ivAction1 = findViewById(R.id.ivAction1);
		ivAction1.setVisibility(VISIBLE);
		ivAction1.setImageResource(resId);
		ivAction1.setOnClickListener(listener);
		return this;
	}
	
	public TitleBar setIconAction2(int resId, View.OnClickListener listener) {
		ImageView ivAction2 = findViewById(R.id.ivAction2);
		ivAction2.setVisibility(VISIBLE);
		ivAction2.setImageResource(resId);
		ivAction2.setOnClickListener(listener);
		return this;
	}
	
	public TitleBar showDivider(boolean show) {
		findViewById(R.id.viewDivider).setVisibility(show ? VISIBLE : GONE);
		return this;
	}
	
	public TitleBar setBackground(@ColorInt int color) {
		findViewById(R.id.titleBar).setBackgroundColor(color);
		return this;
	}
}
