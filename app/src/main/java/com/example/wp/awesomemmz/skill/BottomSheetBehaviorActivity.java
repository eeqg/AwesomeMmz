package com.example.wp.awesomemmz.skill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;

public class BottomSheetBehaviorActivity extends BaseActivity {
	BottomSheetBehavior<LinearLayout> mBehavior;
	BottomSheetDialog bottomSheetDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bottom_sheet_behavior);
		
		LinearLayout bottomSheet = findViewById(R.id.bottomSheet);
		mBehavior = BottomSheetBehavior.from(bottomSheet);
		//设置默认先隐藏
		mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		//设置监听事件
		mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				Log.d("test_wp", "-----newState : " + newState);
			}
			
			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {
				Log.d("test_wp", "-----slideOffset = " + slideOffset);
			}
		});
		
		findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleBottomSheet();
			}
		});
		
		observeBottomSheetDialog();
		observeBottomSheetDialogFragment();
	}
	
	private void observeBottomSheetDialog() {
		if (bottomSheetDialog == null) {
			bottomSheetDialog = new BottomSheetDialog(this);
			bottomSheetDialog.setContentView(R.layout.dialog_bottom_sheet_test);
		}
		// bottomSheetDialog.show();
		findViewById(R.id.btnBottomDialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bottomSheetDialog.show();
			}
		});
	}
	
	private void observeBottomSheetDialogFragment() {
		findViewById(R.id.btnBottomDialogFragment).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SampleSheetDialogFragment sampleSheetDialogFragment = SampleSheetDialogFragment.newInstance();
				sampleSheetDialogFragment.show(getSupportFragmentManager(), "tag");
			}
		});
	}
	
	private boolean hideBottomSheet() {
		if (mBehavior != null && mBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
			mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
			return true;
		}
		return false;
	}
	
	private boolean collapseBottomSheet() {
		if (mBehavior != null && mBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
			mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
			return true;
		}
		return false;
	}
	
	private void toggleBottomSheet() {
		if (mBehavior == null) {
			return;
		}
		if (mBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
			mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		} else {
			mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
		}
	}
	
	@Override
	public void onBackPressed() {
		if (hideBottomSheet()) {
			return;
		}
		super.onBackPressed();
	}
}
