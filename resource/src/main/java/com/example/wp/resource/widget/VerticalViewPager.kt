package com.example.wp.resource.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.wp.resource.widget.banner.transformer.DefaultTransformer

/**
 * Created by wp on 2020/12/3.
 */
class VerticalViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    init {
        //设置viewpager的切换动画,这里设置才能真正实现垂直滑动的viewpager
        setPageTransformer(true, DefaultTransformer())
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val intercept = super.onInterceptTouchEvent(swapEvent(ev))
        swapEvent(ev)
        return intercept
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(swapEvent(ev));
    }

    private fun swapEvent(event: MotionEvent): MotionEvent? {
        //获取宽高
        val width = width.toFloat()
        val height = height.toFloat()
        //将Y轴的移动距离转变成X轴的移动距离
        val swappedX = event.y / height * width
        //将X轴的移动距离转变成Y轴的移动距离
        val swappedY = event.x / width * height
        //重设event的位置
        event.setLocation(swappedX, swappedY)
        return event
    }
}