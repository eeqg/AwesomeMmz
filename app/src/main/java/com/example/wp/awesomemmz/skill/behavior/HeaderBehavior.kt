package com.example.wp.awesomemmz.skill.behavior

import android.animation.ValueAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LogUtils


/**
 * Created by wp on 2020/3/27.
 */
class HeaderBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    private var isHeadHide = false
    private var isAnimating = false
    private val SCROOL_GAP_VALUE = 100
    private var childHeight = 0
    private val animationDuration = 400

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
        return if (target.id == R.id.scrollBody) {
            if (childHeight == 0) {
                childHeight = child.height;
            }
            true
        } else {
            false
        }
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (isAnimating) {
            return;
        }
        LogUtils.d("-----$dy")
        if (dy > SCROOL_GAP_VALUE && !isHeadHide) {
            hide(child, target)
        } else if (dy < -SCROOL_GAP_VALUE && isHeadHide) {
            show(child, target)
        }
    }

    private fun hide(child: View, target: View) {
        isHeadHide = true
        val valueAnimator = ValueAnimator()
        valueAnimator.setIntValues(0, childHeight)
        valueAnimator.duration = animationDuration.toLong()
        valueAnimator.addUpdateListener { animation ->
            if (child.bottom > 0) {
                val value = animation.animatedValue as Int
                isAnimating = value != childHeight
                child.layout(child.left, -value, child.right, -value + childHeight)
                target.layout(target.left, -value + childHeight, target.right, target.bottom)
            }
        }
        valueAnimator.start()
    }

    private fun show(child: View, target: View) {
        isHeadHide = false
        val valueAnimator = ValueAnimator()
        valueAnimator.setIntValues(0, childHeight)
        valueAnimator.duration = animationDuration.toLong()
        valueAnimator.addUpdateListener { animation ->
            if (child.bottom < childHeight) {
                val value = animation.animatedValue as Int
                isAnimating = value != childHeight
                child.layout(child.left, value - childHeight, child.right, value)
                target.layout(target.left, value, target.right, target.bottom)
            }
        }
        valueAnimator.start()
    }
}