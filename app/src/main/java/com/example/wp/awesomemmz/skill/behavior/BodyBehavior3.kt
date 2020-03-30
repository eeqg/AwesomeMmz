package com.example.wp.awesomemmz.skill.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LogUtils

/**
 * Created by wp on 2020/3/27.
 */
class BodyBehavior3(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency.id == R.id.header
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        var y = dependency.height + dependency.translationY
        LogUtils.d("BodyBehavior3-----y = $y")

        if (y < 0) y = 0f
        child.y = y
        return true
    }
}