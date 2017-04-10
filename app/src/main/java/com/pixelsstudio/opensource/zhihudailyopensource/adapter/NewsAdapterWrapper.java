package com.pixelsstudio.opensource.zhihudailyopensource.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WongChen on 2017/3/23.
 */

public class NewsAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private NewsAdapter mAdapter;
    private View mFooterView;

    public NewsAdapterWrapper(NewsAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            if (position == 0){
                mFooterView.setVisibility(View.GONE);
            }else{
                mFooterView.setVisibility(View.VISIBLE);
            }
            return ITEM_TYPE.FOOTER.ordinal();
        } else {
            return ITEM_TYPE.NORMAL.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.FOOTER.ordinal()) {
            return new RecyclerView.ViewHolder(mFooterView) {
            };
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            mOnLoadMoreListener.onLoadMore();
        } else {
            mAdapter.onBindViewHolder(((NewsAdapter.NewsViewHolder) holder), position);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() == 0 ? mAdapter.getItemCount() : mAdapter.getItemCount() + 1;
    }

    public void addFooterView(View footerView) {
        mFooterView = footerView;
    }


    enum ITEM_TYPE {
        FOOTER,
        NORMAL
    }

    private OnLoadMoreListener mOnLoadMoreListener = null;

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
