package com.xushiwei.kotlintest.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.RankContract
import com.xushiwei.kotlintest.mvp.model.api.service.ApiService
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/23/2019 18:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class RankModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RankContract.Model {
    override fun getRankList(url: String): Observable<HomeBean.Issue> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getIssueData(url)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
