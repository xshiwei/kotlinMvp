package com.xushiwei.kotlintest.di.module

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.xushiwei.kotlintest.mvp.contract.HomeContract
import com.xushiwei.kotlintest.mvp.model.HomeModel
import com.xushiwei.kotlintest.mvp.ui.adapter.HomeListAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/15/2019 14:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建HomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class HomeModule(private val view: HomeContract.View) {
    @FragmentScope
    @Provides
    fun provideHomeView(): HomeContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideHomeModel(model: HomeModel): HomeContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideHomeList(): MutableList<HomeBean.Issue.Item> {
        return ArrayList()
    }

    @FragmentScope
    @Provides
    fun provideHomeAdapter(list: MutableList<HomeBean.Issue.Item?>): HomeListAdapter {
        return HomeListAdapter(list)
    }
}
