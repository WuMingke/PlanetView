package com.erkuai.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class PlanetView extends FrameLayout {

    private static final float START_ANGLE = 270f;

    private static final int PADDING = 80;

    private static final double ROTATE_X = Math.PI * 7 / 18;

    private float sweepAngle = 0f;

    private float radius;

    private int padding;

    public PlanetView(@NonNull Context context) {
        super(context);
    }

    public PlanetView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlanetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        padding = (int) (context.getResources().getDisplayMetrics().density * PADDING);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        radius = getMeasuredWidth() / 2f - padding;
        layoutChildren();
    }

    private void layoutChildren() {
        int count = getChildCount();
        if (count == 0) return;
        // 行星之间的角度
        float averageAngle = 360f / count;
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();

            // 第index个子view的角度
            double angle = (START_ANGLE - averageAngle * index + sweepAngle) * Math.PI / 180;
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);

            double coordinateX = getMeasuredWidth() / 2f - radius * cos;
            // Math.cos(ROTATE_X) 表示将Y坐标转换为旋转之后的y坐标
            double coordinateY = radius / 2f - radius * sin * Math.cos(ROTATE_X);

            child.layout((int) (coordinateX - width / 2),
                    (int) (coordinateY - height / 2),
                    (int) (coordinateX + width / 2),
                    (int) (coordinateY + height / 2));

            // 假设view的最小缩放是原来的0.3倍
            float scale = (float) ((1 - 0.3f) / 2 * (1 - Math.sin(angle)) + 0.3f);
            child.setScaleX(scale);
            child.setScaleY(scale);
        }
    }
}
