package com.pixelsstudio.opensource.zhihudailyopensource.newsdetails;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pixelsstudio.opensource.zhihudailyopensource.api.ApiClient;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.NewsDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * Created by WongChen on 2017/2/22.
 */

public class NewsDetailsPresenter implements NewsDetailsContract.Presenter{
    private final NewsDetailsContract.View mNewsDetailsView;

    public NewsDetailsPresenter(@NonNull NewsDetailsContract.View newsDetailsView) {
        mNewsDetailsView = checkNotNull(newsDetailsView);
        mNewsDetailsView.setPresenter(this);
    }

    @Override
    public void getData(int id) {
        Call<NewsDetails> call = ApiClient.getAPI().newsDetails(id);
        call.enqueue(new Callback<NewsDetails>() {
            @Override
            public void onResponse(Call<NewsDetails> call, Response<NewsDetails> response) {
                mNewsDetailsView.showNewsDetails(response.body());
            }

            @Override
            public void onFailure(Call<NewsDetails> call, Throwable t) {

            }
        });
    }

    @Override
    public void starred(Context context, ListNews.StoriesEntity newsBean) {

    }

    @Override
    public void collection(Context context, ListNews.StoriesEntity newsBean) {

    }
}
