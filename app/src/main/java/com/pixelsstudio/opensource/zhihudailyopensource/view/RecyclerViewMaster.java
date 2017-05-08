package com.pixelsstudio.opensource.zhihudailyopensource.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by WongChen on 2017/1/12.
 */

public class RecyclerViewMaster extends RecyclerView {
    private DividerDecoration mDividerDecoration;
    private View mEmptyView;
    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mEmptyView == null) {
                return;
            }
            Adapter adapter = getAdapter();
            if (adapter.getItemCount() == 0) {
                mEmptyView.setVisibility(VISIBLE);
                RecyclerViewMaster.this.setVisibility(GONE);
            } else {
                mEmptyView.setVisibility(GONE);
                RecyclerViewMaster.this.setVisibility(VISIBLE);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onChanged();
        }
    };


    public RecyclerViewMaster(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDividerDecoration = new DividerDecoration(context);
        addItemDecoration(mDividerDecoration);
    }

    public void setEmptyView(View emptyView) {
        RelativeLayout.LayoutParams rlParams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        mEmptyView = emptyView;
        ((ViewGroup) this.getRootView()).addView(mEmptyView,rlParams);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        adapter.registerAdapterDataObserver(mObserver);
        mObserver.onChanged();
    }


    public void setDivider(Drawable divider) {
        mDividerDecoration.setDivider(divider);
    }

    public void setmItemSpacing(int itemSpacing) {
        mDividerDecoration.setmItemSpacing(itemSpacing);
    }

    public void setDividerHeight(int dividerHeight) {
        mDividerDecoration.setDividerHeight(dividerHeight);
    }

    public void setmDividerWidth(int dividerWidth) {
        mDividerDecoration.setmDividerWidth(dividerWidth);
    }

    private class DividerDecoration extends RecyclerView.ItemDecoration {
        private final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        private Drawable mDivider;
        private int mDividerHeight = 0;
        private int mDividerWidth = 0;
        private int mItemSpacing = 0;

        public DividerDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            outRect.bottom = mItemSpacing / 2;
//            outRect.left = mItemSpacing / 2;
//            outRect.right = mItemSpacing / 2;
//            outRect.top = mItemSpacing / 2;
//            RecyclerViewMaseter.this.setPadding(mItemSpacing / 2, mItemSpacing / 2, mItemSpacing / 2, mItemSpacing / 2);

            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (mDivider == null) {
                return;
            }
            drawHorizontalLine(c, parent);
            drawVerticalLine(c, parent);
        }

        private void drawHorizontalLine(Canvas c, RecyclerView parent) {
//            final int childCount = parent.getChildCount();
//            final int width = parent.getWidth();
//            for (int childViewIndex = 0; childViewIndex < childCount; childViewIndex++) {
//                final View view = parent.getChildAt(childViewIndex);
//                int top = (int) ViewCompat.getY(view) + view.getHeight();
//                int offset = 0;//边距
//                mDivider.setBounds(offset, top, width - offset, top + mDividerHeight);
//                mDivider.draw(c);
//            }
        }

        private void drawVerticalLine(Canvas c, RecyclerView parent) {
//            final int childCount = parent.getChildCount();
//            final int height = parent.getHeight();
//            for (int childViewIndex = 0; childViewIndex < childCount; childViewIndex++) {
//                final View view = parent.getChildAt(childViewIndex);
//                int top = (int) ViewCompat.getX(view) + view.getWidth();
////            int offset = DisplayUtils.dp2Px(getContext(), 16);
//                int offset = 0;//边距
//                mDivider.setBounds(top, (int) ViewCompat.getY(view), top + mDividerHeight, (int) ViewCompat.getY(view) + height);
//                mDivider.draw(c);
//            }
        }


        public void setDivider(Drawable divider) {
            if (divider != null) {
                mDividerHeight = divider.getIntrinsicHeight();
            } else {
                mDividerHeight = 0;
            }
            mDivider = divider;
            invalidateItemDecorations();
        }

        public void setDividerHeight(int dividerHeight) {
            mDividerHeight = dividerHeight;
            invalidateItemDecorations();
        }

        public void setmDividerWidth(int dividerWidth) {
            mDividerWidth = dividerWidth;
            invalidateItemDecorations();
        }

        public void setmItemSpacing(int itemSpacing) {
            mItemSpacing = itemSpacing;
            invalidateItemDecorations();
        }
    }
}
