package com.xushiwei.kotlintest.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.xushiwei.kotlintest.di.module.MainModule

import com.jess.arms.di.scope.ActivityScope
import com.xushiwei.kotlintest.mvp.ui.activity.MainActivity



@ActivityScope
@Component(modules = arrayOf(MainModule::class), dependencies = arrayOf(AppComponent::class))
interface MainComponent {
    fun inject(activity: MainActivity)
}
