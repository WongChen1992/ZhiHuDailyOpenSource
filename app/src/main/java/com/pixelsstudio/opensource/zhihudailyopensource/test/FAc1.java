package com.pixelsstudio.opensource.zhihudailyopensource.test;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pixelsstudio.opensource.zhihudailyopensource.R;

/**
 * Created by WongChen on 2017/4/23.
 */

public class FAc1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fac1, container, false);

                final Button btn = (Button) mView.findViewById(R.id.button1);
        final Intent intent = new Intent(getActivity(), Ac2.class);
        ViewCompat.setTransitionName(btn, "bbb");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(getActivity(), btn, "bbb");
                // start the new activity
                startActivity(intent, options.toBundle());
            }
        });

        return mView;
    }
}
