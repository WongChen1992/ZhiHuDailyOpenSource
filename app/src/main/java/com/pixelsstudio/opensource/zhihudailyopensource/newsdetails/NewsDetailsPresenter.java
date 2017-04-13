package com.pixelsstudio.opensource.zhihudailyopensource.newsdetails;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pixelsstudio.opensource.zhihudailyopensource.api.ApiClient;
import com.pixelsstudio.opensource.zhihudailyopensource.db.SQLiteDBHelper;
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
    private NewsDetails mNewsDetails;
    private ListNews.StoriesEntity mNewsBean;

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
                mNewsDetails = response.body();
                mNewsBean = new ListNews.StoriesEntity();
                mNewsBean.setId(mNewsDetails.getId());
                mNewsBean.setTitle(mNewsDetails.getTitle());
                mNewsBean.setImages(mNewsDetails.getImages());
                mNewsDetailsView.showNewsDetails(mNewsDetails);
            }

            @Override
            public void onFailure(Call<NewsDetails> call, Throwable t) {

            }
        });
    }

    @Override
    public void starred(Context context, boolean isCheck) {
        if (isCheck){
            if( SQLiteDBHelper.getInstens(context).delete(SQLiteDBHelper.TABLE_STARRED,new String[]{Integer.toString(mNewsDetails.getId())}) != 0){
                mNewsDetailsView.showToast("删除成功");
                mNewsDetailsView.setStarredImg(false);
            }else{
                mNewsDetailsView.showToast("删除失败");
            }
            return;
        }
        if(SQLiteDBHelper.getInstens(context).insert(SQLiteDBHelper.TABLE_STARRED,mNewsDetails.getId(),mNewsBean) != -1){
            mNewsDetailsView.showToast("添加成功");
            mNewsDetailsView.setStarredImg(true);
        }else{
            mNewsDetailsView.showToast("添加失败");
        }
    }

    @Override
    public void collection(Context context, boolean isCheck) {
        if (isCheck){
            if( SQLiteDBHelper.getInstens(context).delete(SQLiteDBHelper.TABLE_COLLECTION,new String[]{Integer.toString(mNewsDetails.getId())}) != 0){
                mNewsDetailsView.showToast("删除成功");
                mNewsDetailsView.setCollectionImg(false);
            }else{
                mNewsDetailsView.showToast("删除失败");
            }
            return;
        }
        if(SQLiteDBHelper.getInstens(context).insert(SQLiteDBHelper.TABLE_COLLECTION,mNewsDetails.getId(),mNewsBean) != -1){
            mNewsDetailsView.showToast("添加成功");
            mNewsDetailsView.setCollectionImg(true);
        }else{
            mNewsDetailsView.showToast("添加失败");
        }
    }

    @Override
    public void checkStarred(Context context) {
        boolean isStarred = SQLiteDBHelper.getInstens(context).query(SQLiteDBHelper.TABLE_STARRED, new String[]{String.valueOf(mNewsDetails.getId())});
        mNewsDetailsView.setStarredImg(isStarred);
    }

    @Override
    public void checkCollection(Context context) {
        boolean isCollection = SQLiteDBHelper.getInstens(context).query(SQLiteDBHelper.TABLE_COLLECTION, new String[]{String.valueOf(mNewsDetails.getId())});
        mNewsDetailsView.setCollectionImg(isCollection);
    }
}
