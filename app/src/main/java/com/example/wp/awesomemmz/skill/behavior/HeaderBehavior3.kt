package com.example.wp.awesomemmz.skill.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.wp.resource.utils.LogUtils


/**
 * Created by wp on 2020/3/27.
 */
class HeaderBehavior3(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    // 界面整体向上滑动，达到列表可滑动的临界点
    private var upReach = false
    // 列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private var downReach = false
    // 列表上一个全部可见的item位置
    private var lastPosition = -1

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downReach = false
                upReach = false
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        //    axes 表示方向 有一下两种值
        //  *     ViewCompat.SCROLL_AXIS_HORIZONTAL 横向滑动
        //  *     ViewCompat.SCROLL_AXIS_VERTICAL 纵向滑动
        // public boolean startNestedScroll(int axes)
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL !== 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (target is RecyclerView) {
            // 列表第一个全部可见Item的位置
            val pos = (target.layoutManager as LinearLayoutManager?)!!.findFirstCompletelyVisibleItemPosition()
            if (pos == 0 && pos < lastPosition) {
                downReach = true
            }
            LogUtils.d("-----dy = $dy-----translationY = ${child.translationY}")
            // 整体可以滑动，否则RecyclerView消费滑动事件
            if (canScroll(child, dy) && pos == 0) {
                // 让CoordinatorLayout消费滑动事件
                //consumed[0]为水平方向应该消耗的距离，consumed[1]为垂直方向应该消耗的距离
                consumed[1] = dy

                var finalY = child.translationY - dy
                if (finalY < -child.height) {
                    finalY = (-child.height).toFloat()
                    upReach = true
                } else if (finalY > 0) {
                    finalY = 0f
                }
                child.translationY = finalY
            }
            lastPosition = pos
        }
    }

    private fun canScroll(child: View, scrollY: Int): Boolean {
        if (scrollY > 0 && child.translationY == -child.height.toFloat() && !upReach) {
            return false
        }
        return !downReach
    }
}