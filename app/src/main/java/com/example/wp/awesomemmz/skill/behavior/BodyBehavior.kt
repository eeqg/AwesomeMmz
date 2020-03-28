package com.example.wp.awesomemmz.skill.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.NestedScrollingChild
import android.support.v4.view.NestedScrollingChildHelper
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Created by wp on 2020/3/27.
 */
class BodyBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs), NestedScrollingChild {
    private var childHelper: NestedScrollingChildHelper? = null
    private var ox = 0f
    private var oy = 0f
    private val consumed = IntArray(2)
    private val offsetInWindow = IntArray(2)

    override fun onTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        if (childHelper == null) {
            childHelper = NestedScrollingChildHelper(child)
            isNestedScrollingEnabled = true
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                ox = ev.x
                oy = ev.y
                if (oy < child.top || oy > child.bottom || ox < child.left || ox > child.right) {
                    return true
                }
                //开始滑动
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
            }
            MotionEvent.ACTION_MOVE -> {
                val clampedX = ev.x
                val clampedY = ev.y
                val dx = (clampedX - ox).toInt()
                val dy = (clampedY - oy).toInt()
                if (Math.abs(dy) > Math.abs(dx)) { //分发触屏事件给parent处理
                    dispatchNestedPreScroll(dx, -dy, consumed, offsetInWindow)
                }
            }
            MotionEvent.ACTION_UP -> stopNestedScroll()
        }
        return true
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        childHelper?.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return childHelper?.isNestedScrollingEnabled ?: false
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return childHelper?.startNestedScroll(axes) ?: false
    }

    override fun stopNestedScroll() {
        childHelper?.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return childHelper?.hasNestedScrollingParent() ?: false
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {
        return childHelper?.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
                ?: false
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean {
        return childHelper?.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow) ?: false
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return childHelper?.dispatchNestedFling(velocityX, velocityY, consumed) ?: false
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return childHelper?.dispatchNestedPreFling(velocityX, velocityY) ?: false
    }
}