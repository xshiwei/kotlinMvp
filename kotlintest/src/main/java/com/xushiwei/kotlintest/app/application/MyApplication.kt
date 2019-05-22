package com.xushiwei.kotlintest.app.application

import com.jess.arms.base.App
import com.jess.arms.base.BaseApplication
import kotlin.properties.Delegates

class MyApplication : BaseApplication(), App {

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        var instance: MyApplication by Delegates.notNull()
    }

}