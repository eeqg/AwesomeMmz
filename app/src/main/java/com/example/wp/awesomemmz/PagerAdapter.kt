package com.example.wp.awesomemmz

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.wp.awesomemmz.common.EmptyFragment
import com.example.wp.awesomemmz.index.IndexFragment
import com.example.wp.awesomemmz.javaz.JavazFragment
import com.example.wp.awesomemmz.other.OtherFragment
import com.example.wp.awesomemmz.skill.SkillFragment
import com.example.wp.awesomemmz.star.StarFragment

/**
 * Created by wp on 2019/1/30.
 */
class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    private val titles = arrayOf("Index", "Skill", "Star", "Others", "Java", "2023")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> IndexFragment()
            1 -> SkillFragment.newInstance("1", "2")
            2 -> StarFragment.newInstance()
            3 -> OtherFragment()
            4 -> JavazFragment.newInstance(1)
            else -> EmptyFragment()
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}