package com.xushiwei.kotlintest.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.xushiwei.kotlintest.di.component.DaggerMineComponent
import com.xushiwei.kotlintest.di.module.MineModule
import com.xushiwei.kotlintest.mvp.contract.MineContract
import com.xushiwei.kotlintest.mvp.presenter.MinePresenter

import com.xushiwei.kotlintest.R

class MineFragment : BaseFragment<MinePresenter>(), MineContract.View {
    companion object {
        fun newInstance(param: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            bundle.putString("param", param)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .mineModule(MineModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun setData(data: Any?) {

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

    }
}
