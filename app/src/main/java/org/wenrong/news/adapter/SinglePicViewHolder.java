package org.wenrong.news.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.wenrong.news.R;
import org.wenrong.news.entity.NewsItem;
import org.wenrong.news.utils.GlideUtils;

/**
 * Created by Administrator on 2017/10/4.
 */

public class SinglePicViewHolder extends BaseViewHolder {

    ImageView mIV_pic1;
    TextView mTV_title;
    TextView mTV_time;
    TextView mTV_author;

    public SinglePicViewHolder(View itemView) {
        super(itemView);
        mIV_pic1 = (ImageView) itemView.findViewById(R.id.iv_news_pic);
        mTV_title = (TextView) itemView.findViewById(R.id.tv_news_title);
        mTV_author = (TextView) itemView.findViewById(R.id.tv_news_author);
        mTV_time = (TextView) itemView.findViewById(R.id.tv_news_time);

    }

    @Override
    public void setData(NewsItem item) {
        mTV_title.setText(item.getTitle());
        mTV_time.setText(item.getDate());
        mTV_author.setText(item.getAuthor_name());

        GlideUtils.getInstance().loadPicFromNet(itemView.getContext(), mIV_pic1, item.getThumbnail_pic_s());

    }
}
