package com.dobi.md1_statusbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivityDarren extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtilDarren.setActivityTranslucent(this);
    }
}
