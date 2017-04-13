package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pixelsstudio.opensource.zhihudailyopensource.db.SQLiteDBHelper;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * Created by WongChen on 2017/3/20.
 */

public class CollectionPresenter implements CollectionContract.Presenter{
    private CollectionContract.View mCollectionView;
    private List<ListNews.StoriesEntity> mDatas = new ArrayList<>();

    public CollectionPresenter(@NonNull CollectionContract.View collectionView) {
        mCollectionView = checkNotNull(collectionView);
        mCollectionView.setPresenter(this);
    }

    @Override
    public void getData(Context context) {
        mDatas.clear();
        mDatas.addAll(SQLiteDBHelper.getInstens(context).queryAll(SQLiteDBHelper.TABLE_COLLECTION));
        mCollectionView.upDateRecyclerView(mDatas);
    }

    @Override
    public void delete(Context context, ListNews.StoriesEntity newsBean) {
        mDatas.remove(newsBean);
        int i = SQLiteDBHelper.getInstens(context).delete(SQLiteDBHelper.TABLE_COLLECTION,new String[]{Integer.toString(newsBean.getId())});
        if(i != 0){
            mCollectionView.showToast("删除成功");
            mCollectionView.upDateRecyclerView(mDatas);
        }else{
            mCollectionView.showToast("删除失败");
        }
    }
}
