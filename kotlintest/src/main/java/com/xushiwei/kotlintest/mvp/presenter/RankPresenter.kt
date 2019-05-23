package com.xushiwei.kotlintest.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.xushiwei.kotlintest.R
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.RankContract
import com.xushiwei.kotlintest.mvp.ui.adapter.RankAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


@FragmentScope
class RankPresenter
@Inject
constructor(model: RankContract.Model, rootView: RankContract.View) :
    BasePresenter<RankContract.Model, RankContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    @Inject
    lateinit var mAdapter: RankAdapter

    fun getRankList(pullToRefresh: Boolean, url: String) {
        mModel.getRankList(url)
            .subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(2, 2))
            .doOnSubscribe {
                if (pullToRefresh) {
                    mRootView.showLoading()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .doFinally {
                if (pullToRefresh) {
                    mRootView.hideLoading()
                }
            }
            .subscribe(object : ErrorHandleSubscriber<HomeBean.Issue?>(mErrorHandler) {

                override fun onError(t: Throwable) {
                    super.onError(t)
                    if (pullToRefresh) {
                        mRootView.hideLoading()
                    }
                    mAdapter.setEmptyView(R.layout.layout_error_view)
                }

                override fun onNext(t: HomeBean.Issue) {
                    if (pullToRefresh) {
                        mAdapter.setNewData(t.itemList)
                        mRootView.hideLoading()
                    }
                }
            })
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
