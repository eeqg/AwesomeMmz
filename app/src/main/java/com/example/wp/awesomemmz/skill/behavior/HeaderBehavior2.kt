package com.example.wp.awesomemmz.skill.behavior

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ScrollingView
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LogUtils
import kotlin.math.abs

/**
 * Created by wp on 2020/3/27.
 */
class HeaderBehavior2(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var llHandleBar: View? = null
    private var llLogo: View? = null
    private var tvSearch: View? = null
    private var maxSearchWidth = 0
    private var desX = 0

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        //    axes 表示方向 有一下两种值
        //  *     ViewCompat.SCROLL_AXIS_HORIZONTAL 横向滑动
        //  *     ViewCompat.SCROLL_AXIS_VERTICAL 纵向滑动
        // public boolean startNestedScroll(int axes)
//        LogUtils.d("-----target.id = ${target.id}")
//        return target.id == R.id.body
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)

        if (llHandleBar == null) {
            llHandleBar = child.findViewById(R.id.llHandleBar)
            llLogo = child.findViewById(R.id.llLogo)
            tvSearch = child.findViewById(R.id.tvSearch)
            maxSearchWidth = tvSearch!!.width
            desX = llHandleBar!!.width + child.paddingRight + 20
        }

        llHandleBar?.let {
            val maxTranslationY = child.height - it.height
            LogUtils.d("--begin-->-----dya = $dy")
            LogUtils.d("-----child.translationY = ${child.translationY}")

            var canScrollY = false
//                LogUtils.d("-----target: $target")
//                LogUtils.d("-----isScrollView: ${target is ScrollingView}")
//                LogUtils.d("-----target.scrollY = ${target.scrollY}")
            if (target is ScrollingView) {
                LogUtils.d("-----target.computeVerticalScrollOffset = ${(target as ScrollingView).computeVerticalScrollOffset()}")
                if ((target as ScrollingView).computeVerticalScrollOffset() == 0 && dy < 0) {
                    canScrollY = true
                } else {
                    if (abs(child.translationY) < maxTranslationY) {
                        canScrollY = true
                    }
                }
            } else {
                canScrollY = true
            }

            LogUtils.d("------canScrollY: $canScrollY")
            if (canScrollY) {
                // 让CoordinatorLayout消费滑动事件
                //consumed[0]为水平方向应该消耗的距离，consumed[1]为垂直方向应该消耗的距离
//                consumed[1] = dy

                val offsetDy = dy * .5f

                consumed[1] = offsetDy.toInt()

                LogUtils.d("--begin-->-----offsetDy = $offsetDy")
                var finalY = child.translationY - offsetDy
                if (finalY < -maxTranslationY) {
                    finalY = -maxTranslationY.toFloat()
                } else if (finalY > 0) {
                    finalY = 0f
                }
                LogUtils.d("-----finalY = $finalY")
                child.translationY = finalY
                it.translationY = -finalY

                val fraction = abs(finalY / maxTranslationY)
                LogUtils.d("-----fraction = $fraction")
//            val color: Int = ColorUtil.changeAlpha(Color.WHITE, fraction)
//            it.setBackgroundColor(color)

                //logo
                llLogo?.alpha = 1 - fraction
                llLogo?.translationY = -finalY

                //search
                val layoutParams = tvSearch!!.layoutParams as ConstraintLayout.LayoutParams
                val newWidth = maxSearchWidth - (desX * fraction).toInt() - layoutParams.rightMargin
                LogUtils.d("-----newWidth = $newWidth")
//            tvSearch!!.newWidth = newWidth
                layoutParams.width = newWidth
                tvSearch!!.layoutParams = layoutParams
                tvSearch!!.translationY = -20 * fraction

            }
        }
    }
}