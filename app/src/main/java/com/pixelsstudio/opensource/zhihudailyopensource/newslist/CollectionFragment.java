package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import com.pixelsstudio.opensource.zhihudailyopensource.R;
import com.pixelsstudio.opensource.zhihudailyopensource.adapter.NewsAdapter;
import com.pixelsstudio.opensource.zhihudailyopensource.base.BaseFragment;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;
import com.pixelsstudio.opensource.zhihudailyopensource.newsdetails.NewsDetailsActivity;
import com.pixelsstudio.opensource.zhihudailyopensource.view.RecyclerViewMaseter;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * Created by WongChen on 2017/1/8.
 */

public class CollectionFragment extends BaseFragment implements CollectionContract.View{
    private CollectionContract.Presenter mPresenter;
    private SwipeRefreshLayout srl;
    private RecyclerViewMaseter mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private List<ListNews.StoriesEntity> mDatas = new ArrayList<>();
    private ListNews.StoriesEntity mData;//存储点击item的数据

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newslist;
    }

    @Override
    public void initView() {
        srl = (SwipeRefreshLayout) mView.findViewById(R.id.srl);
        srl.setColorSchemeResources(R.color.colorPrimary);

        mRecyclerView = (RecyclerViewMaseter) mView.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mNewsAdapter = new NewsAdapter(mContext,mDatas);
        mNewsAdapter.setOnRecyclerViewItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int id) {
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        mNewsAdapter.setOnRecyclerViewItemLongClickListener(new NewsAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(ListNews.StoriesEntity data) {
                mData = data;
                showDialog();
            }
        });
        mRecyclerView.setAdapter(mNewsAdapter);
        mPresenter.getData(mContext);
    }

    @Override
    public void showRefreshView() {

    }

    @Override
    public void hideRefreshView() {

    }

    @Override
    public void upDateRecyclerView(List<ListNews.StoriesEntity> data) {
        mDatas.clear();
        mDatas.addAll(data);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDialog() {
        String[] items = {getResources().getString(R.string.delete)};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delete(mContext,mData);
            }
        });
        listDialog.show();
    }

    @Override
    public void showToast(String str) {
        Snackbar.make(mView, str, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void setPresenter(CollectionContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
