package org.wenrong.news.ui.fragments;

import org.wenrong.news.base.BaseFragment;
import org.wenrong.news.model.NewsModel;

/**
 * Created by Administrator on 2017/10/5.
 */

public class CaiJingNewsFragment extends BaseFragment {


    @Override
    public String setNewsType() {
        return NewsModel.NEWS_TYPE_CAIJING;
    }
}
