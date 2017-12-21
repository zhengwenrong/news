package org.wenrong.news.presenter;

import android.content.Context;

import org.wenrong.news.entity.NewsItem;
import org.wenrong.news.iview.INewsView;
import org.wenrong.news.model.NewsModel;

import java.util.List;

import cn.wwah.basekit.base.presenter.BasePresenter;
import rx.Observer;

/**
 * Created by Administrator on 2017/10/4.
 */

public class NewsPresenter extends BasePresenter {

    INewsView iNewsView;
    NewsModel newsModel;


    public NewsPresenter(Context context,INewsView iNewsView) {
        super(context);
        this.iNewsView = iNewsView;
        newsModel = new NewsModel(context);
    }

    @Override
    public Throwable doError(Throwable throwable) {
        return null;
    }

    public void getNewsFromNet(String type){

        newsModel.findTouTiaoNews(type, new Observer<List<NewsItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<NewsItem> newsItems) {

                iNewsView.getNewsFromNet(newsItems);

            }
        });

    }

    public void loadMore(){



    }

    public void getNewsFromLocal(String type){

        List<NewsItem> newsFromLocal = newsModel.getNewsFromLocal(type);
        iNewsView.getNewsFromLocal(newsFromLocal);

    }

}
