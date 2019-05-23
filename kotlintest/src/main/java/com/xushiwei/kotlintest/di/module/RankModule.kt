package com.xushiwei.kotlintest.di.module

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.xushiwei.kotlintest.mvp.contract.RankContract
import com.xushiwei.kotlintest.mvp.model.RankModel
import com.xushiwei.kotlintest.mvp.ui.adapter.RankAdapter


@Module
//构建RankModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class RankModule(private val view: RankContract.View) {
    @FragmentScope
    @Provides
    fun provideRankView(): RankContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideRankModel(model: RankModel): RankContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideRankList(): ArrayList<HomeBean.Issue.Item> {
        return arrayListOf()
    }

    @FragmentScope
    @Provides
    fun provideRankAdapter(list: ArrayList<HomeBean.Issue.Item>): RankAdapter {
        return RankAdapter(list)
    }
}
