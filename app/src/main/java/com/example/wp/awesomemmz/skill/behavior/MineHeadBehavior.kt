package com.example.wp.awesomemmz.skill.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ScrollingView
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.example.wp.awesomemmz.R
import com.example.wp.resource.common.ext.dp2px
import com.example.wp.resource.common.ext.leftMargin
import com.example.wp.resource.utils.LogUtils
import kotlin.math.abs

/**
 * Created by wp on 2020/4/14.
 */
class MineHeadBehavior(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {
    private var avatar: View? = null
    private var tvTitle: View? = null
    private var tvSubtitle: View? = null

    private val minHeight = context.dp2px(68f)
    private val minAvatarHeight = context.dp2px(44f)
    private var maxAvatarHeight = 0
    private var avatarScale = 0f
    private var maxTitleTranslateX = 0f

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)

        if (avatar == null) {
            avatar = child.findViewById(R.id.avatar)
            tvTitle = child.findViewById(R.id.tvTitle)
            tvSubtitle = child.findViewById(R.id.tvSubtitle)

            maxAvatarHeight = avatar!!.height
            avatarScale = 1f * minAvatarHeight / maxAvatarHeight - 0.1f
            maxTitleTranslateX =
                tvTitle!!.x - minAvatarHeight - child.paddingLeft - tvTitle!!.leftMargin
        }

        val maxTranslationY = child.height - minHeight
//            LogUtils.d("--begin-->-----dya = $dy")
//            LogUtils.d("-----child.translationY = ${child.translationY}")

        var canScrollY = false
//                LogUtils.d("-----target: $target")
//                LogUtils.d("-----isScrollView: ${target is ScrollingView}")
//                LogUtils.d("-----target.scrollY = ${target.scrollY}")
        if (target is ScrollingView) {
//                LogUtils.d("-----target.computeVerticalScrollOffset = ${(target as ScrollingView).computeVerticalScrollOffset()}")
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

//            LogUtils.d("------canScrollY: $canScrollY")
        if (canScrollY) {
            val offsetDy = dy * 0.5f

            consumed[1] = dy

//                LogUtils.d("--begin-->-----offsetDy = $offsetDy")
            var finalY = child.translationY - offsetDy
            if (finalY < -maxTranslationY) {
                finalY = -maxTranslationY.toFloat()
            } else if (finalY > 0) {
                finalY = 0f
            }
            LogUtils.d("-----finalY = $finalY")
            child.translationY = finalY

            val fraction = abs(finalY / maxTranslationY)
            LogUtils.d("-----fraction = $fraction")
//            val color: Int = ColorUtil.changeAlpha(Color.WHITE, fraction)
//            it.setBackgroundColor(color)

            //avatar
            avatar?.let {
                it.scaleX = 1 - fraction * avatarScale
                it.scaleY = 1 - fraction * avatarScale
                val at = fraction * ((maxAvatarHeight - minAvatarHeight) / 2f)
                it.translationY = at
                it.translationX = -at
            }

            //title
            tvTitle?.let {
                it.translationX = -maxTitleTranslateX * fraction
                it.translationY = (maxTranslationY - it.height / 2) * fraction
            }
            tvSubtitle?.let {
                it.translationX = -maxTitleTranslateX * fraction
//                it.translationY = -fraction
                it.alpha = 1 - fraction
            }

        }
    }
}