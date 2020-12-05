package com.example.wp.awesomemmz.skill

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.wp.awesomemmz.common.GlideImageLoader
import com.example.wp.resource.utils.LogUtils

/**
 * Created by wp on 2020/12/3.
 */
class PicturePagerAdapter(val list: List<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = ImageView(container.context)
        GlideImageLoader.getInstance().load(view, list[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        LogUtils.d("-----destroyItem: $`object`")
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int = list.size
}