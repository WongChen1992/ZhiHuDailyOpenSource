package com.pixelsstudio.opensource.zhihudailyopensource.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by WongChen on 2017/1/8.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
