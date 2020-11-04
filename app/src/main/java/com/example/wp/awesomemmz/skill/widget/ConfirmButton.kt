package com.example.wp.awesomemmz.skill.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.wp.resource.utils.LogUtils

/**
 * Created by wp on 2020/11/3.
 */
class ConfirmButton : View {
    private val TAG = "ConfirmButton"
    private val DURATION: Long = 800

    private val mPaint = Paint()
    private val textPaint = Paint()
    private val rectF = RectF()
    private val textRect = RectF()
    private val okPaint = Paint()
    private val okPath = Path()
    private val animSet = AnimatorSet()

    private var radius: Float = 0f
    private var translateX = 0f
    private var drawOk = false

    private var bgColor = Color.WHITE
    private var textColor: Int = 0xff328899.toInt()
    private var textSize: Float = 48f
    private var text = "confirm"
    private var confirmListener: OnConfirmListener? = null
    private var distanceY = 300f

    fun setBgColor(@ColorInt color: Int) {
        this.bgColor = color
        invalidate()
    }

    fun setText(text: String) {
        this.text = text
        invalidate()
    }

    fun setTextSize(px: Float) {
        this.textSize = px
        invalidate()
    }

    fun setTextColor(@ColorInt color: Int) {
        this.textColor = color
        invalidate()
    }

    fun setDistance(distance: Float) {
        this.distanceY = distance
    }

    fun setOnConfirmListener(listener: OnConfirmListener?) {
        confirmListener = listener
    }

    fun reset() {
        if(!drawOk) return
        drawOk = false
        radius = 0f
        translateX = 0f
        translationY += distanceY
        textPaint.alpha = 255
        invalidate()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        mPaint.run {
            isAntiAlias = true
            color = bgColor
            strokeWidth = 4f
            style = Paint.Style.FILL
        }
        textPaint.run {
            isAntiAlias = true
            color = textColor
            textAlign = Paint.Align.CENTER
            textSize = this@ConfirmButton.textSize
        }
        okPaint.run {
            isAntiAlias = true
            color = 0xff232323.toInt()
            strokeWidth = 6f
            style = Paint.Style.STROKE
        }

        animSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                confirmListener?.onEnd()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                confirmListener?.onStart()
            }
        })
        setOnClickListener { start() }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        LogUtils.d(TAG, "----width = $width, height = $height")
        rectF.set(translateX, 0f, width.toFloat() - translateX, height.toFloat())
        canvas.drawRoundRect(rectF, radius, radius, mPaint)

        drawText(canvas)

        if (drawOk) {
            canvas.drawPath(okPath, okPaint)
        }
    }

    private fun drawText(canvas: Canvas) {
        textRect.set(0f, 0f, width.toFloat(), height.toFloat())
        val fontMetricsInt = textPaint.fontMetricsInt
        val baseLine = (textRect.top + textRect.bottom - fontMetricsInt.ascent - fontMetricsInt.descent) / 2
        val baseLine2 = (textRect.top + textRect.bottom - fontMetricsInt.top - fontMetricsInt.bottom) / 2
        LogUtils.d(TAG, "----baseLine = $baseLine, baseLine2 = $baseLine2")
        canvas.drawText(text, textRect.centerX(), baseLine, textPaint)
    }

    private fun start() {
        val radiusAnim = ValueAnimator.ofFloat(0f, height / 2f).apply {
            duration = DURATION
            addUpdateListener {
                radius = it.animatedValue as Float
                invalidate()
            }
        }

        val distanceX = (width - height) / 2f
        val shrinkAnim = ValueAnimator.ofFloat(0f, distanceX).apply {
            duration = DURATION
            addUpdateListener {
                translateX = it.animatedValue as Float
                textPaint.alpha = (255 * (1 - translateX / distanceX)).toInt()
                invalidate()
            }
        }

        val translateAnim = ObjectAnimator.ofFloat(this, "translationY", 0f, -distanceY)
                .apply {
                    duration = DURATION
                    interpolator = AccelerateDecelerateInterpolator()
                    invalidate()
                }

        //对勾的路径
        okPath.moveTo(distanceX + height * 0.3f, height * 0.3f)
        okPath.lineTo(distanceX + height * .5f, height * .6f)
        okPath.lineTo(distanceX + height * 1.15f, 0f)
        val pathLength: Float = PathMeasure(okPath, false).length
        val okAnim = ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 400
            addUpdateListener {
                drawOk = true
                val phase: Float = (it.animatedValue as Float) * pathLength
                val effect = DashPathEffect(floatArrayOf(pathLength, pathLength), phase)
                okPaint.pathEffect = effect
                invalidate()
            }
        }

        animSet.play(translateAnim).before(okAnim).after(shrinkAnim).after(radiusAnim)
        animSet.start()
    }

    interface OnConfirmListener {
        fun onStart()
        fun onEnd()
    }
}