package com.example.wp.awesomemmz.skill;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wp on 2019/9/26.
 */
public class CityItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint headerBgPaint;
    private final Paint headerTextPaint;
    private final Rect textBounds;

    private int headerHeight;
    private int offsetLeft;

    public CityItemDecoration() {
        headerBgPaint = new Paint();
        headerBgPaint.setColor(Color.parseColor("#e0e0e0"));
        headerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        headerTextPaint.setColor(Color.parseColor("#939699"));
        headerTextPaint.setTextSize(20);

        textBounds = new Rect();

        headerHeight = 50;
        offsetLeft = 20;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() instanceof CityListAdapter) {
            CityListAdapter adapter = (CityListAdapter) parent.getAdapter();
            int position = parent.getChildLayoutPosition(view);
            if (adapter.isItemHeader(position)) {
                outRect.top = headerHeight;
            }
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //画title
        if (parent.getAdapter() instanceof CityListAdapter) {
            CityListAdapter adapter = (CityListAdapter) parent.getAdapter();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = parent.getChildAt(i);
                int position = parent.getChildLayoutPosition(childView);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                if (adapter.isItemHeader(position)) {
                    //画背景
                    c.drawRect(left, childView.getTop() - headerHeight, right, childView.getTop(), headerBgPaint);
                    //画title
                    String text = adapter.getItem(position).formatInitial();
                    headerTextPaint.getTextBounds(text, 0, text.length(), textBounds);//计算text显示区域
                    //左下角的坐标??--(x默认是字符串的左边在屏幕的位置，y是指定这个字符baseline在屏幕上的位置。)
                    c.drawText(text, offsetLeft, (childView.getTop() - headerHeight) + headerHeight / 2 + textBounds.height() / 2, headerTextPaint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //画悬浮title
        if (parent.getAdapter() instanceof CityListAdapter) {
            CityListAdapter adapter = (CityListAdapter) parent.getAdapter();
            int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
            View view = parent.findViewHolderForAdapterPosition(position).itemView;
            boolean isHeader = adapter.isItemHeader(position + 1);
            int top = parent.getPaddingTop();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            String text = adapter.getItem(position).formatInitial();
            headerTextPaint.getTextBounds(text, 0, text.length(), textBounds);//计算text显示区域
            if (isHeader) {
                int bottom = Math.min(headerHeight, view.getBottom());
                c.drawRect(left, top + view.getTop() - headerHeight, right, top + bottom, headerBgPaint);
                // c.drawText(text, left + offsetLeft, top + headerHeight / 2 + textBounds.height() / 2 - (headerHeight - bottom), headerTextPaint);
                c.drawText(text, left + offsetLeft, top + bottom - (headerHeight - textBounds.height()) / 2, headerTextPaint);
            } else {
                c.drawRect(left, top, right, top + headerHeight, headerBgPaint);
                c.drawText(text, left + offsetLeft, top + headerHeight / 2 + textBounds.height() / 2, headerTextPaint);
            }
            c.save();
        }
    }
}
