package com.xushiwei.kotlintest.di.module

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.xushiwei.kotlintest.mvp.contract.FollowContract
import com.xushiwei.kotlintest.mvp.model.FollowModel
import com.xushiwei.kotlintest.mvp.ui.adapter.FollowAdapter

@Module
//构建FollowModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class FollowModule(private val view: FollowContract.View) {
    @FragmentScope
    @Provides
    fun provideFollowView(): FollowContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideFollowModel(model: FollowModel): FollowContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideFollowList(): ArrayList<HomeBean.Issue.Item> {
        return arrayListOf()
    }

    @FragmentScope
    @Provides
    fun provideFollowAdapter(list: ArrayList<HomeBean.Issue.Item>): FollowAdapter {
        return FollowAdapter(list)
    }
}
