package com.xushiwei.kotlintest.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.xushiwei.kotlintest.di.component.DaggerDiscoveryComponent
import com.xushiwei.kotlintest.di.module.DiscoveryModule
import com.xushiwei.kotlintest.mvp.contract.DiscoveryContract
import com.xushiwei.kotlintest.mvp.presenter.DiscoveryPresenter

import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.mvp.ui.adapter.MyTabLayoutAdapter
import com.xushiwei.kotlintest.mvp.ui.base.BaseFragment
import com.xushiwei.kotlintest.mvp.widget.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_discovery.*
import javax.inject.Inject


class DiscoveryFragment : BaseFragment<DiscoveryPresenter>(), DiscoveryContract.View {

    @Inject
    lateinit var mFragments: ArrayList<Fragment>
    @Inject
    lateinit var mTitles: ArrayList<String>

    companion object {
        fun newInstance(param: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val bundle = Bundle()
            bundle.putString("param", param)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerDiscoveryComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .discoveryModule(DiscoveryModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mTitles.add("关注")
        mTitles.add("分类")
        mFragments.add(FollowFragment.newInstance("关注"))
        mFragments.add(CategoryFragment.newInstance("分类"))

        mViewPager.adapter = MyTabLayoutAdapter(childFragmentManager, mFragments, mTitles)
        mTabLayout.setupWithViewPager(mViewPager)
        mViewPager.currentItem = 0
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
