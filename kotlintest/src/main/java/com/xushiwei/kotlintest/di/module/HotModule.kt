package com.xushiwei.kotlintest.di.module

import android.support.v4.app.Fragment
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.xushiwei.kotlintest.mvp.contract.HotContract
import com.xushiwei.kotlintest.mvp.model.HotModel

@Module
//构建HotModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class HotModule(private val view: HotContract.View) {
    @FragmentScope
    @Provides
    fun provideHotView(): HotContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideHotModel(model: HotModel): HotContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideFragmentList(): ArrayList<Fragment> {
        return arrayListOf()
    }

    @FragmentScope
    @Provides
    fun provideTitleList(): ArrayList<String> {
        return arrayListOf()
    }
}
