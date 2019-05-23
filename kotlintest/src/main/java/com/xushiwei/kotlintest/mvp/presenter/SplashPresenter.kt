package com.xushiwei.kotlintest.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.SplashContract
import com.xushiwei.kotlintest.mvp.ui.activity.MainActivity
import android.content.Intent
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@ActivityScope
class SplashPresenter
@Inject
constructor(model: SplashContract.Model, rootView: SplashContract.View) :
    BasePresenter<SplashContract.Model, SplashContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun initSplash() {
        //设置默认3秒后进入
        Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object : Observer<Long?> {
                override fun onComplete() {
                    goMainActivity()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Long) {

                }

                override fun onError(e: Throwable) {
                    goMainActivity()
                }
            })
    }

    private fun goMainActivity() {
        mRootView.launchActivity(Intent(mRootView.getMyActivity(), MainActivity::class.java))
        mRootView.killMyself()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
