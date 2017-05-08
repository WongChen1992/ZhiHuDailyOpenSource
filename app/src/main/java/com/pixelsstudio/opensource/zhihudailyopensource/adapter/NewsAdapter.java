package com.pixelsstudio.opensource.zhihudailyopensource.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pixelsstudio.opensource.zhihudailyopensource.R;
import com.pixelsstudio.opensource.zhihudailyopensource.db.SQLiteDBHelper;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;
import com.pixelsstudio.opensource.zhihudailyopensource.newslist.NewsListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WongChen on 2017/2/22.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<ListNews.StoriesEntity> mDatas;
    private Context mContext;
    private Fragment mFragment;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener = null;

    public NewsAdapter(Context context, List<ListNews.StoriesEntity> data, Fragment fm) {
        mContext = context;
        mDatas = data;
        mFragment = fm;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final NewsAdapter.NewsViewHolder holder, int position) {
        //首页列表将进行是否为已读文章判断
        if (mFragment.getClass().equals(NewsListFragment.class)){
            if(SQLiteDBHelper.getInstens(mContext).query(SQLiteDBHelper.TABLE_READ,new String[]{String.valueOf(mDatas.get(position).getId())})){
                holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.colorTextCaption));
            }else{
                holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.colorTextTitle));
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRecyclerViewItemClickListener != null) {
                    mOnRecyclerViewItemClickListener.onItemClick((ListNews.StoriesEntity) ((HashMap) v.getTag()).get("data"));
                }
            }
        });
        holder.itemView.setOnLongClickListener(this);
        holder.tv_title.setText(mDatas.get(position).getTitle());
        holder.iv_poster.setImageURI(Uri.parse(mDatas.get(position).getImages().get(0)));
        Map<String, Object> data = new HashMap<>();
        data.put("data", mDatas.get(position));
        holder.itemView.setTag(data);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewItemClickListener != null) {
            mOnRecyclerViewItemClickListener.onItemClick((ListNews.StoriesEntity) ((HashMap) v.getTag()).get("data"));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnRecyclerViewItemLongClickListener != null) {
            mOnRecyclerViewItemLongClickListener.onItemLongClick((ListNews.StoriesEntity) ((HashMap) v.getTag()).get("data"));
        }
        return true;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnRecyclerViewItemClickListener = listener;
    }

    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        mOnRecyclerViewItemLongClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(ListNews.StoriesEntity data);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(ListNews.StoriesEntity data);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        SimpleDraweeView iv_poster;

        public NewsViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            iv_poster = (SimpleDraweeView) view.findViewById(R.id.iv_poster);
        }
    }
}
