package com.example.wp.awesomemmz.skill.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.widget.Switch
import kotlin.math.min

/**
 * Created by wp on 2020/11/2.
 */
internal class GeoShapeView : View {
    private val mPaint: Paint = Paint()
    private val mPath: Path = Path()
    private var mCurrentShape: Shape = Shape.CIRCLE

    enum class Shape {
        CIRCLE, RECT, RECTANGLE
    }

    constructor(context: Context?) : super(context) {}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    init {
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        mPaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mCurrentShape) {
            Shape.RECT -> {
                mPaint.color = Color.parseColor("#FF99CC")
                canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
            }
            Shape.CIRCLE -> {
                val radius = min(width, height) / 2f
                mPaint.color = Color.parseColor("#99FFCC")
                canvas.drawCircle(width / 2f, height / 2f, radius, mPaint)
            }
            Shape.RECTANGLE -> {
                mPaint.color = Color.parseColor("#99CCFF")
                mPath.run {
                    reset()
                    moveTo(width / 2f, 0f)
                    lineTo(0f, height.toFloat())
                    lineTo(width.toFloat(), height.toFloat())
                    close()
                }
                canvas.drawPath(mPath, mPaint)
            }
        }
    }

    fun changeShape() {
        when {
            mCurrentShape === Shape.RECT -> {
                mCurrentShape = Shape.CIRCLE
            }
            mCurrentShape === Shape.CIRCLE -> {
                mCurrentShape = Shape.RECTANGLE
            }
            mCurrentShape === Shape.RECTANGLE -> {
                mCurrentShape = Shape.RECT
            }
        }

        invalidate()
    }

    fun getCurrentShape(): Shape {
        return mCurrentShape
    }
}