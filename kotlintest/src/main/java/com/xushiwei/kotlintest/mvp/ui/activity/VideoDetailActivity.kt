package com.xushiwei.kotlintest.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.xushiwei.kotlintest.di.component.DaggerVideoDetailComponent
import com.xushiwei.kotlintest.di.module.VideoDetailModule
import com.xushiwei.kotlintest.mvp.contract.VideoDetailContract
import com.xushiwei.kotlintest.mvp.presenter.VideoDetailPresenter

import com.xushiwei.kotlintest.R

class VideoDetailActivity : BaseActivity<VideoDetailPresenter>(), VideoDetailContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerVideoDetailComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .videoDetailModule(VideoDetailModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_video_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {

    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
