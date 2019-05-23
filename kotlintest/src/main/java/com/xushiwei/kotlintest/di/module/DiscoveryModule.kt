package com.xushiwei.kotlintest.di.module

import android.support.v4.app.Fragment
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.xushiwei.kotlintest.mvp.contract.DiscoveryContract
import com.xushiwei.kotlintest.mvp.model.DiscoveryModel

@Module
//构建DiscoveryModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class DiscoveryModule(private val view: DiscoveryContract.View) {
    @FragmentScope
    @Provides
    fun provideDiscoveryView(): DiscoveryContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideDiscoveryModel(model: DiscoveryModel): DiscoveryContract.Model {
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
