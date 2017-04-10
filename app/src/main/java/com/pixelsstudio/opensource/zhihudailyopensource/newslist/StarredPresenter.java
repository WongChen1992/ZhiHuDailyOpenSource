package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pixelsstudio.opensource.zhihudailyopensource.db.SQLiteDBHelper;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * Created by WongChen on 2017/3/20.
 */

public class StarredPresenter implements StarredContract.Presenter{
    private StarredContract.View mStarredView;
    private List<ListNews.StoriesEntity> mDatas = new ArrayList<>();

    public StarredPresenter(@NonNull StarredContract.View starredView) {
        mStarredView = checkNotNull(starredView);
        mStarredView.setPresenter(this);
    }

    @Override
    public void getData(Context context) {
        mDatas.addAll(SQLiteDBHelper.getInstens(context).queryAll(SQLiteDBHelper.TABLE_STARRED));
        mStarredView.upDateRecyclerView(mDatas);
    }

    @Override
    public void collection(Context context, ListNews.StoriesEntity newsBean) {
        if(SQLiteDBHelper.getInstens(context).insert(SQLiteDBHelper.TABLE_COLLECTION,newsBean.getId(),newsBean) != -1){
            mStarredView.showToast("添加成功");
        }else{
            mStarredView.showToast("添加失败");
        }
    }

    @Override
    public void delete(Context context,ListNews.StoriesEntity newsBean) {
        mDatas.remove(newsBean);
        int i = SQLiteDBHelper.getInstens(context).delete(SQLiteDBHelper.TABLE_STARRED,new String[]{Integer.toString(newsBean.getId())});
        if(i != 0){
            mStarredView.showToast("删除成功");
            mStarredView.upDateRecyclerView(mDatas);
        }else{
            mStarredView.showToast("删除失败");
        }
    }
}
