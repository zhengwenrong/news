package org.wenrong.news.api;

import org.wenrong.news.entity.NewsGson;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/4.
 */

public interface NewsService {

    @FormUrlEncoded
    @POST("index")
    Observable<NewsGson> getNews(@Field("type")String type, @Field("key")String key);

}
