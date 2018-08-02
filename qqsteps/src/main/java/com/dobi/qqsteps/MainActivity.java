package com.dobi.qqsteps;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QQStepView qqStepView = (QQStepView) findViewById(R.id.step_view);

        qqStepView.setStepMax(4000);
        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,3000);
        //设置间隔时间
        valueAnimator.setDuration(1000);
        //添加一个减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        //设置监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float currentStep = (float) animation.getAnimatedValue();
                qqStepView.setCurretStep((int)currentStep);
            }
        });
        //开始动画
        valueAnimator.start();
    }
}
