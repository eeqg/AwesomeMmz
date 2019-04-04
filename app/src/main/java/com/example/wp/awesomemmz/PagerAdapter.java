package com.example.wp.awesomemmz;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wp.awesomemmz.index.IndexFragment;
import com.example.wp.awesomemmz.skill.SkillFragment;
import com.example.wp.awesomemmz.star.StarFragment;

/**
 * Created by wp on 2019/1/30.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
	
	private String[] titles = {"Index", "Skill", "Star", "2021", "2022", "2023", "2024", "2025"};
	
	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position) {
		if (position == 1) {
			return SkillFragment.newInstance("1", "2");
		} else if (position == 2) {
			return StarFragment.newInstance();
		}
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
