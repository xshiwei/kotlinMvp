package com.xushiwei.kotlintest.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.HomeContract
import com.xushiwei.kotlintest.mvp.model.api.service.ApiService
import io.reactivex.Observable

@FragmentScope
class HomeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HomeContract.Model {
    override fun getmoreData(url: String): Observable<HomeBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getMoreHomeData(url)
    }

    override fun getHomeBean(num: Int): Observable<HomeBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getFirstHomeData(num)
    }

    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun onDestroy() {
        super.onDestroy()
    }


}
