/****************************************************************************
 * Copyright (c) 2016 Elektrotehnicki fakultet
 * Patre 5, Banja Luka
 * <p/>
 * All Rights Reserved
 * <p/>
 * \file TunerScale.java
 * \brief
 * This file contains a source code for TunerScale - view that is rendered inside of RadioView,
 * and it is used to show current frequency and scale.
 * <p/>
 * Created on 27.04.2016
 *
 * @Author Milan Maric
 * <p/>
 * \notes
 * <p/>
 * <p/>
 * \history
 * <p/>
 **********************************************************************/
package net.etfbl.prs.prs112212_z2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class TunerScale extends View {

    public static final int lineLength = 20;
    private static final String TAG = "TunerScale";
    public static final float minFreq = 80;
    public static final float maxFreq = 110;
    public static final int freqMargin = 60;
    private Bitmap mBitmap;
    private Rect mSrcRect;
    private Rect mDstRect = new Rect();

    private Paint red = new Paint(Color.RED);
    private Paint grey = new Paint(Color.GRAY);


    private float freq = 110;


    public TunerScale(Context context) {
        super(context);
        setBitmap();
    }

    public TunerScale(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBitmap();
    }

    public TunerScale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBitmap();
    }

    /**
     * This method is called from constructors and it's used to getBitmap from drawable,
     * also it sets a source rectangle.
     */
    private void setBitmap() {
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tuner_scale);
        mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //  super.onDraw(canvas);
        Log.d(TAG, "onDraw" + canvas.getWidth() + " ," + canvas.getHeight());
        mDstRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, grey);
        float pos = (mBitmap.getWidth() - 2* freqMargin) * (freq - minFreq) / (maxFreq-minFreq);
        int x = ScaleUtil.scale(canvas.getWidth(), mBitmap.getWidth(), freqMargin + (int) pos);
        Log.d(TAG, "x:" + x + " pos:" + pos);
        canvas.drawLine(x, ScaleUtil.scale(canvas.getHeight(), mBitmap.getHeight(), lineLength),
                x,
                canvas.getHeight() - ScaleUtil.scale(canvas.getHeight(),mBitmap.getHeight(), lineLength), red);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout(changed:" + changed + " l:" + left + " t:" + top + " r:" + right + " b:" + bottom + ")");
    }

    /**
     * This is getter method that is used for getting value of current frequency
     * @return freq - current frequency
     */
    public float getFreq() {
        return freq;
    }

    /**
     * This method is used to set current frequency
     * @param freq value of current frequency
     * @return true if frequency is set to new vale, false otherwise
     */
    public boolean setFreq(float freq) {
        if (freq >= minFreq && freq <= maxFreq) {
            this.freq = freq;
            Log.d(TAG, "frequency set to: " + freq);
            invalidate();
            return true;
        }
        return false;

    }


}
