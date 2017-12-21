package org.wenrong.news.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/10/5.
 */

public class GlideUtils {

    public static GlideUtils instance;

    public static GlideUtils getInstance() {

        if (instance == null) {

            instance = new GlideUtils();

        }
        return instance;
    }

    private GlideUtils() {
    }

    public void loadPicFromNet(Context context, ImageView view, String url) {

        Glide.with(context).load(url).into(view);

    }

}
