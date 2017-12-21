package org.wenrong.news.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.wenrong.news.api.NewsService;
import org.wenrong.news.base.BaseApplication;
import org.wenrong.news.db.NewsOpenHelper;
import org.wenrong.news.entity.NewsGson;
import org.wenrong.news.entity.NewsItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.wwah.basekit.base.model.BaseModel;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/10/4.
 */

public class NewsModel extends BaseModel {

    private NewsOpenHelper openHelper;

    public NewsModel(Context context) {
        super(context);
        openHelper = new NewsOpenHelper(mContext, "news.db", null, 1);
    }

    public static final String KEY = "3d1b3d642f6d45c77983832a3ee3555e";

    /**
     * 军事新闻
     */
    public static final String NEWS_TYPE_JUNSHI = "junshi";

    /**
     * 头条新闻
     */
    public static final String NEWS_TYPE_TOP = "top";

    /**
     * 社会新闻
     */
    public static final String NEWS_TYPE_SHEHUI = "shehui";

    /**
     * 国际新闻
     */
    public static final String NEWS_TYPE_GUOJI = "guoji";

    /**
     * 娱乐新闻
     */
    public static final String NEWS_TYPE_YULE = "yule";

    /**
     * 体育新闻
     */
    public static final String NEWS_TYPE_TIYU = "tiyu";

    /**
     * 科技新闻
     */
    public static final String NEWS_TYPE_KEJI = "keji";

    /**
     * 财经新闻
     */
    public static final String NEWS_TYPE_CAIJING = "caijing";

    /**
     * 时尚新闻
     */
    public static final String NEWS_TYPE_SHISHANG = "shishang";

    /**
     * 国内新闻
     */
    public static final String NEWS_TYPE_GUONEI = "guonei";


    public void findTouTiaoNews(final String type, Observer<List<NewsItem>> observer) {

        //访问网络
        NewsService newsService = BaseApplication.getApplication().getRetrofit().create(NewsService.class);

        Observable<List<NewsItem>> observable = newsService.getNews(type, KEY).map(new Func1<NewsGson, List<NewsItem>>() {
            @Override
            public List<NewsItem> call(NewsGson news) {

                List<NewsItem> newsList = new ArrayList<NewsItem>();

                List<NewsGson.ResultBean.DataBean> data = news.getResult().getData();

                for (NewsGson.ResultBean.DataBean bean : data) {

                    NewsItem item = new NewsItem();

                    item.setUniquekey(bean.getUniquekey());
                    item.setTitle(bean.getTitle());
                    item.setDate(bean.getDate());
                    item.setCategory(bean.getCategory());
                    item.setAuthor_name(bean.getAuthor_name());
                    item.setUrl(bean.getUrl());
                    item.setThumbnail_pic_s(bean.getThumbnail_pic_s());

                    if (null != bean.getThumbnail_pic_s02() && !"".equals(bean.getThumbnail_pic_s02())) {

                        item.setThumbnail_pic_s02(bean.getThumbnail_pic_s02());

                    }

                    if (null != bean.getThumbnail_pic_s03() && !"".equals(bean.getThumbnail_pic_s03())) {

                        item.setThumbnail_pic_s03(bean.getThumbnail_pic_s03());
                    }

                    newsList.add(item);

                }

                //缓存数据到本地
                saveNewsToDB(newsList);

                return newsList;
            }
        });

        subscribe(observable, observer);

    }

    private void saveNewsToDB(List<NewsItem> newsList) {

        SQLiteDatabase database = openHelper.getWritableDatabase();

        for (NewsItem item : newsList) {
            ContentValues values = new ContentValues();
            values.put("id", item.getUniquekey());
            values.put("author", item.getAuthor_name());
            values.put("category", item.getCategory());
            values.put("data", item.getDate());
            values.put("pic1", item.getThumbnail_pic_s());
            values.put("pic2", item.getThumbnail_pic_s02());
            values.put("pic3", item.getThumbnail_pic_s03());
            values.put("title", item.getTitle());
            values.put("url", item.getUrl());

            database.insert("news", null, values);
        }

        database.close();

    }

    public List<NewsItem> getNewsFromLocal(String type) {

        List<NewsItem> news = new ArrayList<>();

        type = getType(type);
        SQLiteDatabase database = openHelper.getReadableDatabase();

        Cursor cursor = database.query("news", new String[]{"author", "category", "data", "pic1", "pic2", "pic3", "title", "url"}, "category=?", new String[]{type}, null, null, "data desc");

        while (cursor.moveToNext()) {

            NewsItem item = new NewsItem();
            //item.setUniquekey(cursor.getString(cursor.getColumnIndex("_id")));
            item.setThumbnail_pic_s02(cursor.getString(cursor.getColumnIndex("pic2")));
            item.setThumbnail_pic_s(cursor.getString(cursor.getColumnIndex("pic1")));
            item.setThumbnail_pic_s03(cursor.getString(cursor.getColumnIndex("pic3")));
            item.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            item.setAuthor_name(cursor.getString(cursor.getColumnIndex("author")));
            item.setDate(cursor.getString(cursor.getColumnIndex("data")));
            item.setTitle(cursor.getString(cursor.getColumnIndex("title")));

            news.add(item);
        }

        database.close();
        return news;

    }

    private String getType(String type) {

        String newsType = "";

        switch (type) {

            case NEWS_TYPE_TOP:
                newsType = "头条";
                break;
            case NEWS_TYPE_SHEHUI:
                newsType = "社会";
                break;
            case NEWS_TYPE_GUONEI:
                newsType = "国内";
                break;
            case NEWS_TYPE_GUOJI:
                newsType = "国际";
                break;
            case NEWS_TYPE_YULE:
                newsType = "娱乐";
                break;
            case NEWS_TYPE_TIYU:
                newsType = "体育";
                break;
            case NEWS_TYPE_JUNSHI:
                newsType = "军事";
                break;
            case NEWS_TYPE_KEJI:
                newsType = "科技";
                break;
            case NEWS_TYPE_CAIJING:
                newsType = "财经";
                break;
            case NEWS_TYPE_SHISHANG:
                newsType = "时尚";
                break;
        }
        return newsType;


    }

    //获取缓存的文件
    private File getChacheFile(String type) {

        File cacheDir = mContext.getCacheDir();
        String filaname = type + "news.txt";
        File cahcheFile = new File(cacheDir, filaname);

        if (!cahcheFile.exists()) {

            try {
                cacheDir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return cahcheFile;
    }


}
