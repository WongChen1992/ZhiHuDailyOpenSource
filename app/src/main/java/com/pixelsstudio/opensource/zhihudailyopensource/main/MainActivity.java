package com.pixelsstudio.opensource.zhihudailyopensource.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pixelsstudio.opensource.zhihudailyopensource.R;
import com.pixelsstudio.opensource.zhihudailyopensource.helpandfeedback.SettingsActivity;
import com.pixelsstudio.opensource.zhihudailyopensource.newslist.HomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },1200);

    }
}