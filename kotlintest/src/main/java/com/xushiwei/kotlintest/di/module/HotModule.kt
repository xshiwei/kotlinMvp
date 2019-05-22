package com.xushiwei.kotlintest.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.xushiwei.kotlintest.mvp.contract.HotContract
import com.xushiwei.kotlintest.mvp.model.HotModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/15/2019 14:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
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
}
