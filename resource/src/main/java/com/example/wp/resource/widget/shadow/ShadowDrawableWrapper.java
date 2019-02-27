package com.example.wp.resource.widget.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.wp.resource.R.color;

/**
 * 使用 FAB 的原理模拟阴影
 */
public class ShadowDrawableWrapper extends DrawableWrapper {
	static final double COS_45 = Math.cos(Math.toRadians(45.0D));
	static final float SHADOW_MULTIPLIER = 1.5F;
	static final float SHADOW_TOP_SCALE = 0.25F;
	static final float SHADOW_HORIZ_SCALE = 0.5F;
	static final float SHADOW_BOTTOM_SCALE = 1.0F;
	final Paint cornerShadowPaint;
	final Paint edgeShadowPaint;
	final RectF contentBounds;
	float cornerRadius;
	Path cornerShadowPath;
	float maxShadowSize;
	float rawMaxShadowSize;
	float shadowSize;
	float rawShadowSize;
	private boolean dirty = true;
	private final int shadowStartColor;
	private final int shadowMiddleColor;
	private final int shadowEndColor;
	private boolean addPaddingForCorners = true;
	private float rotation;
	private boolean printedShadowClipWarning = false;
	
	public ShadowDrawableWrapper(Context context, Drawable content, float radius, float shadowSize, float maxShadowSize) {
		super(content);
		this.shadowStartColor = ContextCompat.getColor(context, color.custom_shadow_start_color);
		this.shadowMiddleColor = ContextCompat.getColor(context, color.custom_shadow_mid_color);
		this.shadowEndColor = ContextCompat.getColor(context, color.custom_shadow_end_color);
		this.cornerShadowPaint = new Paint(5);
		this.cornerShadowPaint.setStyle(Style.FILL);
		this.cornerRadius = (float) Math.round(radius);
		this.contentBounds = new RectF();
		this.edgeShadowPaint = new Paint(this.cornerShadowPaint);
		this.edgeShadowPaint.setAntiAlias(false);
		this.setShadowSize(shadowSize, maxShadowSize);
	}
	
	private static int toEven(float value) {
		int i = Math.round(value);
		return i % 2 == 1 ? i - 1 : i;
	}
	
	public void setAddPaddingForCorners(boolean addPaddingForCorners) {
		this.addPaddingForCorners = addPaddingForCorners;
		this.invalidateSelf();
	}
	
	public void setAlpha(int alpha) {
		super.setAlpha(alpha);
		this.cornerShadowPaint.setAlpha(alpha);
		this.edgeShadowPaint.setAlpha(alpha);
	}
	
	protected void onBoundsChange(Rect bounds) {
		this.dirty = true;
	}
	
	public void setShadowSize(float shadowSize, float maxShadowSize) {
		if (shadowSize >= 0.0F && maxShadowSize >= 0.0F) {
			shadowSize = (float) toEven(shadowSize);
			maxShadowSize = (float) toEven(maxShadowSize);
			if (shadowSize > maxShadowSize) {
				shadowSize = maxShadowSize;
				if (!this.printedShadowClipWarning) {
					this.printedShadowClipWarning = true;
				}
			}
			
			if (this.rawShadowSize != shadowSize || this.rawMaxShadowSize != maxShadowSize) {
				this.rawShadowSize = shadowSize;
				this.rawMaxShadowSize = maxShadowSize;
				this.shadowSize = (float) Math.round(shadowSize * 1.5F);
				this.maxShadowSize = maxShadowSize;
				this.dirty = true;
				this.invalidateSelf();
			}
		} else {
			throw new IllegalArgumentException("invalid shadow size");
		}
	}
	
	public void setShadowSize(float size) {
		this.setShadowSize(size, this.rawMaxShadowSize);
	}
	
	public float getShadowSize() {
		return this.rawShadowSize;
	}
	
	public boolean getPadding(Rect padding) {
		int vOffset = (int) Math.ceil((double) calculateVerticalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
		int hOffset = (int) Math.ceil((double) calculateHorizontalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
		padding.set(hOffset, vOffset, hOffset, vOffset);
		return true;
	}
	
	public static float calculateVerticalPadding(float maxShadowSize, float cornerRadius, boolean addPaddingForCorners) {
		return addPaddingForCorners ? (float) ((double) (maxShadowSize * 1.5F) + (1.0D - COS_45) * (double) cornerRadius) : maxShadowSize * 1.5F;
	}
	
	public static float calculateHorizontalPadding(float maxShadowSize, float cornerRadius, boolean addPaddingForCorners) {
		return addPaddingForCorners ? (float) ((double) maxShadowSize + (1.0D - COS_45) * (double) cornerRadius) : maxShadowSize;
	}
	
	public int getOpacity() {
		return -3;
	}
	
	public void setCornerRadius(float radius) {
		radius = (float) Math.round(radius);
		if (this.cornerRadius != radius) {
			this.cornerRadius = radius;
			this.dirty = true;
			this.invalidateSelf();
		}
	}
	
	public void draw(Canvas canvas) {
		if (this.dirty) {
			this.buildComponents(this.getBounds());
			this.dirty = false;
		}
		
		this.drawShadow(canvas);
		super.draw(canvas);
	}
	
	public final void setRotation(float rotation) {
		if (this.rotation != rotation) {
			this.rotation = rotation;
			this.invalidateSelf();
		}
		
	}
	
	private void drawShadow(Canvas canvas) {
		int rotateSaved = canvas.save();
		canvas.rotate(this.rotation, this.contentBounds.centerX(), this.contentBounds.centerY());
		float edgeShadowTop = -this.cornerRadius - this.shadowSize;
		float shadowOffset = this.cornerRadius;
		boolean drawHorizontalEdges = this.contentBounds.width() - 2.0F * shadowOffset > 0.0F;
		boolean drawVerticalEdges = this.contentBounds.height() - 2.0F * shadowOffset > 0.0F;
		float shadowOffsetTop = this.rawShadowSize - this.rawShadowSize * 0.25F;
		float shadowOffsetHorizontal = this.rawShadowSize - this.rawShadowSize * 0.5F;
		float shadowOffsetBottom = this.rawShadowSize - this.rawShadowSize * 1.0F;
		float shadowScaleHorizontal = shadowOffset / (shadowOffset + shadowOffsetHorizontal);
		float shadowScaleTop = shadowOffset / (shadowOffset + shadowOffsetTop);
		float shadowScaleBottom = shadowOffset / (shadowOffset + shadowOffsetBottom);
		int saved = canvas.save();
		canvas.translate(this.contentBounds.left + shadowOffset, this.contentBounds.top + shadowOffset);
		canvas.scale(shadowScaleHorizontal, shadowScaleTop);
		canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
		if (drawHorizontalEdges) {
			canvas.scale(1.0F / shadowScaleHorizontal, 1.0F);
			canvas.drawRect(0.0F, edgeShadowTop, this.contentBounds.width() - 2.0F * shadowOffset, -this.cornerRadius, this.edgeShadowPaint);
		}
		
		canvas.restoreToCount(saved);
		saved = canvas.save();
		canvas.translate(this.contentBounds.right - shadowOffset, this.contentBounds.bottom - shadowOffset);
		canvas.scale(shadowScaleHorizontal, shadowScaleBottom);
		canvas.rotate(180.0F);
		canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
		if (drawHorizontalEdges) {
			canvas.scale(1.0F / shadowScaleHorizontal, 1.0F);
			canvas.drawRect(0.0F, edgeShadowTop, this.contentBounds.width() - 2.0F * shadowOffset, -this.cornerRadius + this.shadowSize, this.edgeShadowPaint);
		}
		
		canvas.restoreToCount(saved);
		saved = canvas.save();
		canvas.translate(this.contentBounds.left + shadowOffset, this.contentBounds.bottom - shadowOffset);
		canvas.scale(shadowScaleHorizontal, shadowScaleBottom);
		canvas.rotate(270.0F);
		canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
		if (drawVerticalEdges) {
			canvas.scale(1.0F / shadowScaleBottom, 1.0F);
			canvas.drawRect(0.0F, edgeShadowTop, this.contentBounds.height() - 2.0F * shadowOffset, -this.cornerRadius, this.edgeShadowPaint);
		}
		
		canvas.restoreToCount(saved);
		saved = canvas.save();
		canvas.translate(this.contentBounds.right - shadowOffset, this.contentBounds.top + shadowOffset);
		canvas.scale(shadowScaleHorizontal, shadowScaleTop);
		canvas.rotate(90.0F);
		canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
		if (drawVerticalEdges) {
			canvas.scale(1.0F / shadowScaleTop, 1.0F);
			canvas.drawRect(0.0F, edgeShadowTop, this.contentBounds.height() - 2.0F * shadowOffset, -this.cornerRadius, this.edgeShadowPaint);
		}
		
		canvas.restoreToCount(saved);
		canvas.restoreToCount(rotateSaved);
	}
	
	private void buildShadowCorners() {
		RectF innerBounds = new RectF(-this.cornerRadius, -this.cornerRadius, this.cornerRadius, this.cornerRadius);
		RectF outerBounds = new RectF(innerBounds);
		outerBounds.inset(-this.shadowSize, -this.shadowSize);
		if (this.cornerShadowPath == null) {
			this.cornerShadowPath = new Path();
		} else {
			this.cornerShadowPath.reset();
		}
		
		this.cornerShadowPath.setFillType(FillType.EVEN_ODD);
		this.cornerShadowPath.moveTo(-this.cornerRadius, 0.0F);
		this.cornerShadowPath.rLineTo(-this.shadowSize, 0.0F);
		this.cornerShadowPath.arcTo(outerBounds, 180.0F, 90.0F, false);
		this.cornerShadowPath.arcTo(innerBounds, 270.0F, -90.0F, false);
		this.cornerShadowPath.close();
		float shadowRadius = -outerBounds.top;
		if (shadowRadius > 0.0F) {
			float startRatio = this.cornerRadius / shadowRadius;
			float midRatio = startRatio + (1.0F - startRatio) / 2.0F;
			this.cornerShadowPaint.setShader(new RadialGradient(0.0F, 0.0F, shadowRadius, new int[]{0, this.shadowStartColor, this.shadowMiddleColor, this.shadowEndColor}, new float[]{0.0F, startRatio, midRatio, 1.0F}, TileMode.CLAMP));
		}
		
		this.edgeShadowPaint.setShader(new LinearGradient(0.0F, innerBounds.top, 0.0F, outerBounds.top, new int[]{this.shadowStartColor, this.shadowMiddleColor, this.shadowEndColor}, new float[]{0.0F, 0.5F, 1.0F}, TileMode.CLAMP));
		this.edgeShadowPaint.setAntiAlias(false);
	}
	
	private void buildComponents(Rect bounds) {
		float verticalOffset = this.rawMaxShadowSize * 1.5F;
		this.contentBounds.set((float) bounds.left + this.rawMaxShadowSize, (float) bounds.top + verticalOffset, (float) bounds.right - this.rawMaxShadowSize, (float) bounds.bottom - verticalOffset);
		this.getWrappedDrawable().setBounds((int) this.contentBounds.left, (int) this.contentBounds.top, (int) this.contentBounds.right, (int) this.contentBounds.bottom);
		this.buildShadowCorners();
	}
	
	public float getCornerRadius() {
		return this.cornerRadius;
	}
	
	public void setMaxShadowSize(float size) {
		this.setShadowSize(this.rawShadowSize, size);
	}
	
	public float getMaxShadowSize() {
		return this.rawMaxShadowSize;
	}
	
	public float getMinWidth() {
		float content = 2.0F * Math.max(this.rawMaxShadowSize, this.cornerRadius + this.rawMaxShadowSize / 2.0F);
		return content + this.rawMaxShadowSize * 2.0F;
	}
	
	public float getMinHeight() {
		float content = 2.0F * Math.max(this.rawMaxShadowSize, this.cornerRadius + this.rawMaxShadowSize * 1.5F / 2.0F);
		return content + this.rawMaxShadowSize * 1.5F * 2.0F;
	}
}
