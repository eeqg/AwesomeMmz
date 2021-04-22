package com.example.wp.awesomemmz.skill

import android.animation.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LogUtil
import com.example.wp.resource.utils.LogUtils
import kotlinx.android.synthetic.main.activity_add_to_cart.*

class AddToCartActivity : AppCompatActivity() {
    private val animView: ImageView by lazy {
        ImageView(this).apply {
            setImageResource(R.mipmap.ic_add)
        }
    }
    private val mRootView: ViewGroup by lazy {
        window.decorView as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@AddToCartActivity)
            adapter = ListAdapter(this@AddToCartActivity)
        }
    }

    fun add2Cart(startLoc: IntArray) {
        mRootView.addView(animView, ViewGroup.LayoutParams(45, 45))

        //开始位置
        LogUtil.d("-----startLoc: ${startLoc[0]}, ${startLoc[1]}")
        val startPosition = Point(startLoc[0], startLoc[1])
        LogUtils.d("-----startPosition: $startPosition")

        //结束位置
        val endLoc = IntArray(2)
        flCart.getLocationInWindow(endLoc)
        LogUtil.d("-----endLoc: ${endLoc[0]}, ${endLoc[1]}")
        val endPosition = Point(endLoc[0], endLoc[1])
        LogUtils.d("-----endPosition: $endPosition")

        //控制点位置(自己调整, 改变曲线轨迹)
        val controlPoint = Point((startPosition.x + endPosition.x) / 2 - 100, startPosition.y - 300)

        //animator
        ValueAnimator.ofObject(Bizier2Evaluator(controlPoint), startPosition, endPosition).apply {
            duration = 500
            addUpdateListener {
                val curPoint: Point = it.animatedValue as Point
                animView.x = curPoint.x.toFloat()
                animView.y = curPoint.y.toFloat()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mRootView.removeView(animView)

                    startScaleAnim()
                }
            })

            start()
        }
    }

    //购物车缩放动画
    private fun startScaleAnim() {
        val scaleXProper = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f)
        val scaleYProper = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f)
        ObjectAnimator.ofPropertyValuesHolder(flCart, scaleXProper, scaleYProper).run {
            duration = 260
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    inner class ListAdapter(private val mContext: AddToCartActivity) : RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
            val root = View.inflate(mContext, R.layout.item_add_cart_list, null)
            return ItemViewHolder(root)
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {
            holder.ivAdd?.setOnClickListener {
                LogUtil.d("-----add")
                val loc = IntArray(2)
                it.getLocationInWindow(loc)
                mContext.add2Cart(loc)
            }
        }

        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var ivAdd: ImageView? = null

            init {
                ivAdd = itemView.findViewById(R.id.ivAdd)
            }
        }
    }
}