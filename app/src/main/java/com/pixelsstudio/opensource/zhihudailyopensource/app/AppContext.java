package com.pixelsstudio.opensource.zhihudailyopensource.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by WongChen on 2017/1/8.
 */

public class AppContext extends Application {
    public boolean isReadDelete;//要闻是否为阅后删除

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Fresco.initialize(this);
        //TODO 初始化isReadDelete
    }

    private static Context context;

    public static Context getContextObject(){
        return context;
    }
}
