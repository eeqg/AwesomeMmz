package com.example.wp.awesomemmz.skill

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.view.View
import com.example.wp.resource.common.ext.dp2px

/**
 * Created by wp on 2019/9/26.
 *
 * 通用悬浮ItemDecoration
 */
class FloatingItemDecoration(val context: Context, val config: Config) : ItemDecoration() {
    private val headerTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textBounds: Rect
    private val headerHeight: Int
    private val headerBgPaint: Paint = Paint()
    private var offsetLeft: Int = 0
    
    data class Config(
        val textColor: String,
        val textSize: Float,
        val height: Int,
        val bgColor: String = "#FFFFFF",
        val left: Int = 0
    )
    
    init {
        headerTextPaint.color = Color.parseColor(config.textColor)
        headerTextPaint.textSize = context.dp2px(config.textSize).toFloat()
        textBounds = Rect()
        headerHeight = context.dp2px(config.height.toFloat())
        headerBgPaint.color = Color.parseColor(config.bgColor)
        offsetLeft = config.left
    }
    
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.adapter is IFloatingAdapter) {
            val adapter = parent.adapter as IFloatingAdapter?
            val position = parent.getChildLayoutPosition(view)
            if (adapter!!.isItemHeader(position)) {
                outRect.top = headerHeight
            }
        }
    }
    
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //画title
        if (parent.adapter is IFloatingAdapter) {
            val adapter = parent.adapter as IFloatingAdapter?
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val childView = parent.getChildAt(i)
                val position = parent.getChildLayoutPosition(childView)
                val left = parent.paddingLeft
                val right = parent.width - parent.paddingRight
                if (adapter!!.isItemHeader(position)) {
                    //画背景
                    c.drawRect(left.toFloat(),
                        (childView.top - headerHeight).toFloat(),
                        right.toFloat(),
                        childView.top.toFloat(),
                        headerBgPaint)
                    //画title
                    val text = adapter.getShowText(position)
                    headerTextPaint.getTextBounds(text, 0, text.length, textBounds) //计算text显示区域
                    //左下角的坐标??--(x默认是字符串的左边在屏幕的位置，y是指定这个字符baseline在屏幕上的位置。)
                    val textX =
                        if (offsetLeft > 0) offsetLeft else (parent.width - textBounds.width()) / 2
                    c.drawText(text,
                        textX.toFloat(),
                        (childView.top - headerHeight + headerHeight / 2 + textBounds.height() / 2).toFloat(),
                        headerTextPaint)
                }
            }
        }
    }
    
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //画悬浮title
        if (parent.adapter is IFloatingAdapter) {
            val adapter = parent.adapter as IFloatingAdapter?
            val position =
                (parent.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
            val view = parent.findViewHolderForAdapterPosition(position)!!.itemView
            val isHeader = adapter!!.isItemHeader(position + 1)
            val top = parent.paddingTop
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            val text = adapter.getShowText(position)
            headerTextPaint.getTextBounds(text, 0, text.length, textBounds) //计算text显示区域
            val textX = if (offsetLeft > 0) offsetLeft else (parent.width - textBounds.width()) / 2
            if (isHeader) {
                val bottom = Math.min(headerHeight, view.bottom)
                c.drawRect(left.toFloat(),
                    (top + view.top - headerHeight).toFloat(),
                    right.toFloat(),
                    (top + bottom).toFloat(),
                    headerBgPaint)
                // c.drawText(text, left + offsetLeft, top + headerHeight / 2 + textBounds.height() / 2 - (headerHeight - bottom), headerTextPaint);
                c.drawText(text,
                    (left + textX).toFloat(),
                    (top + bottom - (headerHeight - textBounds.height()) / 2).toFloat(),
                    headerTextPaint)
            } else {
                c.drawRect(left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    (top + headerHeight).toFloat(),
                    headerBgPaint)
                c.drawText(text,
                    (left + textX).toFloat(),
                    (top + headerHeight / 2 + textBounds.height() / 2).toFloat(),
                    headerTextPaint)
            }
            c.save()
        }
    }
}