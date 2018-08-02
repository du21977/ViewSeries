package com.dobi.redpackageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.dobi.redpackageview.R.id.red_pack_view;

public class MainActivity extends AppCompatActivity {

    RedPackageView red_pack_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        red_pack_view = (RedPackageView) findViewById(R.id.red_pack_view);
        red_pack_view.setTotalProgress(3);
        red_pack_view.startAnimation(1,3);
//        red_pack_view.setOnClickListener{
//
//        }
    }
}
