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

public class CardVoucher extends View {

    private Paint paint;
    private Path path;
    private float shadowRadius;

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
        float topX = getWidth() / 2f;
        float topY = 0f + shadowRadius;
        float bottomY = toFloat(getHeight()) - shadowRadius;

        path.moveTo(topX, topY); // Top
        path.lineTo(topX / 2f, bottomY); // Bottom left

        path.lineTo(topX - 40, bottomY);
        path.lineTo(topX, bottomY - 50);
        path.lineTo(topX + 40, bottomY);

        path.lineTo(topX * 1.5f, bottomY); // Bottom right
        path.lineTo(topX, topY); // Back to Top
        path.close();
        canvas.drawPath(path, paint);
    }

    private float toFloat(int value) {
        return (float) value;
    }

}
