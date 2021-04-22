package com.example.wp.awesomemmz.skill

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LogUtil
import kotlinx.android.synthetic.main.activity_goods_detail.*


class GoodsDetailActivity : AppCompatActivity() {
    var scaleAnim: ScaleAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        addCart.setOnClickListener { add2Cart() }
    }

    private fun add2Cart() {
//        scaleAnim = ScaleAnimation(1f, 1.2f, 1f, 1.2f, flCart.width / 2f, flCart.height / 2f)
//                .apply {
//                    duration = 230
//                }

        //create animate view
        val animateView = ImageView(this).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            setImageDrawable(ivPicture.drawable)
        }

        //add view
        val offset = 0
        val layoutParams = FrameLayout.LayoutParams(120, 120)
        layoutParams.leftMargin = ivPicture.width / 2 + offset
        layoutParams.topMargin = ivPicture.height / 2 - offset
        val rootView: ViewGroup = window.decorView as ViewGroup
        rootView.addView(animateView, layoutParams)

        val starLoc = IntArray(2)
        //ivPicture.getLocationInWindow(starLoc)
        animateView.getLocationInWindow(starLoc)
        LogUtil.d("-----starLoc : [${starLoc[0]}, ${starLoc[1]}]")
        val startX = starLoc[0]
        val startY = starLoc[1]
        LogUtil.d("-----startX = $startX, startY = $startY")

        val endLoc = IntArray(2)
        flCart.getLocationInWindow(endLoc)
        LogUtil.d("-----endLoc : [${endLoc[0]}, ${endLoc[1]}]")
        val endX = endLoc[0]
        val endY = endLoc[1]
        LogUtil.d("-----endX = $endX, endY = $endY")

        val transitionX = (ivPicture.width / 2)
        val transitionY = endY - ivPicture.height / 2 - 56//56: status bar height
        LogUtil.d("-----transitionX = $transitionX, transitionY = $transitionY")

        val alphaProper = PropertyValuesHolder.ofFloat("alpha", 1f, .3f)
        val scaleXProper = PropertyValuesHolder.ofFloat("scaleX", 1f, .1f)
        val scaleYProper = PropertyValuesHolder.ofFloat("scaleY", 1f, .1f)
        LogUtil.d("-----translationX = " + animateView.translationX)
        LogUtil.d("-----translationY = " + animateView.translationY)
        val translationXProper = PropertyValuesHolder.ofFloat("translationX", animateView.translationX, -transitionX.toFloat())
        val translationYProper = PropertyValuesHolder.ofFloat("translationY", animateView.translationY, transitionY.toFloat())
        val animator = ObjectAnimator.ofPropertyValuesHolder(animateView, alphaProper, scaleXProper, scaleYProper, translationXProper, translationYProper)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 800
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                animateView.visibility = View.GONE
                rootView.removeView(animateView)
                //flCart.startAnimation(scaleAnim)
                startScaleAnim()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        animator.start()
    }

    private fun startScaleAnim(){
        val scaleXProper = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f)
        val scaleYProper = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f)
        ObjectAnimator.ofPropertyValuesHolder(flCart, scaleXProper, scaleYProper).run {
            duration = 260
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }
}