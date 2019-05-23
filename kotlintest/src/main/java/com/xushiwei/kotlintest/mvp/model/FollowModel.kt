package com.xushiwei.kotlintest.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.FollowContract
import com.xushiwei.kotlintest.mvp.model.api.service.ApiService
import io.reactivex.Observable


@FragmentScope
class FollowModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), FollowContract.Model {
    override fun requestFollowList(): Observable<HomeBean.Issue> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getFollowInfo()
    }

    override fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getIssueData(url)
    }

    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun onDestroy() {
        super.onDestroy()
    }
}
