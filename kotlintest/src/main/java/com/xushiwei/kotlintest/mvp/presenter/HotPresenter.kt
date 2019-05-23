package com.xushiwei.kotlintest.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.HotContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay

@FragmentScope
class HotPresenter
@Inject
constructor(model: HotContract.Model, rootView: HotContract.View) :
    BasePresenter<HotContract.Model, HotContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onCreate() {
        getTabInfo()
    }

    private fun getTabInfo() {
        mModel.getTabInfoBean()
            .subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(2, 2))
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object : ErrorHandleSubscriber<TabInfoBean?>(mErrorHandler) {
                override fun onNext(t: TabInfoBean) {
                    mRootView.setTabInfo(t)
                }
            })
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
