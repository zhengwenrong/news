package org.wenrong.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.wenrong.news.entity.NewsItem;

/**
 * Created by Administrator on 2017/10/4.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void  setData(NewsItem item);

}
