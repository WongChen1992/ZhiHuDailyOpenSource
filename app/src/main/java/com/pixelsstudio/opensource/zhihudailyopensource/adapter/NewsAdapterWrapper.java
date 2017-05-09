package com.pixelsstudio.opensource.zhihudailyopensource.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pixelsstudio.opensource.zhihudailyopensource.R;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;

import java.util.List;

/**
 * Created by WongChen on 2017/3/23.
 */

public class NewsAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private NewsAdapter mAdapter;
    private View mFooterView;
    private List<ListNews.StoriesEntity> mData;
    private Context mContext;

    public NewsAdapterWrapper(Context context, NewsAdapter adapter, List<ListNews.StoriesEntity> data) {
        mAdapter = adapter;
        mData = data;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            if (position == 0) {
                mFooterView.setVisibility(View.GONE);
            } else {
                mFooterView.setVisibility(View.VISIBLE);
            }
            return ITEM_TYPE.FOOTER.ordinal();
        } else if (mData.get(position).getType() == 1) {
            return ITEM_TYPE.DATE.ordinal();
        } else {
            return ITEM_TYPE.NORMAL.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.FOOTER.ordinal()) {
            return new RecyclerView.ViewHolder(mFooterView) {
            };
        } else if (viewType == ITEM_TYPE.DATE.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_date, parent, false);
            NewsAdapterWrapper.DateViewHolder holder = new NewsAdapterWrapper.DateViewHolder(view);
            return holder;
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            mOnLoadMoreListener.onLoadMore();
        } else if (mData.get(position).getType() == 1) {
            String date = mData.get(position).getTitle();
            StringBuilder sb = new StringBuilder(date);
            sb.insert(5, "-");
            sb.insert(7, "-");
            date = sb.toString();
            ((DateViewHolder) holder).tv_date.setText(date);
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
        DATE,
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

    class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date;

        public DateViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
