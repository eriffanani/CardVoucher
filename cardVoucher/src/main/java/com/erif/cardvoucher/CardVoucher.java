package com.erif.cardvoucher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CardVoucher extends View {

    private Paint paint;
    private Path path;
    private float shadowRadius;
    private final List<Float> edgesYInside = new ArrayList<>();
    private final List<Float> edgesYOutside = new ArrayList<>();

    public CardVoucher(Context context) {
        super(context);
        init(context);
    }

    public CardVoucher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardVoucher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setWillNotDraw(false);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        shadowRadius = context.getResources().getDimensionPixelSize(R.dimen.elevation_normal) / 1.2f;
        float dy = shadowRadius / 2f;
        int shadowColor = ContextCompat.getColor(getContext(), R.color.shadow_color);
        paint.setShadowLayer(shadowRadius, 0f, dy, shadowColor);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int edgeCount = 8;
        float left = 0f + (shadowRadius / 1.8f);
        float top = 0f + (shadowRadius / 1.8f);
        float right = (left + getWidth()) - (shadowRadius * 1.8f);
        float bottom = (top + getHeight()) - (shadowRadius * 1.8f);

        float edgeSize = (bottom - shadowRadius) / edgeCount;

        path.moveTo(left, top); // Top Left

        edgesYInside.clear();
        float currentYInside = top;
        for (int i=0; i<edgeCount; i++) {
            if (i == 0) {
                currentYInside = currentYInside + (edgeSize / 2f);
            } else {
                currentYInside = currentYInside + edgeSize;
            }
            edgesYInside.add(currentYInside);
        }

        edgesYOutside.clear();
        float currentYOutside = top;
        for (int i=0; i<edgeCount - 1; i++) {
            currentYOutside = currentYOutside + edgeSize;
            edgesYOutside.add(currentYOutside);
        }

        float xInside = left + (edgeSize / 1.75f);
        for (int i=0; i<edgeCount; i++) {
            if (i < edgeCount - 1) {
                // Inside
                path.lineTo(xInside, edgesYInside.get(i));
                // Outside
                path.lineTo(left, edgesYOutside.get(i));
            } else {
                // Inside
                path.lineTo(xInside, edgesYInside.get(i));
            }
        }
        path.lineTo(left, bottom); // Bottom left
        path.lineTo(right, bottom); // Bottom Right
        path.lineTo(right, top); // Top Right
        path.close();
        canvas.drawPath(path, paint);
    }

}
