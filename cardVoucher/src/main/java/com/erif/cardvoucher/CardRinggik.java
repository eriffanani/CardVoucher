package com.erif.cardvoucher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CardRinggik extends View {

    private Paint paint;
    private Path path;
    private float shadowRadius;
    private RectF rectF;

    public CardRinggik(Context context) {
        super(context);
        init(context);
    }

    public CardRinggik(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardRinggik(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        rectF = new RectF();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int edgeCount = 8;
        float spaceSize = 0.4f;
        float available = 1f - spaceSize;

        float left = 0f + (shadowRadius / 1.8f);
        float top = 0f + (shadowRadius / 1.8f);
        float right = (left + getWidth()) - (shadowRadius * 1.8f);
        float bottom = (top + getHeight()) - (shadowRadius * 1.8f);

        path.reset();

        float getEdgeOriginalSize = bottom / edgeCount;
        float space = getEdgeOriginalSize * spaceSize;

        float newHeight = (bottom - space) / edgeCount;
        float edgeSize = newHeight * available;

        path.moveTo(left, top); // Start point on top left

        float edgeLeft = left - (edgeSize / 2f);
        float edgeRight = edgeLeft + edgeSize;
        float currentHeight = 0f;
        for (int i=0; i<=edgeCount; i++) {
            currentHeight += space;
            path.lineTo(left, currentHeight);
            if (i < edgeCount) {
                float edgeTop = currentHeight;
                currentHeight += edgeSize;
                rectF.set(edgeLeft, edgeTop, edgeRight, currentHeight);
                path.arcTo(rectF, -90f, 270f);
                path.lineTo(left, currentHeight);
            }
        }

        path.lineTo(left, bottom); // Bottom Left
        path.lineTo(right, bottom); // Bottom Right
        path.lineTo(right, top); // Top Right
        path.close();
        canvas.drawPath(path, paint);
    }

    private float toFloat(int value) {
        return (float) value;
    }

}
