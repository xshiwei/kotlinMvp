package com.xushiwei.kotlintest.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.CategoryContract
import com.xushiwei.kotlintest.mvp.model.api.service.ApiService
import io.reactivex.Observable

@FragmentScope
class CategoryModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CategoryContract.Model {

    override fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getCategory()
    }

    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun onDestroy() {
        super.onDestroy()
    }
}
