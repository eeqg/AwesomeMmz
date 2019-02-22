package com.example.wp.resource.widget.bezier;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by wp on 2019/2/22.
 */
public class BezierView1 extends View {
	private final float c = 0.551915024494f;
	private int centerX, centerY;
	private PointF p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
	private Paint mPaint;
	private Path bezierPath;
	
	private ValueAnimator backAnimator;
	
	private int radius = 50;
	
	public BezierView1(Context context) {
		this(context, null);
	}
	
	public BezierView1(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public BezierView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		initView();
	}
	
	private void initView() {
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
		
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeWidth(2);
		bezierPath = new Path();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		centerX = w / 2;
		centerY = h / 2;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawCoordinateSystem(canvas);
		canvas.translate(centerX, centerY); // 将坐标系移动到画布中央
		canvas.scale(1, -1);                 // 翻转Y轴
		
		// 绘制贝塞尔曲线
		bezierPath.reset();
		bezierPath.moveTo(p0.x, p0.y);
		bezierPath.cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
		bezierPath.cubicTo(p4.x, p4.y, p5.x, p5.y, p6.x, p6.y);
		bezierPath.cubicTo(p7.x, p7.y, p8.x, p8.y, p9.x, p9.y);
		bezierPath.cubicTo(p10.x, p10.y, p11.x, p11.y, p0.x, p0.y);
		bezierPath.close();
		canvas.drawPath(bezierPath, mPaint);
		//end
	}
	
	// 绘制坐标系
	private void drawCoordinateSystem(Canvas canvas) {
		canvas.save();                      // 绘制做坐标系
		
		canvas.translate(centerX, centerY); // 将坐标系移动到画布中央
		canvas.scale(1, -1);                 // 翻转Y轴
		
		Paint paint = new Paint();
		paint.setColor(Color.parseColor("#E5E5E5"));
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.STROKE);
		
		int x = 350, y = 650;
		
		canvas.drawLine(0, -y, 0, y, paint);
		canvas.drawLine(-x, 0, x, 0, paint);
		
		canvas.drawLine(0, y, 10, y - 20, paint);
		canvas.drawLine(0, y, -10, y - 20, paint);
		canvas.drawLine(x, 0, x - 20, 10, paint);
		canvas.drawLine(x, 0, x - 20, -10, paint);
		
		paint.setTextSize(20);
		canvas.drawText("x", x - 10, -20, paint);
		canvas.scale(1, -1);
		canvas.drawText("y", 20, 20 - y, paint);
		
		canvas.restore();
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX() - centerX;
		float y = event.getY() - centerY;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
				break;
			case MotionEvent.ACTION_MOVE:
				p2 = new PointF(x, radius * c);
				p3 = new PointF(x, 0);
				p4 = new PointF(x, -radius * c);
				p5 = new PointF(radius * c, y);
				p6 = new PointF(0, y);
				p7 = new PointF(-radius * c, y);
				invalidate();
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				startSnappingBackAnimation(x - radius, y - radius);
				break;
		}
		return true;
	}
	
	/**
	 * 回弹动画
	 */
	private void startSnappingBackAnimation(final float x, final float y) {
		Log.d("test_wp", "-----value = " + x);
		Log.d("test_wp", "-----value = " + y);
		if (backAnimator == null) {
			backAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(400);
			backAnimator.setInterpolator(new BounceInterpolator());
			backAnimator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
				
				}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					p2 = new PointF(radius, radius * c);
					p3 = new PointF(radius, 0);
					p4 = new PointF(radius, -radius * c);
					invalidate();
				}
				
				@Override
				public void onAnimationCancel(Animator animation) {
				
				}
				
				@Override
				public void onAnimationRepeat(Animator animation) {
				
				}
			});
			
		}
		if (backAnimator.isRunning()) {
			return;
		}
		backAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int dx = (int) (x * (1 - (float) animation.getAnimatedValue()));
				int dy = (int) (y * (1 - (float) animation.getAnimatedValue()));
				Log.d("test_wp", "-----dValue = " + dx);
				p2 = new PointF(radius + dx, radius * c);
				p3 = new PointF(radius + dx, 0);
				p4 = new PointF(radius + dx, -radius * c);
				p5 = new PointF(radius * c, -radius + dy);
				p6 = new PointF(0, -radius + dy);
				p7 = new PointF(-radius * c, -radius + dy);
				invalidate();
			}
		});
		backAnimator.start();
	}
}
