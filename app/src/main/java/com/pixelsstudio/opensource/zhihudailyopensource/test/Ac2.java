package com.pixelsstudio.opensource.zhihudailyopensource.test;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pixelsstudio.opensource.zhihudailyopensource.R;

/**
 * Created by WongChen on 2017/4/23.
 */

public class Ac2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac2);

        Button btn = (Button) findViewById(R.id.button);

        ViewCompat.setTransitionName(btn, "bbb");

    }
}
