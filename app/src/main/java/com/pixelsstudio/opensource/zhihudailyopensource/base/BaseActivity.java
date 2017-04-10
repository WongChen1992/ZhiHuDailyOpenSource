package com.pixelsstudio.opensource.zhihudailyopensource.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pixelsstudio.opensource.zhihudailyopensource.app.AppContext;

/**
 * Created by WongChen on 2017/1/15.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public AppContext mAppContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppContext = (AppContext) getApplication();
    }
}
