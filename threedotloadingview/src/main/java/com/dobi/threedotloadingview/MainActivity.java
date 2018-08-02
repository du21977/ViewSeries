package com.dobi.threedotloadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取完之后是不是要设置为 GONE
        View loadingView = findViewById(R.id.load_view);
//        loadingView.setVisibility(View.GONE);
        // 只是把它隐藏  58同城的加载动画讲过一次了
        // 10 个页面  内存泄漏  就是对象不可回收
        // 比如当前 Activity 关闭 但是实例一直存在内存中（就是被别人持有了）
    }
}
