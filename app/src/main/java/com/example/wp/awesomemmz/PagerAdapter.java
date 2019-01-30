package com.example.wp.awesomemmz;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wp.awesomemmz.index.IndexFragment;

/**
 * Created by wp on 2019/1/30.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
	
	private String[] titles = {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
	
	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int i) {
		return new IndexFragment();
	}
	
	@Override
	public int getCount() {
		return titles.length;
	}
	
	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
}
