package com.example.wp.awesomemmz.skill

import android.animation.TypeEvaluator

/**
 * @description
 * @author wp
 * @date   2021/4/22 17:35
 */
class Bizier2Evaluator(private val controlPoint: Point) : TypeEvaluator<Point> {

    override fun evaluate(fraction: Float, startValue: Point?, endValue: Point?): Point {
        val x = ((1 - fraction) * (1 - fraction) * startValue!!.x + 2 * fraction * (1 - fraction) * controlPoint.x + fraction * fraction * endValue!!.x).toInt()
        val y = ((1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * controlPoint.y + fraction * fraction * endValue.y).toInt()
        return Point(x, y)
    }
}