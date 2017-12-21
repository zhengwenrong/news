package org.wenrong.news.iview

import org.wenrong.news.entity.NewsItem

/**
 * Created by Administrator on 2017/10/4.
 */
interface INewsView {

    fun getNewsFromNet(news:List<NewsItem>)

    fun loadMore(news:List<NewsItem>)

    fun refresh(news:List<NewsItem>)

    fun getNewsFromLocal(news:List<NewsItem>)
}