package com.example.wp.awesomemmz.skill.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LogUtils

/**
 * Created by wp on 2020/3/27.
 */
class HeaderBehavior2(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return if (target.id == R.id.body) {
//            if (childHeight == 0) {
//                childHeight = child.height;
//            }
            true
        } else {
            false
        }
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        var finalY = child.translationY - dy
        if (finalY < -child.height / 2) {
            finalY = (-child.height) / 2F
        } else if (finalY > 0) {
            finalY = 0f
        }
        LogUtils.d("-----finalY = $finalY")
        child.translationY = finalY

        val llHandleBar = child.findViewById<View>(R.id.llHandleBar)
        llHandleBar.translationY = -finalY

//        consumed[1] = dy
    }
}