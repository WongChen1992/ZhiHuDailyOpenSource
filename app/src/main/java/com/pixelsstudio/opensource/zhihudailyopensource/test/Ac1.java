package com.pixelsstudio.opensource.zhihudailyopensource.test;

import android.app.ActivityOptions;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pixelsstudio.opensource.zhihudailyopensource.R;

/**
 * Created by WongChen on 2017/4/23.
 */

public class Ac1 extends AppCompatActivity {
    private FAc1 fAc1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac1);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fAc1 = new FAc1();
        transaction.replace(R.id.frame, fAc1);
        transaction.commit();
//
//        final Button btn = (Button) findViewById(R.id.button1);
//        final Intent intent = new Intent(Ac1.this, Ac2.class);
//        ViewCompat.setTransitionName(btn, "bbb");
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityOptions options = ActivityOptions
//                        .makeSceneTransitionAnimation(Ac1.this, btn, "bbb");
//                // start the new activity
//                startActivity(intent, options.toBundle());
//            }
//        });
    }
}
