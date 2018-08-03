package com.dobi.qqsteps;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/4/20.
 */

public class QQStepView extends View {

//    private int mOuterClor = Color.RED;
//    private int mInnerColor = Color.BLUE;
//    private int mBorderWith = 20; //20px
//    private  int mStepTextSize;
//    private int mStepTextColor ;

    //外圆颜色
    private int mOuterColor = Color.RED;
    //里圆颜色
    private int mInnerColor = Color.BLUE;
    //圆边宽度
    private int mBorderWidth = 20;// 20px
    //字体大小
    private int mStepTextSize;
    //字体颜色
    private int mStepTextColor;

    private Paint mOutPaint,mInnerPaint,mTextPaint;

    //总共的，当前的步数
    private int mStepMax = 0;
    private int mCurrentStep = 0;


    public QQStepView(Context context) {
        //这里把super改成this
        this(context,null);
    }


    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }



    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 1.分析效果；
        // 2.确定自定义属性，编写attrs.xml
        // 3.在布局中使用
        // 4.在自定义View中获取自定义属性
        // 5.onMeasure()
        // 6.onDraw() 画外圆弧 ，内圆弧 ，文字
        // 7.其他

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor,mOuterColor);//后面的参数是默认值
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor,mInnerColor);//后面的参数是默认值
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWith,mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);

        array.recycle();

        //外圆
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);  //抗锯齿
        mOutPaint.setStrokeWidth(mBorderWidth); //宽度
        mOutPaint.setColor(mOuterColor); //颜色
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);  //帽子
        mOutPaint.setStyle(Paint.Style.STROKE);// 画笔空心  ，实心是FULL



//        //内圆
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);// 画笔空心

        //画文字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);
    }


    /**
     * 对于View来说，就是重置宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 调用者在布局文件中可能  wrap_content
        // 获取模式如果是 AT_MOST  默认取一个固定值40DP

        // 宽度高度不一致 取最小值，确保是个正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //保证宽和高一致，同时去较小者
        setMeasuredDimension(width>height?height:width,width>height?height:width);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //6.1 画外圆弧
        // 6.1 画外圆弧    分析：圆弧闭合了  思考：边缘没显示完整  描边有宽度 mBorderWidth  圆弧

        // int center = getWidth()/2;
        // int radius = getWidth()/2 - mBorderWidth/2;
        // RectF rectF = new RectF(center-radius,center-radius
        // ,center+radius,center+radius);
        ////矩形的范围  ，因为有描边，所以这个显示不全
//        RectF rectF = new RectF(0,0,getWidth(),getHeight());
//        //
//        canvas.drawArc(rectF,135,270,false,mOutPaint);

        //左上右下  -----因为边缘，所以半径并不等于getWidth
        RectF rectF = new RectF(mBorderWidth/2,mBorderWidth/2
                ,getWidth()-mBorderWidth/2,getHeight()-mBorderWidth/2);
        // 研究研究
        //从135度开始画，画270度   false表示是否是与圆心相连--扇形
        canvas.drawArc(rectF,135,270,false,mOutPaint);

        //6.2 画内圆弧-------是外圆的一定比例
        //这里角度不能写死，动态递增的，所以按比例
        if(mStepMax ==0 ) return;
        //当前步数除以最大步数,必须转换float
        float sweepAngle = (float) mCurrentStep/mStepMax;
        //sweepAngle*270  实际要画的度数
        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);


        //6.3 画文字
        String stepText = mCurrentStep +"";
        Rect textBounds = new Rect();
        //得到绘制的区域长度
        mTextPaint.getTextBounds(stepText ,0,stepText.length(),textBounds);
        //得到绘制文字的起始位置
        int dx = getWidth()/2 -textBounds.width()/2;

        // 基线 baseLine
        Paint.FontMetricsInt  fontMetrics = mTextPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        int baseLine = getHeight()/2 + dy;

        //绘制文字,起始位置和基线
        canvas.drawText(stepText,dx,baseLine,mTextPaint);

    }

    //  7.其他  写几个方法动起来

    public synchronized void setStepMax(int stepMax){
        this.mStepMax = stepMax;
    }

    public synchronized void setCurretStep(int curretStep){
        this.mCurrentStep = curretStep;
        //不断绘制
        invalidate();
    }

}
