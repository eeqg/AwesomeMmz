package com.example.wp.awesomemmz.skill;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wp on 2019/9/27.
 */
public class LetterIndexView extends View {
    private final String TAG = "LetterIndexView";
    private final String[] letters = {"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private Paint textPaint;
    private Paint bgPaint;
    private Rect textBounds;
    private boolean drawBg = false;

    private OnLetterTouchListener letterTouchListener;

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        bgPaint = new Paint();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#969399"));
        textBounds = new Rect();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                drawBg = true;
                int height = getHeight() / letters.length;
                int position = (int) (event.getY() / height);
                Log.d(TAG, "-----event.getY() = " + event.getY());
                Log.d(TAG, "-----height = " + height);
                Log.d(TAG, "-----position = " + position);
                position = position < 0 ? 0 : (position >= letters.length ? letters.length - 1 : position);
                if (letterTouchListener != null) {
                    letterTouchListener.onTouched(position, letters[position]);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                drawBg = false;
                if (letterTouchListener != null) {
                    letterTouchListener.onCancel();
                }
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawBg) {
            canvas.drawColor(Color.parseColor("#20808080"));
        }

        float height = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
        float width = getWidth();
        float textSize = height * 3 / 7;
        textPaint.setTextSize(textSize);

        for (int i = 0; i < letters.length; i++) {
            String letter = letters[i];
            textPaint.getTextBounds(letter, 0, letter.length(), textBounds);
            canvas.drawText(letter, (width - textBounds.width()) / 2 - getPaddingLeft(),
                    getPaddingTop() + (height / 2 + textBounds.height() / 2) + height * i, textPaint);
        }
    }

    public void setOnLetterTouchListener(OnLetterTouchListener listener) {
        this.letterTouchListener = listener;
    }

    public interface OnLetterTouchListener {
        void onTouched(int position, String letter);

        void onCancel();
    }
}
