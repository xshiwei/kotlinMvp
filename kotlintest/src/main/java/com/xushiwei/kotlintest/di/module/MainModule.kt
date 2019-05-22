package com.xushiwei.kotlintest.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.xushiwei.kotlintest.mvp.contract.MainContract
import com.xushiwei.kotlintest.mvp.model.MainModel
import com.xushiwei.kotlintest.mvp.ui.fragment.DiscoveryFragment
import com.xushiwei.kotlintest.mvp.ui.fragment.HomeFragment
import com.xushiwei.kotlintest.mvp.ui.fragment.HotFragment
import com.xushiwei.kotlintest.mvp.ui.fragment.MineFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/14/2019 17:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MainModule(private val view: MainContract.View) {
    @ActivityScope
    @Provides
    fun provideMainView(): MainContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMainModel(model: MainModel): MainContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideHomeFragment(): HomeFragment {
        return HomeFragment.newInstance("home")
    }

    @ActivityScope
    @Provides
    fun provideDiscoverFragment(): DiscoveryFragment {
        return DiscoveryFragment.newInstance("discover")
    }

    @ActivityScope
    @Provides
    fun provideHotFragment(): HotFragment {
        return HotFragment.newInstance("hot")
    }

    @ActivityScope
    @Provides
    fun provideMineFragment(): MineFragment {
        return MineFragment.newInstance("mine")
    }


}
