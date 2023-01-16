package com.erif.cardvoucher;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class HelperCardVoucher {

    private void drawEdgeTriangle(float shadowRadius, float width) {
        /*float left = 0f + (shadowRadius / 1.8f);
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
        canvas.drawPath(path, paint);*/


    }

    public static void drawLine(
            Canvas canvas, Path pathLine, Paint paintLine,
            float left, float top, float right, float bottom, int lineDotted
    ) {
        if (lineDotted != 0) {
            if (lineDotted == 1) {
                pathLine.moveTo(right / 2f, top);
                pathLine.lineTo(right / 2f, bottom);
            } else {
                pathLine.moveTo(left, bottom / 2f);
                pathLine.lineTo(right, bottom / 2f);
            }
            canvas.drawPath(pathLine, paintLine);
        }
    }

}
