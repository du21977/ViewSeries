package com.dobi.letterside;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LetterSideBar.LetterTouchListener {
    private TextView mLetterTv;
    private LetterSideBar mLetterSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLetterTv = (TextView) findViewById(R.id.letter_tv);
        mLetterSideBar = (LetterSideBar) findViewById(R.id.letter_side_bar);
        mLetterSideBar.setOnLetterTouchListener(this);
    }


    /**
     * 接口回调
     * @param letter
     * @param isTouch
     */
    @Override
    public void touch(CharSequence letter,boolean isTouch) {
        if(isTouch) {
            mLetterTv.setVisibility(View.VISIBLE);
            mLetterTv.setText(letter);
        }else{
            //自己实现让其延时消失
            mLetterTv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLetterTv.setVisibility(View.GONE);
                }
            },1000);

        }
    }
}

