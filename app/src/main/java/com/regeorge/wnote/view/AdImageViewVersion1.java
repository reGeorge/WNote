package com.regeorge.wnote.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class AdImageViewVersion1 extends AppCompatImageView {
    public AdImageViewVersion1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    private RectF mBitmapRectF;
//    private Bitmap mBitmap;

//    private int mMinDy;

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        mMinDy = h;
//        Drawable drawable = getDrawable();
//
//        if (drawable == null) {
//            return;
//        }
//
//        mBitmap = drawableToBitamp(drawable);
//        mBitmapRectF = new RectF(0, 0,
//                w,
//                mBitmap.getHeight() * w / mBitmap.getWidth());
//
//    }
//
//
//    private Bitmap drawableToBitamp(Drawable drawable) {
//        if (drawable instanceof BitmapDrawable) {
//            BitmapDrawable bd = (BitmapDrawable) drawable;
//            return bd.getBitmap();
//        }
//        int w = drawable.getIntrinsicWidth();
//        int h = drawable.getIntrinsicHeight();
//        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, w, h);
//        drawable.draw(canvas);
//        return bitmap;
//    }

    // ... 省略一些代码

    private int mDx;
    private int mMinDx;

    public void setDx(float bili) {
        if (getDrawable() == null) {
            return;
        }
//        int mPx = getDrawable().getBounds().height();
        int Min = 0;
        int Max = getDrawable().getBounds().height() - mMinDx;
        mDx = (int) (bili * (getDrawable().getBounds().height() - mMinDx ));
//        mDx = (int) (bili);

        if (mDx <= Min) {
            mDx = Min;
        }
        if (mDx > Max) {
            mDx = Max;
        }

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDx = h;
    }

    public int getDx() {
        return mDx;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        int w = getWidth();
        int h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, w, h);
        canvas.save();
        canvas.translate(0, -getDx());
        super.onDraw(canvas);
        canvas.restore();
    }

}