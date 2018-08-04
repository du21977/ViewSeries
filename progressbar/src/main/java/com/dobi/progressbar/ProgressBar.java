package com.dobi.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2018/4/24.
 * 圆形进度条
 */

public class ProgressBar extends View {

    //内圆背景色
    private int mInnerBackground = Color.RED;
    //外圆背景色
    private int mOuterBackground = Color.RED;
    //圆宽度
    private int mRoundWidth = 10;// 10px
    //字体大小
    private float mProgressTextSize = 15;
    //字体颜色
    private int mProgressTextColor = Color.RED;
    //内圆，外圆，字体画笔
    private Paint mInnerPaint, mOuterPaint, mTextPaint;

    private int mMax = 100;
    private int mProgress = 0;

    public ProgressBar(Context context) {
        this(context,null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性  ----不要忘记attrs
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mInnerBackground = array.getColor(R.styleable.ProgressBar_innerBackground, mInnerBackground);
        mOuterBackground = array.getColor(R.styleable.ProgressBar_outerBackground, mOuterBackground);
        //getDimension得到是浮点数据，getDimensionPixelSize是int数据
        mRoundWidth = (int) array.getDimension(R.styleable.ProgressBar_roundWidth, dip2px(10));
        mProgressTextSize = array.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize,
                sp2px(mProgressTextSize));
        mProgressTextColor = array.getColor(R.styleable.ProgressBar_progressTextColor, mProgressTextColor);

        array.recycle();

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBackground);
        mInnerPaint.setStrokeWidth(mRoundWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterBackground);
        mOuterPaint.setStrokeWidth(mRoundWidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);

    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //要保证宽高一致是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        // 先画内圆
        int center = getWidth() / 2;
        canvas.drawCircle(center, center, center - mRoundWidth / 2, mInnerPaint);

        // 画外圆,画圆弧
        RectF rect = new RectF(0 + mRoundWidth / 2, 0 + mRoundWidth / 2,
                getWidth() - mRoundWidth / 2, getHeight() - mRoundWidth / 2);

        if (mProgress == 0) {
            return;
        }
        float percent = (float) mProgress / mMax;
        canvas.drawArc(rect, 0, percent * 360, false, mOuterPaint);

        // 画进度文字
        String text = ((int) (percent * 100)) + "%";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBounds);
        int x = getWidth() / 2 - textBounds.width() / 2;

        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLineY = getHeight() / 2 + dy;

        canvas.drawText(text, x, baseLineY, mTextPaint);
    }

    // 给几个方法
    public synchronized void setMax(int max) {
        if (max < 0) {

        }
        this.mMax = max;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
        }
        this.mProgress = progress;
        // 刷新 invalidate
        invalidate();
    }

}
