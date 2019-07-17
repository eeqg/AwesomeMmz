package com.example.wp.resource.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * Created by wp on 2019/7/17.
 */
public class OverScrollView extends TwinklingRefreshLayout {
	public OverScrollView(Context context) {
		this(context, null);
	}
	
	public OverScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		init();
	}
	
	private void init() {
		setPureScrollModeOn();
	}
}
