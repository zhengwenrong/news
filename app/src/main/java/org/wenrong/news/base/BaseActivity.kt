package org.wenrong.news.base

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import cn.wwah.basekit.base.iview.IBaseView
import cn.wwah.common.ActivityManagerUtil
import cn.wwah.common.DrawerToast
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.LifecycleTransformer
import com.trello.rxlifecycle.RxLifecycle
import com.zhy.autolayout.AutoLayoutActivity
import rx.Observable
import rx.subjects.BehaviorSubject

/**
 * Created by Administrator on 2017/10/4.
 */

open class BaseActivity : AutoLayoutActivity(), ActivityLifecycleProvider, IBaseView {

    //ActiivityManagerUtils
    var activityManagerUtils:ActivityManagerUtil? = null
    var actiivty:Activity ? = null
    var subjects:BehaviorSubject<ActivityEvent> = BehaviorSubject.create()
    var mActivity:Activity?=null
    var mToast:DrawerToast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        activityManagerUtils = ActivityManagerUtil.getInstance()

        activityManagerUtils?.pushOneActivity(this)

        subjects.onNext(ActivityEvent.CREATE)

        mToast = DrawerToast.getInstance(this.applicationContext)

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showException(throwable: Throwable) {

    }

    override fun lifecycle(): Observable<ActivityEvent> {
        return subjects.asObservable()
    }

    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(subjects,event)
    }

    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycle.bindActivity(subjects)
    }

    override fun onStart() {
        super.onStart()
        this.subjects.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        super.onResume()
        this.subjects.onNext(ActivityEvent.RESUME)
    }

    override fun onStop() {
        super.onStop()
        this.subjects.onNext(ActivityEvent.STOP)
    }


    override fun onPause() {
        super.onPause()
        this.subjects.onNext(ActivityEvent.PAUSE)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.activityManagerUtils?.popOneActivity(this)
    }

}
