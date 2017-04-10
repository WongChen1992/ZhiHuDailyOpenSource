package com.pixelsstudio.opensource.zhihudailyopensource.newsdetails;

import android.content.Context;

import com.pixelsstudio.opensource.zhihudailyopensource.base.BaseView;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.NewsDetails;

/**
 * Created by WongChen on 2017/2/22.
 */

public class NewsDetailsContract {
    interface View extends BaseView<NewsDetailsContract.Presenter> {
        void showNewsDetails(NewsDetails data);
    }

    interface Presenter{
        void getData(int id);
        void starred(Context context, ListNews.StoriesEntity newsBean);
        void collection(Context context,ListNews.StoriesEntity newsBean);
    }
}
