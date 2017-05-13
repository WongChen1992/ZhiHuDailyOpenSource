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
        void setStarredImg(boolean isStarred);
        void setCollectionImg(boolean isCollection);
        void setStarredVisible(boolean isVisible);
        void setCollectionVisible(boolean isVisible);
        void showToast(String str);
    }

    interface Presenter{
        void getData(int id);
        void starred(Context context, boolean isCheck);
        void collection(Context context, boolean isCheck);
        void checkStarred(Context context);
        void checkCollection(Context context);
    }
}
