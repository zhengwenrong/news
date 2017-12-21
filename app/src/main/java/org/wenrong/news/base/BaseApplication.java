package org.wenrong.news.base;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;

import cn.wwah.common.DrawerToast;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/10/4.
 */

public class BaseApplication extends Application {

    public String getBaseUrl() {
        return baseUrl;
    }

    private String baseUrl = "http://v.juhe.cn/toutiao/";
    private Retrofit retrofit;
    private static BaseApplication myApp;
    private DrawerToast mToast;
    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("ViseLog")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树

        mToast = DrawerToast.getInstance(myApp);
        mToast.setDefaultTextColor(Color.WHITE);
        mToast.setDefaultBackgroundColor(Color.parseColor("#303F9F"), 200);

        //加入内存泄露检测工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static BaseApplication getApplication() {
        return myApp;
    }

    public  DrawerToast getmToast() {
        return mToast;
    }
}
