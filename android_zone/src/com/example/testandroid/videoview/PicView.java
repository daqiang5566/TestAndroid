package com.example.testandroid.videoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.example.testandroid.R;

/**
 * Created by kexiwei on 2016/3/9.
 */
public class PicView extends View {

    Bitmap bitmap;

    private Matrix matrix;

    public PicView(Context context) {
        this(context, null);
    }

    public PicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sample);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix == null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        } else {
            canvas.drawBitmap(bitmap, matrix, null);
        }
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }



}
