package com.example.wp.awesomemmz.skill;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wp on 2019/9/26.
 */
public class CityItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint headerBgPaint;
    private final Paint headerTextPaint;

    public CityItemDecoration() {
        headerBgPaint = new Paint();
        headerBgPaint.setColor(Color.parseColor("#20808080"));
        headerTextPaint = new Paint();
        headerTextPaint.setColor(Color.parseColor("#808080"));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() instanceof CityListAdapter) {
            CityListAdapter adapter = (CityListAdapter) parent.getAdapter();
            int position = parent.getChildLayoutPosition(view);
            if (adapter.isItemHeader(position)) {
                outRect.top = 100;
            }
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() instanceof CityListAdapter) {
            CityListAdapter adapter = (CityListAdapter) parent.getAdapter();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = parent.getChildAt(i);
                int position = parent.getChildLayoutPosition(childView);
                if (adapter.isItemHeader(position)) {
                    c.drawRect(0, childView.getTop() - 100, parent.getWidth(), childView.getTop(), headerBgPaint);
                    // headerTextPaint.getTextBounds();
                    c.drawText(adapter.getItem(position).formatInitial(),
                            20, (childView.getTop() - 100) + 100 / 2 + 50 / 2, headerTextPaint);
                }
            }
        }
    }
}
