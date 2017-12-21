package org.wenrong.news.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/10/5.
 */

public class NewsOpenHelper extends SQLiteOpenHelper {

    public static final String CREATETABLE = "create table news(id text primary key,author text,category text," +
            "data text,pic1 text,pic2 text, pic3 text,title text,url text)";

    private Context context;

    public NewsOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATETABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
