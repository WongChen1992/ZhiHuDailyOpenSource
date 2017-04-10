package com.pixelsstudio.opensource.zhihudailyopensource.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WongChen on 2017/1/15.
 */

public abstract class BaseFragment extends Fragment {
    public Context mContext;
    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mContext = getActivity();
        initView();
        return mView;
    }

    public abstract int getLayoutId();

    public abstract void initView();
}
