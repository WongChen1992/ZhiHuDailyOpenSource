package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.Context;

import com.pixelsstudio.opensource.zhihudailyopensource.base.BaseView;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;

import java.util.List;

/**
 * Created by WongChen on 2017/1/15.
 */

public interface NewsListContract {

    interface View extends BaseView<Presenter>{
        void showRefreshView();
        void hideRefreshView();
        void upDateRecyclerView(List<ListNews.StoriesEntity> data);
        void showDialog();
        void showToast(String str);
    }

    interface Presenter{
        void getData();
        void loadMoreData();
        void starred(Context context, ListNews.StoriesEntity newsBean);
        void collection(Context context,ListNews.StoriesEntity newsBean);
        void read(Context context,ListNews.StoriesEntity newsBean);
    }

}
