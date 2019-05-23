package com.xushiwei.kotlintest.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.xushiwei.kotlintest.di.component.DaggerSplashComponent
import com.xushiwei.kotlintest.di.module.SplashModule
import com.xushiwei.kotlintest.mvp.contract.SplashContract
import com.xushiwei.kotlintest.mvp.presenter.SplashPresenter

import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.mvp.ui.base.BaseActivity

class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .splashModule(SplashModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_splash //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)  //去掉标题栏
        this.window
            .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)  //去掉信息栏
        super.onCreate(savedInstanceState)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter?.initSplash()
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

    override fun onBackPressed() {
        //不响应后退键
    }

    override fun getMyActivity(): AppCompatActivity {
        return this
    }
}
