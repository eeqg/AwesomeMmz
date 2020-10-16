package com.example.wp.awesomemmz.index;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.custom.CustomViewActivity;
import com.example.wp.awesomemmz.frame.LifecycleActivity;
import com.example.wp.awesomemmz.frame.LiveDataTestActivity;
import com.example.wp.awesomemmz.image.ImageActivity;
import com.example.wp.awesomemmz.index.bean.ClassInfoBean;
import com.example.wp.awesomemmz.index.provider.CustProviderActivity;
import com.example.wp.awesomemmz.index.video.VideoActivity;
import com.example.wp.awesomemmz.map.LocationActivity;
import com.example.wp.awesomemmz.other.BannerActivity;
import com.example.wp.awesomemmz.other.OverScrollActivity;
import com.example.wp.awesomemmz.other.PageSlideActivity;
import com.example.wp.awesomemmz.other.SpecActivity;
import com.example.wp.resource.utils.LaunchUtil;
import com.example.wp.resource.utils.LogUtils;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wp.picture.widget.SimpleFloating;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by wp on 2019/1/30.
 */
public class IndexFragment extends Fragment {
	
	private final static int MSG_EXPAND = 99;
	
	private Activity mActivity;
	private View rootView;
	private ArrayList<ClassInfoBean> data = new ArrayList<>();
	
	private ImageView tinyView;
	private ValueAnimator collapseAnimator;
	private ValueAnimator expandAnimator;
	float initValue, collapsedValue;
	private MyHandler myHandler;
	private SimpleFloating simpleFloating;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_list_common, container, false);
		return rootView;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		this.mActivity = getActivity();
		
		initData();
		initView();
		addTinyView();
	}
	
	private void initView() {
		RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		final IndexAdapter indexAdapter = new IndexAdapter(getContext());
		recyclerView.setAdapter(indexAdapter);
		
		indexAdapter.addAll(data);
		indexAdapter.notifyDataSetChanged();
		
		indexAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(int position) {
				String className = indexAdapter.getItem(position).classPath;
				if (TextUtils.isEmpty(className)) {
					return;
				}
				try {
					Class activityClass = Class.forName(className);
					LaunchUtil.launchActivity(getContext(), activityClass);
					// startActivity(new Intent(getContext(), activityClass));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				switch (newState) {
					case RecyclerView.SCROLL_STATE_DRAGGING:
					case RecyclerView.SCROLL_STATE_SETTLING:
						startCollapseAnimation();
						
						simpleFloating.startCollapseAnimation();
						break;
					case RecyclerView.SCROLL_STATE_IDLE:
						startExpandAnimation();
						
						simpleFloating.startExpandAnimationDelay();
						break;
				}
			}
			
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}
		});
	}
	
	private void initData() {
		data.add(new ClassInfoBean("banner", BannerActivity.class.getName()));
		data.add(new ClassInfoBean("scroll", OverScrollActivity.class.getName()));
		data.add(new ClassInfoBean("pageSlide", PageSlideActivity.class.getName()));
		data.add(new ClassInfoBean("image", ImageActivity.class.getName()));
		data.add(new ClassInfoBean("video", VideoActivity.class.getName()));
		data.add(new ClassInfoBean("SpecView", SpecActivity.class.getName()));
		data.add(new ClassInfoBean("recovery", ""));
		data.add(new ClassInfoBean("customView", CustomViewActivity.class.getName()));
		data.add(new ClassInfoBean("Lifecycle", LifecycleActivity.class.getName()));
		data.add(new ClassInfoBean("LiveData", LiveDataTestActivity.class.getName()));
		data.add(new ClassInfoBean("M-V-VM", ""));
		data.add(new ClassInfoBean("map & location", LocationActivity.class.getName()));
		data.add(new ClassInfoBean("ContentProvider", CustProviderActivity.class.getName()));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
		data.add(new ClassInfoBean("...", ""));
	}
	
	private void addTinyView() {
		FrameLayout.LayoutParams layoutParams0 = new FrameLayout.LayoutParams(120, 120);
		layoutParams0.gravity = Gravity.BOTTOM | Gravity.START;
		layoutParams0.leftMargin = 10;
		layoutParams0.bottomMargin = 150;
		ImageView iv = new ImageView(getContext());
		iv.setImageDrawable(new ColorDrawable(Color.parseColor("#90FF2323")));
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				simpleFloating.hide();
			}
		});
		simpleFloating = new SimpleFloating(getContext(), iv, layoutParams0);
		simpleFloating.setCollapseSite(SimpleFloating.Site.LEFT);
		simpleFloating.show();
		
		final ViewGroup contentView = mActivity.findViewById(android.R.id.content);
		tinyView = new ImageView(mActivity);
		tinyView.setImageResource(R.mipmap.image1);
		tinyView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(100, 100);
		layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
		contentView.addView(tinyView, layoutParams);
		
		tinyView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// contentView.removeView(imageView);
			}
		});
		
		tinyView.post(new Runnable() {
			@Override
			public void run() {
				initValue = tinyView.getX();
				collapsedValue = initValue + 80;
			}
		});
	}
	
	private void startCollapseAnimation() {
		if (myHandler != null && myHandler.hasMessages(MSG_EXPAND)) {
			myHandler.removeMessages(MSG_EXPAND);
		}
		if (expandAnimator != null && expandAnimator.isStarted()) {
			expandAnimator.cancel();
		}
		if (collapseAnimator != null && collapseAnimator.isStarted()) {
			collapseAnimator.cancel();
		}
		collapseAnimator = ValueAnimator.ofFloat(tinyView.getX(), collapsedValue).setDuration(400);
		collapseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float values = (float) animation.getAnimatedValue();
				LogUtils.d("----values = " + values);
				tinyView.setX(values);
			}
		});
		collapseAnimator.start();
	}
	
	private void startExpandAnimation() {
		if (myHandler == null) {
			myHandler = new MyHandler(IndexFragment.this);
		}
		if (myHandler.hasMessages(MSG_EXPAND)) {
			myHandler.removeMessages(MSG_EXPAND);
		}
		myHandler.sendEmptyMessageDelayed(MSG_EXPAND, 500);
	}
	
	private void startExpandAnimationRe() {
		if (collapseAnimator != null && collapseAnimator.isStarted()) {
			collapseAnimator.cancel();
		}
		if (expandAnimator != null && expandAnimator.isStarted()) {
			expandAnimator.cancel();
		}
		expandAnimator = ValueAnimator.ofFloat(tinyView.getX(), initValue).setDuration(400);
		expandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float values = (float) animation.getAnimatedValue();
				LogUtils.d("----values = " + values);
				tinyView.setX(values);
			}
		});
		expandAnimator.start();
	}
	
	public static class MyHandler extends Handler {
		private final WeakReference<IndexFragment> mFragment;
		
		MyHandler(IndexFragment fragment) {
			mFragment = new WeakReference<>(fragment);
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MSG_EXPAND) {
				mFragment.get().startExpandAnimationRe();
			}
		}
	}
}
