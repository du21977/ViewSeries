package com.dobi.threedotloadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/4/24.
 */

public class CircleView extends View {

    private Paint mPaint;
    private int mColor;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖动
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不要父类的，自己重写
       // super.onDraw(canvas);
        int cx = getWidth()/2;
        int cy = getHeight()/2;
        canvas.drawCircle(cx,cy,cx,mPaint);
    }


    /**
     * 切换颜色
     * @param color
     */
    public void exchangeColor(int color){
        mColor = color;

        mPaint.setColor(mColor);
        //不断onDraw()
        invalidate();
    }

    public  int getColor(){
        return  mColor;
    }
}
