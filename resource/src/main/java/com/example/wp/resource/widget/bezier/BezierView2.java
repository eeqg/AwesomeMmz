package com.example.wp.resource.widget.bezier;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.wp.resource.utils.LogUtils;

import java.util.Arrays;

/**
 * Created by wp on 2019/2/22.
 */
public class BezierView2 extends View {
	private final float c = 0.551915024494f;
	/**
	 * 离开圆的阈值
	 */
	private float dis1 = 0.5f;
	/**
	 * 最大值的阈值
	 */
	private float dis2 = 0.8f;
	/**
	 * 到达下个圆的阈值
	 */
	private float dis3 = 0.9f;
	
	private float[] bezPos; //记录每一个圆心x轴的位置
	private float[] xPivotPos;  //根据圆心x轴+mRadius，划分成不同的区域 ,主要为了判断触摸x轴的位置
	private int currentPoint = 0;  //当前圆的位置
	private int nextPoint = 0; //圆要到达的下一个位置
	
	private float rRatio = 1;  //P2,3,4 x轴倍数
	private float lRatio = 1;  //P8,9,10倍数
	private float tbRatio = 1;  //y轴缩放倍数
	private float boundRatio = 0.55f;  //进入另一个圆的回弹效果
	
	private int width, height;
	private PointF p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
	private Paint mPaint;
	private Paint mCirclePaint;
	private Path bezierPath;
	private Matrix matrixBounceL;//将向右弹的动画改为向左
	
	private ValueAnimator backAnimator;
	private ValueAnimator rippleAnimator;
	private Xfermode clearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
	
	private boolean rippleAniming = false;
	private RectF rippleScope = new RectF();
	private Paint ripplePaint;
	private boolean isSliding = false;
	private float animatedValue;
	private float rippleAnimatedValue;
	private float interval;
	
	private int radius = 35;
	private int duration = 600;
	private int pointNum = 4;
	private int strokeColor = Color.GRAY;
	private int fillColor = Color.RED;
	
	public BezierView2(Context context) {
		this(context, null);
	}
	
	public BezierView2(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public BezierView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		initView();
	}
	
	private void initView() {
		mPaint = new Paint();
		mPaint.setColor(fillColor);
		mPaint.setStyle(Paint.Style.FILL);
		bezierPath = new Path();
		
		mCirclePaint = new Paint();
		mCirclePaint.setColor(strokeColor);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setStrokeWidth(2);
		
		ripplePaint = new Paint();
		ripplePaint.setColor(0xF0FFFFFF);
		ripplePaint.setStyle(Paint.Style.FILL);
		
		matrixBounceL = new Matrix();
		matrixBounceL.preScale(-1, 1);
		
		setPoints();
	}
	
	private void setPoints() {
		p0 = new PointF(0, radius);
		p1 = new PointF(radius * c, radius);
		p2 = new PointF(radius, radius * c);
		p3 = new PointF(radius, 0);
		p4 = new PointF(radius, -radius * c);
		p5 = new PointF(radius * c, -radius);
		p6 = new PointF(0, -radius);
		p7 = new PointF(-radius * c, -radius);
		p8 = new PointF(-radius, -radius * c);
		p9 = new PointF(-radius, 0);
		p10 = new PointF(-radius, radius * c);
		p11 = new PointF(-radius * c, radius);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
		initPointsCoordinate();
	}
	
	/**
	 * 圆心x坐标
	 */
	private void initPointsCoordinate() {
		bezPos = new float[pointNum];
		xPivotPos = new float[pointNum];
		interval = 1f * width / pointNum;
		// LogUtils.d("-----getScreenWidth : " + ScreenUtils.getScreenWidth(getContext()));
		// LogUtils.d("-----width : " + width);
		// LogUtils.d("-----interval : " + interval);
		for (int i = 0; i < pointNum; i++) {
			bezPos[i] = interval / 2 + interval * i;
			xPivotPos[i] = bezPos[i] + radius;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.translate(0, height / 2);
		// canvas.scale(1, -1);                 // 翻转Y轴
		bezierPath.reset();
		
		//绘制圆框
		for (int i = 0; i < pointNum; i++) {
			canvas.drawCircle(bezPos[i], 0, radius - 2, mCirclePaint);
		}
		
		//实现 触摸反馈
		// LogUtils.d("-----rippleAniming : " + rippleAniming);
		if (rippleAniming) {
			int count = canvas.saveLayer(rippleScope, ripplePaint, Canvas.ALL_SAVE_FLAG);
			canvas.drawCircle(bezPos[nextPoint], 0, rippleAnimatedValue, ripplePaint);//white circle
			
			ripplePaint.setXfermode(clearXfermode);//clear
			
			canvas.drawCircle(bezPos[nextPoint], 0, radius * 0.7f, ripplePaint);//transparent circle
			if (rippleAnimatedValue > radius) {
				canvas.drawCircle(bezPos[nextPoint], 0, (rippleAnimatedValue - radius) / 0.5f * 1.4f, ripplePaint);
			}
			
			ripplePaint.setXfermode(null);
			
			canvas.restoreToCount(count);
		}
		
		canvas.translate(bezPos[currentPoint], 0);
		
		//translation
		boolean isTrans = false;
		float transX = (nextPoint - currentPoint) * interval;
		if (dis1 <= animatedValue && animatedValue <= dis3) {
			isTrans = true;
			transX = transX * (animatedValue - dis1) / (dis3 - dis1);
		}
		if (dis3 < animatedValue && animatedValue <= 1) {
			isTrans = true;
		}
		if (isTrans) {
			canvas.translate(transX, 0);
		}
		
		// 绘制贝塞尔曲线
		drawBezier();
		canvas.drawPath(bezierPath, mPaint);
		//end
	}
	
	/**
	 * 通过 path 将向右弹射的动画绘制出来
	 * 如果要绘制向左的动画，只要设置path的transform(matrix)即可
	 */
	private void drawBezier() {
		if (0 < animatedValue && animatedValue <= dis1) {
			rRatio = 1f + animatedValue * 2;                 //  [1,2]
			lRatio = 1f;
			tbRatio = 1f;
		}
		if (dis1 < animatedValue && animatedValue <= dis2) {
			rRatio = 2 - range0Until1(dis1, dis2) * 0.5f;    //  [2,1.5]
			lRatio = 1 + range0Until1(dis1, dis2) * 0.5f;    // [1,1.5]
			tbRatio = 1 - range0Until1(dis1, dis2) / 3;      // [1 , 2/3]
		}
		if (dis2 < animatedValue && animatedValue <= dis3) {
			rRatio = 1.5f - range0Until1(dis2, dis3) * 0.5f;  //  [1.5,1]
			lRatio = 1.5f - range0Until1(dis2, dis3) * (1.5f - boundRatio);//反弹效果，进场 内弹boundRadio
			tbRatio = (range0Until1(dis2, dis3) + 2) / 3;    // [ 2/3,1]
		}
		if (dis3 < animatedValue && animatedValue <= 1f) {
			rRatio = 1;
			tbRatio = 1;
			lRatio = boundRatio + range0Until1(dis3, 1) * (1 - boundRatio);//反弹效果，饱和
		}
		if (animatedValue == 1 || animatedValue == 0) {  //防止极其粗暴的滑动
			rRatio = 1f;
			lRatio = 1f;
			tbRatio = 1f;
		}
		
		// bezierPath.reset();
		bezierPath.moveTo(p0.x, p0.y * tbRatio);
		bezierPath.cubicTo(p1.x, p1.y * tbRatio, p2.x * rRatio, p2.y, p3.x * rRatio, p3.y);
		bezierPath.cubicTo(p4.x * rRatio, p4.y, p5.x, p5.y * tbRatio, p6.x, p6.y * tbRatio);
		bezierPath.cubicTo(p7.x, p7.y * tbRatio, p8.x * lRatio, p8.y, p9.x * lRatio, p9.y);
		bezierPath.cubicTo(p10.x * lRatio, p10.y, p11.x, p11.y * tbRatio, p0.x, p0.y * tbRatio);
		bezierPath.close();
		
		if (nextPoint < currentPoint) {
			bezierPath.transform(matrixBounceL);
		}
	}
	
	private float range0Until1(float minValue, float maxValue) {
		return (animatedValue - minValue) / (maxValue - minValue);
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (y > height / 2 - radius && y < height / 2 + radius && !isSliding) {
					int index = -Arrays.binarySearch(xPivotPos, x) - 1;
					if (index >= 0 && index < pointNum && x > bezPos[index] - radius) {
						bounceTo(index, true);
					}
				}
				break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 回弹动画
	 */
	private void startBounceAnimation() {
		if (backAnimator == null) {
			backAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(duration);
			backAnimator.setInterpolator(new DecelerateInterpolator());
			backAnimator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
					isSliding = true;
				}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					isSliding = false;
					currentPoint = nextPoint;
				}
				
				@Override
				public void onAnimationCancel(Animator animation) {
					isSliding = false;
					currentPoint = nextPoint;
				}
				
				@Override
				public void onAnimationRepeat(Animator animation) {
				
				}
			});
			
			backAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					animatedValue = (float) animation.getAnimatedValue();
					invalidate();
				}
			});
		}
		if (backAnimator.isRunning()) {
			return;
		}
		backAnimator.start();
	}
	
	private void setTouchAnimation() {
		float offset = radius * 1.5f;
		//设置触摸动画范围
		rippleScope.set(bezPos[nextPoint] - offset, -offset, bezPos[nextPoint] + offset, offset);
		
		if (rippleAnimator == null) {
			rippleAnimator = ValueAnimator.ofFloat(0, radius * 1.5f).setDuration(300);
			rippleAnimator.setInterpolator(new DecelerateInterpolator());
			rippleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					rippleAnimatedValue = (float) animation.getAnimatedValue();
					LogUtils.d("-----rippleAnimatedValue : " + rippleAnimatedValue);
					if (rippleAnimatedValue == radius * 1.5f) {
						rippleAniming = false;
					}
				}
			});
			
			rippleAnimator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
					rippleAniming = true;
				}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					rippleAniming = false;
				}
				
				@Override
				public void onAnimationCancel(Animator animation) {
					rippleAniming = false;
				}
				
				@Override
				public void onAnimationRepeat(Animator animation) {
				
				}
			});
		}
		if (rippleAnimator.isRunning()) {
			return;
		}
		rippleAniming = true;
		rippleAnimator.start();
	}
	
	public BezierView2 setPointsNum(int num) {
		if (num < 0) {
			throw new IllegalArgumentException("argument @num must greater than 0.");
		}
		this.pointNum = num;
		return this;
	}
	
	public BezierView2 setRadius(int radius) {
		if (radius < 0) {
			throw new IllegalArgumentException("argument @num must greater than 0.");
		}
		this.radius = radius;
		setPoints();
		return this;
	}
	
	public BezierView2 setStrokeColor(@ColorInt int color) {
		this.strokeColor = color;
		mCirclePaint.setColor(color);
		return this;
	}
	
	public BezierView2 setFillColor(@ColorInt int color) {
		this.fillColor = color;
		mPaint.setColor(color);
		return this;
	}
	
	public void bounceNext() {
		bounceTo(currentPoint + 1);
	}
	
	public void bounceLast() {
		bounceTo(currentPoint - 1);
	}
	
	public void bounceTo(int position) {
		bounceTo(position, false);
	}
	
	private void bounceTo(int position, boolean showRipple) {
		if (position < 0) {
			position = 0;
		}
		if (position >= pointNum) {
			position = pointNum - 1;
		}
		if (position == currentPoint) {
			return;
		}
		if (position < 0 || position >= pointNum) {
			throw new IndexOutOfBoundsException("index out of bounds.");
		}
		nextPoint = position;
		startBounceAnimation();
		if (showRipple) {
			setTouchAnimation();
		}
	}
}
