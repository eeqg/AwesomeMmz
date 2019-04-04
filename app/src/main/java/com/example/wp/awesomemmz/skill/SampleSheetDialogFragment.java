package com.example.wp.awesomemmz.skill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wp.awesomemmz.R;

/**
 * Created by wp on 2019/4/4.
 */
public class SampleSheetDialogFragment extends BottomSheetDialogFragment {
	
	public static SampleSheetDialogFragment newInstance() {
		return new SampleSheetDialogFragment();
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_bottom_sheet_test, container, false);
		return view;
	}
}
