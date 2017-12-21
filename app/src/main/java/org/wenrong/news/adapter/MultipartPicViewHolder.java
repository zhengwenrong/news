package org.wenrong.news.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vise.log.ViseLog;

import org.wenrong.news.R;
import org.wenrong.news.entity.NewsItem;
import org.wenrong.news.utils.GlideUtils;

/**
 * Created by Administrator on 2017/10/4.
 */

public class MultipartPicViewHolder extends BaseViewHolder {

    ImageView mIV_pic1;
    ImageView mIV_pic2;
    ImageView mIV_pic3;
    TextView mTV_title;
    TextView mTV_time;
    TextView mTV_author;

    public MultipartPicViewHolder(View itemView) {
        super(itemView);
        mTV_time = (TextView) itemView.findViewById(R.id.tv_news_time);
        mTV_author = (TextView) itemView.findViewById(R.id.tv_news_author);
        mTV_title = (TextView) itemView.findViewById(R.id.tv_news_title);

        mIV_pic1 = (ImageView) itemView.findViewById(R.id.iv_news_pic1);
        mIV_pic2 = (ImageView) itemView.findViewById(R.id.iv_news_pic2);
        mIV_pic3 = (ImageView) itemView.findViewById(R.id.iv_news_pic3);

    }

    @Override
    public void setData(NewsItem item) {

        ViseLog.d(item);

        mTV_title.setText(item.getTitle());
        mTV_time.setText(item.getDate());
        mTV_author.setText(item.getAuthor_name());

        GlideUtils.getInstance().loadPicFromNet(itemView.getContext(), mIV_pic1, item.getThumbnail_pic_s());
        GlideUtils.getInstance().loadPicFromNet(itemView.getContext(), mIV_pic2, item.getThumbnail_pic_s02());
        GlideUtils.getInstance().loadPicFromNet(itemView.getContext(), mIV_pic3, item.getThumbnail_pic_s03());

    }
}
