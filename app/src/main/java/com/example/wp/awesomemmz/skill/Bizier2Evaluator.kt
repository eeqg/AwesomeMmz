package com.example.wp.awesomemmz.skill

import android.animation.TypeEvaluator

/**
 * @description 贝塞尔曲线（二阶抛物线）
 * @author wp
 * @date   2021/4/22 17:35
 */
class Bizier2Evaluator(private val controlPoint: Point) : TypeEvaluator<Point> {

    override fun evaluate(fraction: Float, startValue: Point?, endValue: Point?): Point {
        //二阶贝塞尔曲线公式: B(t) = (1-t)^2 * P0 + 2t(1-t) * P1 + t^2 * P2 (P0-起始点, P1-控制点, P2-终点)
        val x = ((1 - fraction) * (1 - fraction) * startValue!!.x + 2 * fraction * (1 - fraction) * controlPoint.x + fraction * fraction * endValue!!.x).toInt()
        val y = ((1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * controlPoint.y + fraction * fraction * endValue.y).toInt()
        return Point(x, y)
    }
}