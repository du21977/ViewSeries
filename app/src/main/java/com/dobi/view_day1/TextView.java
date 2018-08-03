package com.dobi.view_day1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/4/19.
 */

//如果extends LinearLayout不会触发onDraw方法
//public class TextView extends LinearLayout 下面可以解决这个不会触发onDraw方法的问题
/*
    1.重写dispatchDraw()
            2.可以背景透明的背景
            setBackgroundColor(Color.TRANSPARENT);
            3. setWillNotDraw(false);*/

public class TextView extends View {

    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;

    private Paint mPaint;


    // 构造函数会在代码里面new的时候调用
    // TextView tv = new TextView(this);
    public TextView(Context context) {
        this(context, null);
    }

    // 在布局layout中使用(调用)
    /*<com.xxx.view_day01.TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World!" />*/
    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // 在布局layout中使用(调用)，但是会有style
    /**
     <com.xxx.view_day01.TextView
     style="@style/defualt"
     android:text="Hello World!"
     */
    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);

        mText = array.getString(R.styleable.TextView_dobiText);
        mTextColor = array.getColor(R.styleable.TextView_dobiTextColor, mTextColor);
        // 15 15px 15sp    sp2px相当于传的sp大小
        //getDimensionPixelSize  系统TextView就是这么调的
        mTextSize = array.getDimensionPixelSize(R.styleable.TextView_dobiTextSize,sp2px(mTextSize));

        // 回收
        array.recycle();

        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 设置字体的大小和颜色
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        //下面是继承LinearLayout是设置这个会执行onDraw方法，否则不会执行
        //setWillNotDraw(false);
        //  默认给一个背景
        // setBackgroundColor(Color.TRANSPARENT);
    }

    private int sp2px(int sp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



        /*
        // 布局的宽高都是由这个方法指定
        // 指定控件的宽高，需要测量
        // 获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 1.确定的值，这个时候不需要计算，给的多少就是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);

        // 2.给的是wrap_content 需要计算
        if(widthMode == MeasureSpec.AT_MOST){
            // 计算的宽度 与 字体的长度有关  与字体的大小  用画笔来测量
            Rect bounds = new Rect();
            // 获取文本的Rect
            //(String text, int start, int end, Rect bounds)
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width = bounds.width() + getPaddingLeft() +getPaddingRight();
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST){
            // 计算的宽度 与 字体的长度有关  与字体的大小  用画笔来测量
            Rect bounds = new Rect();
            // 获取文本的Rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            height = bounds.height() + getPaddingTop() + getPaddingBottom();
        }

        //resolveSize()

        // 设置控件的宽高---只是size 没有mode
        setMeasuredDimension(width,height);
        */

        //宽高直接用系统自带的resolveSize，就不用管模式了,因为resolveSize里面自己判断了
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        Rect bounds = new Rect();
        // 获取文本的Rect
        //(String text, int start, int end, Rect bounds)
        mPaint.getTextBounds(mText,0,mText.length(),bounds);
        int width = bounds.width() + getPaddingLeft() +getPaddingRight();

        Rect bounds1 = new Rect();
        // 获取文本的Rect
        mPaint.getTextBounds(mText,0,mText.length(),bounds1);
       int height = bounds1.height() + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(resolveSize(width,widthMeasureSpec),resolveSize(height,heightMeasureSpec));




    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
        //画文本
        canvas.drawText();
        //画 弧
        canvas.drawArc();
        //画圆
        canvas.drawCircle();
        */

        //dy 代表的是：高度的一半到 baseLine的距离
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        // top 是一个负值  bottom 是一个正值    top，bttom的值代表是  bottom是baseLine到文字底部的距离（正值）
        // 必须要清楚的，可以自己打印就好
        int dy = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        int baseLine = getHeight()/2 + dy;
        //int x = getPaddingLeft();

        //mText是字符串,x 是起始点 y是基线
        canvas.drawText(mText,0,baseLine,mPaint);
    }

    /**
     * 手指触摸，事件分发拦截
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG","手指按下");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("TAG","手指移动");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG","手指抬起");
                break;

        }

        return super.onTouchEvent(event);

    }
}
