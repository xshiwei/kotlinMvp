package com.xushiwei.kotlintest.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.xushiwei.kotlintest.R
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.CategoryContract
import com.xushiwei.kotlintest.mvp.ui.adapter.CategoryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay

@FragmentScope
class CategoryPresenter
@Inject
constructor(model: CategoryContract.Model, rootView: CategoryContract.View) :
    BasePresenter<CategoryContract.Model, CategoryContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    @Inject
    lateinit var mAdapter: CategoryAdapter

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onCreate() {
        getFollowList(true)
    }

    fun getFollowList(pullToRefresh: Boolean) {
        mModel.getCategoryData()
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
            .subscribe(object : ErrorHandleSubscriber<ArrayList<CategoryBean>?>(mErrorHandler) {

                override fun onError(t: Throwable) {
                    super.onError(t)
                    if (pullToRefresh) {
                        mRootView.hideLoading()
                    }
                    mAdapter.setEmptyView(R.layout.layout_error_view)
                }

                override fun onNext(t: ArrayList<CategoryBean>) {
                    if (pullToRefresh) {
                        mAdapter.setNewData(t)
                        mRootView.hideLoading()
                    }
                }
            })
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
