package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pixelsstudio.opensource.zhihudailyopensource.R;
import com.pixelsstudio.opensource.zhihudailyopensource.adapter.NewsAdapter;
import com.pixelsstudio.opensource.zhihudailyopensource.adapter.NewsAdapterWrapper;
import com.pixelsstudio.opensource.zhihudailyopensource.base.BaseFragment;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;
import com.pixelsstudio.opensource.zhihudailyopensource.newsdetails.NewsDetailsActivity;
import com.pixelsstudio.opensource.zhihudailyopensource.view.RecyclerViewMaster;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * Created by WongChen on 2017/1/15.
 */

public class NewsListFragment extends BaseFragment implements NewsListContract.View {
    private NewsListContract.Presenter mPresenter;
    private SwipeRefreshLayout srl;
    private RecyclerViewMaster mRecyclerView;
    private NewsAdapterWrapper mNewsAdapterWrapper;
    private NewsAdapter mNewsAdapter;
    private List<ListNews.StoriesEntity> mDatas = new ArrayList<>();
    private ListNews.StoriesEntity mData;//存储点击item的数据

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newslist;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        srl = (SwipeRefreshLayout) mView.findViewById(R.id.srl);
        srl.setColorSchemeResources(R.color.colorPrimary);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData();
            }
        });

        mRecyclerView = (RecyclerViewMaster) mView.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mNewsAdapter = new NewsAdapter(mContext,mDatas,this);
        mNewsAdapter.setOnRecyclerViewItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ListNews.StoriesEntity data) {
                mPresenter.read(mContext,data);
                mNewsAdapterWrapper.notifyItemChanged(mDatas.indexOf(data));
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                intent.putExtra("id",data.getId());
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
        mNewsAdapterWrapper = new NewsAdapterWrapper(mNewsAdapter);
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.item_footer,mRecyclerView,false);
        mNewsAdapterWrapper.addFooterView(footerView);
        mNewsAdapterWrapper.setOnLoadMoreListener(new NewsAdapterWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });
        mRecyclerView.setAdapter(mNewsAdapterWrapper);
        mPresenter.getData();

    }

    @Override
    public void showRefreshView() {

    }


    @Override
    public void hideRefreshView() {
        srl.setRefreshing(false);
    }

    @Override
    public void upDateRecyclerView(List<ListNews.StoriesEntity> data) {
        mDatas.clear();
        mDatas.addAll(data);
        mNewsAdapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void showDialog() {
        String[] items = { getResources().getString(R.string.tab_starred),getResources().getString(R.string.tab_collection)};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        mPresenter.starred(mContext,mData);
                        break;
                    case 1:
                        mPresenter.collection(mContext,mData);
                        break;
                }
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
    public void setPresenter(@NonNull NewsListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
