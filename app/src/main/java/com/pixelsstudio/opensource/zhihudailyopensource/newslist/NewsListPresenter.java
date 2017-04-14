package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pixelsstudio.opensource.zhihudailyopensource.api.ApiClient;
import com.pixelsstudio.opensource.zhihudailyopensource.db.SQLiteDBHelper;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * Created by WongChen on 2017/1/15.
 */

public class NewsListPresenter implements NewsListContract.Presenter {
    //TODO P是否应该掌握Context
    private NewsListContract.View mNewsListView;
    private String date;
    private List<ListNews.StoriesEntity> mDatas = new ArrayList<>();

    public NewsListPresenter(@NonNull NewsListContract.View newsListView) {
        mNewsListView = checkNotNull(newsListView);
        mNewsListView.setPresenter(this);
    }

    @Override
    public void getData() {
        Call<ListNews> call = ApiClient.getAPI().listNews();
        call.enqueue(new Callback<ListNews>() {
            @Override
            public void onResponse(Call<ListNews> call, Response<ListNews> response) {
                date = response.body().getDate();
                mDatas.clear();
                mDatas.addAll(response.body().getStories());
                mNewsListView.upDateRecyclerView(mDatas);
                mNewsListView.hideRefreshView();
            }

            @Override
            public void onFailure(Call<ListNews> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadMoreData() {
        if (mDatas.size() == 0)
            return;

        Call<ListNews> call = ApiClient.getAPI().newsBefore(date);
        call.enqueue(new Callback<ListNews>() {
            @Override
            public void onResponse(Call<ListNews> call, Response<ListNews> response) {
                date = response.body().getDate();
                mDatas.addAll(response.body().getStories());
                mNewsListView.upDateRecyclerView(mDatas);
            }

            @Override
            public void onFailure(Call<ListNews> call, Throwable t) {

            }
        });
    }

    @Override
    public void starred(Context context, ListNews.StoriesEntity newsBean) {
        if(SQLiteDBHelper.getInstens(context).insert(SQLiteDBHelper.TABLE_STARRED,newsBean.getId(),newsBean) != -1){
            mNewsListView.showToast("添加成功");
        }else{
            mNewsListView.showToast("添加失败");
        }
    }

    @Override
    public void collection(Context context,ListNews.StoriesEntity newsBean) {
        if(SQLiteDBHelper.getInstens(context).insert(SQLiteDBHelper.TABLE_COLLECTION,newsBean.getId(),newsBean) != -1){
            mNewsListView.showToast("添加成功");
        }else{
            mNewsListView.showToast("添加失败");
        }
    }

    @Override
    public void read(Context context, ListNews.StoriesEntity newsBean) {
        SQLiteDBHelper.getInstens(context).insert(SQLiteDBHelper.TABLE_READ,newsBean.getId(),newsBean);
    }

}
