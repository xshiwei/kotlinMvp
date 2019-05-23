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

import com.xushiwei.kotlintest.mvp.contract.FollowContract
import com.xushiwei.kotlintest.mvp.ui.adapter.FollowAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay

@FragmentScope
class FollowPresenter
@Inject
constructor(model: FollowContract.Model, rootView: FollowContract.View) :
    BasePresenter<FollowContract.Model, FollowContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    @Inject
    lateinit var mAdapter: FollowAdapter

    private var nextPageUrl: String? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onCreate() {
        getFollowList(true)
    }

    fun getFollowList(pullToRefresh: Boolean) {
        mModel.requestFollowList()
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
                        mAdapter.setEnableLoadMore(true)
                        mRootView.hideLoading()
                    } else {
                        mAdapter.loadMoreFail()
                    }
                    mAdapter.setEmptyView(R.layout.layout_error_view)
                }

                override fun onNext(t: HomeBean.Issue) {
                    if (pullToRefresh) {
                        nextPageUrl = t.nextPageUrl
                        mAdapter.setNewData(t.itemList)
                        mAdapter.setEnableLoadMore(true)
                        mRootView.hideLoading()
                        mAdapter.disableLoadMoreIfNotFullPage()
                    } else {
                        getMoreData()
                    }
                }
            })
    }

    private fun getMoreData() {
        mModel.loadMoreData(nextPageUrl!!)
            .subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(2, 2))
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object : ErrorHandleSubscriber<HomeBean.Issue?>(mErrorHandler) {

                override fun onError(t: Throwable) {
                    super.onError(t)
                    mAdapter.loadMoreFail()
                }

                override fun onNext(t: HomeBean.Issue) {
                    nextPageUrl = t.nextPageUrl
                    mAdapter.addData(t.itemList)
                    mAdapter.loadMoreComplete()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
