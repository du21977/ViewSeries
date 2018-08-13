package com.lianying.md2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hcDarren on 2017/7/10.
 */

public class TanslationBehavior extends FloatingActionButton.Behavior{

    /**
     * 要构造函数，不要会报错
     * @param context
     * @param attrs
     */
    public TanslationBehavior(Context context,AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams lp) {

        //TanslationBehavior 刚刚创建完毕 会调用这个方法
        super.onAttachedToLayoutParams(lp);
    }

    // 关注垂直滚动 ，而且向上的时候是出来，向下是隐藏
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return  nestedScrollAxes  == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
    private boolean isOut = false;
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.e("TAG","dyConsumed -> "+dyConsumed+" dyUnconsumed -> "+dyUnconsumed);
        // 而且向上的时候是出来，向下是隐藏
        if(dyConsumed > 0){
            if(!isOut) {
                // 往上滑动，是隐藏 , 加一个标志位 已经往下走了
                int translationY = ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin + child.getMeasuredHeight();
                child.animate().translationY(translationY).setDuration(500).start();
                isOut = true;
            }
        }else{
            if(isOut) {
                // 往下滑动
                child.animate().translationY(0).setDuration(500).start();
                isOut = false;
            }
        }
    }
}
