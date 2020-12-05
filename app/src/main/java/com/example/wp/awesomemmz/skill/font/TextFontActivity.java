package com.example.wp.awesomemmz.skill.font;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.skill.transition.MyTransition;
import com.example.wp.resource.base.BaseActivity;

public class TextFontActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_font);

		executeTransition();
		
		final TextView tvDisplay = findViewById(R.id.tvDisplay);
		tvDisplay.setTypeface(getTypeface(FontType.NORMAL));
		
		Spinner fontTypeSpinner = findViewById(R.id.spinner);
		final FontType[] fontTypes = FontType.values();
		fontTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fontTypes));
		fontTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				tvDisplay.setTypeface(getTypeface(fontTypes[position]));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			
			}
		});
	}
	
	private Typeface getTypeface(FontType fontType) {
		Typeface typeface;
		try {
			typeface = Typeface.createFromAsset(getAssets(), fontType.getPath());
		} catch (Exception e) {
			promptMessage("catch exception.");
			typeface = Typeface.DEFAULT;
		}
		return typeface;
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public void executeTransition() {
		postponeEnterTransition();

		final View decorView = getWindow().getDecorView();
		getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				decorView.getViewTreeObserver().removeOnPreDrawListener(this);
				supportStartPostponedEnterTransition();
				return true;
			}
		});

		MyTransition transition = new MyTransition();
//		transition.setPositionDuration(300);
//		transition.setSizeDuration(300);
//		transition.setPositionInterpolator(new FastOutLinearInInterpolator());
//		transition.setSizeInterpolator(new FastOutSlowInInterpolator());
		transition.addTarget("message");

		getWindow().setSharedElementEnterTransition(transition);
	}
}
