package com.example.wp.awesomemmz.skill.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by wp on 2020/11/2.
 */
class LoadingView : LinearLayout {
    private var shapeView: GeoShapeView
    private var shadowView: ImageView
    private var textView: TextView

    private var mShapeSize = 60
    private var mDistance = 160
    private var mGapValue = 16
    private var mDuration = 600L
    private var mText = "loading..."

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setLoadingText(text: String): LoadingView {
        this.mText = text
        return this
    }

    fun setDuration(duration: Long): LoadingView {
        this.mDuration = duration
        return this
    }

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        setPadding(20, 20, 20, 20)
        shapeView = GeoShapeView(context)
        addView(shapeView, mShapeSize, mShapeSize)
        shadowView = ImageView(context).apply {
            setImageDrawable(getShadow())
        }
        val sLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            width = mShapeSize
            height = mShapeSize / 4
            topMargin = mDistance
        }
        addView(shadowView, sLayoutParams)
        textView = TextView(context).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            setTextColor(Color.parseColor("#929399"))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            text = mText
        }
        val tLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            topMargin = mGapValue
        }
        addView(textView, tLayoutParams)

        startFall()
    }

    private fun startFall() {
        val shapeAnim = ObjectAnimator.ofFloat(shapeView, "translationY", 0f, mDistance.toFloat())
                .apply {
                    interpolator = AccelerateInterpolator()
                }
        val shadowAnim = ObjectAnimator.ofFloat(shadowView, "scaleX", .33f, 1f)
        val rotaAnim = ObjectAnimator.ofFloat(shapeView, "rotation", 0f, 180f)
        val animationSet = AnimatorSet().apply {
            duration = mDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    shapeView.changeShape()
                    startUp()
                }
            })

            play(shapeAnim).with(shadowAnim).with(rotaAnim)
        }

        animationSet.start()
    }

    private fun startUp() {
        val shapeAnim = ObjectAnimator.ofFloat(shapeView, "translationY", mDistance.toFloat(), 0f)
                .apply {
                    interpolator = DecelerateInterpolator()
                }
        val shadowAnim = ObjectAnimator.ofFloat(shadowView, "scaleX", 1f, .33f)
        val animationSet = AnimatorSet().apply {
            duration = mDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    startFall()
                }
            })

            play(shapeAnim).with(shadowAnim)
        }

        animationSet.start()
    }

    private fun getShadow(): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.parseColor("#d0d3d9"))
        }
    }
}