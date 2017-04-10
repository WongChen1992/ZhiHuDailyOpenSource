package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.Context;

import com.pixelsstudio.opensource.zhihudailyopensource.base.BaseView;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;

import java.util.List;

/**
 * Created by WongChen on 2017/3/20.
 */

public class CollectionContract {
    interface View extends BaseView<CollectionContract.Presenter> {
        void showRefreshView();
        void hideRefreshView();
        void upDateRecyclerView(List<ListNews.StoriesEntity> data);
        void showDialog();
        void showToast(String str);
    }

    interface Presenter{
        void getData(Context context);
        void delete(Context context,ListNews.StoriesEntity newsBean);
    }

}
