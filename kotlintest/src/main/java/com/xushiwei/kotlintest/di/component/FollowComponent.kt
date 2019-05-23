package com.xushiwei.kotlintest.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.xushiwei.kotlintest.di.module.FollowModule

import com.jess.arms.di.scope.FragmentScope
import com.xushiwei.kotlintest.mvp.ui.fragment.FollowFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/23/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = arrayOf(FollowModule::class), dependencies = arrayOf(AppComponent::class))
interface FollowComponent {
    fun inject(fragment: FollowFragment)
}
