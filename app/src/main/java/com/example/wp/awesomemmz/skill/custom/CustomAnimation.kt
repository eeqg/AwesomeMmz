package com.example.wp.awesomemmz.skill.custom

import android.graphics.Matrix
import android.view.animation.*
import com.example.wp.resource.utils.LogUtils


/**
 * Created by wp on 2020/11/5.
 */
class CustomAnimation : Animation() {
    private var centerX = 0
    private var centerY: Int = 0

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        // 初始化中点坐标
        centerX = width / 2
        centerY = height / 2
        //动画持续的时间
        duration = 2000
        //设置动画结束后是否保留在终止的位置
        fillAfter = true
        /**
         * 设置动画的加速度曲线，传入参数Interpolator的简介子类有：
         * AnticipateOvershootInterpolator 改变开始向后然后向前射出超出屏幕
         * AccelerateDecelerateInterpolator 变化的速度开始和结束缓慢但中间加速穿过
         * AccelerateInterpolator 变化的速度开始慢慢,然后加速
         * AnticipateInterpolator 改变开始向后然后向前射出
         * BounceInterpolator 变化到最后时反弹
         * CycleInterpolator 重复动画指定数量的周期CycleInterpolator(float cycles)需传入周期数
         * DecelerateInterpolator 开始变化的速度很快,然后开始减速
         * LinearInterpolator 线性变化
         * OvershootInterpolator 射出向前,超过屏幕然后回来
         *
         * pS->文字功底欠佳，描述的不是很准确哈，见谅，见谅o(╯□╰)o具体效果还是要自己动手试试
         */
        interpolator = OvershootInterpolator()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val matrix: Matrix = t!!.matrix
        //通过interpolatedTime设置矩阵缩放
        matrix.setScale(interpolatedTime, interpolatedTime)
        LogUtils.d("-----$interpolatedTime")
        matrix.preTranslate((-centerX).toFloat(), (-centerY).toFloat())
        matrix.postTranslate(centerX.toFloat(), centerY.toFloat())
    }
}