package com.dobi.loading_view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        view.setVisibility(View.GONE);
    }

    public void start(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
