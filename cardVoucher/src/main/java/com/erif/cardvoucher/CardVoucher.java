package com.erif.cardvoucher;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CardVoucher extends View {

    private static final int EDGE_COUNT = 7;
    private static final int TYPE_TRIANGLE = 1;

    private static final int LINE_DOTTED_NONE = 0;
    private static final int LINE_DOTTED_VERTICAL = 1;
    private static final int LINE_DOTTED_HORIZONTAL = 2;

    private Paint paint;
    private Path path;
    private float shadowRadius;
    private final List<Float> edgesYInside = new ArrayList<>();
    private final List<Float> edgesYOutside = new ArrayList<>();

    private Paint paintLine;
    private Path pathLine;

    private int edgeCount = EDGE_COUNT;
    private int edgeType = TYPE_TRIANGLE;
    private int lineDotted = LINE_DOTTED_NONE;

    public CardVoucher(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CardVoucher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CardVoucher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setWillNotDraw(false);

        Resources.Theme theme = context.getTheme();
        if (theme != null) {
            TypedArray typedArray = theme.obtainStyledAttributes(
                    attrs, R.styleable.CardVoucher, defStyleAttr, 0
            );
            try {
                edgeType = typedArray.getInteger(R.styleable.CardVoucher_edgeType, TYPE_TRIANGLE);
                edgeCount = typedArray.getInteger(R.styleable.CardVoucher_edgeCount, EDGE_COUNT);
                lineDotted = typedArray.getInteger(R.styleable.CardVoucher_lineDotted, LINE_DOTTED_NONE);
            } finally {
                typedArray.recycle();
            }
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        path = new Path();
        shadowRadius = dimen(R.dimen.elevation_normal) / 1.2f;
        float dy = shadowRadius / 2f;
        int shadowColor = color(R.color.shadow_color);
        paint.setShadowLayer(shadowRadius, 0f, dy, shadowColor);

        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(dimen(R.dimen.line_size));
        paintLine.setColor(color(R.color.line_color));
        PathEffect effects = new DashPathEffect(
                new float[]{
                        dimen(R.dimen.line_height), dimen(R.dimen.line_space)
                }, 0
        );
        paintLine.setPathEffect(effects);
        pathLine = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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

        // Draw Line
        HelperCardVoucher.drawLine(canvas, pathLine, paintLine, left, top, right, bottom, lineDotted);

    }

    private float dimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    private int color(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

}
