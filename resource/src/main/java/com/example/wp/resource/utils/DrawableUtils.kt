package com.example.wp.resource.utils

import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorInt

/**
 * Created by wp on 2020/11/5.
 */
object DrawableUtils {
    fun getGradientDrawable(shape: Int,
                            @ColorInt color: Int,
                            strokeWidth: Int,
                            @ColorInt strokeColor: Int,
                            radius: Float,
                            alpha: Int,
                            size: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        if (shape == 0) {
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            gradientDrawable.setColor(color)
            if (strokeWidth > 0) {
                gradientDrawable.setStroke(strokeWidth, strokeColor)
            }
            if (size > 0) {
                gradientDrawable.setSize(size, size)
            }
            if (radius > 0) {
                gradientDrawable.cornerRadius = radius
            }
        } else if (shape == 1) {
            gradientDrawable.shape = GradientDrawable.OVAL
            gradientDrawable.setColor(color)
            if (strokeWidth > 0) {
                gradientDrawable.setStroke(strokeWidth, strokeColor)
            }
            if (size > 0) {
                gradientDrawable.setSize(size, size)
            }
        } else if (shape == 2) {
            gradientDrawable.shape = GradientDrawable.LINE
            gradientDrawable.setStroke(strokeWidth, strokeColor)
        } else if (shape == 3) {
            gradientDrawable.shape = GradientDrawable.RING
        }
        if (alpha > 0) {
            gradientDrawable.alpha = alpha
        }
        return gradientDrawable
    }

    fun getGradientDrawable(colors: IntArray, size: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            this.colors = colors
            gradientType = GradientDrawable.LINEAR_GRADIENT
            orientation = GradientDrawable.Orientation.LEFT_RIGHT //orientation
            if (size > 0) {
                setSize(size, size)
            }
        }
    }
}