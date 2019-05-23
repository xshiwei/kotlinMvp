package com.xushiwei.kotlintest.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.xushiwei.kotlintest.di.component.DaggerFollowComponent
import com.xushiwei.kotlintest.di.module.FollowModule
import com.xushiwei.kotlintest.mvp.contract.FollowContract
import com.xushiwei.kotlintest.mvp.presenter.FollowPresenter

import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.mvp.ui.adapter.FollowAdapter
import com.xushiwei.kotlintest.mvp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.layout_recyclerview.*
import javax.inject.Inject

class FollowFragment : BaseFragment<FollowPresenter>(), FollowContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var mAdapter: FollowAdapter

    private val linearLayoutManager by lazy {
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        fun newInstance(param: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putString("param", param)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerFollowComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .followModule(FollowModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.layout_recyclerview, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        ArmsUtils.configRecyclerView(mRecyclerView, linearLayoutManager)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.adapter = mAdapter
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
//        mAdapter.setEmptyView(R.layout.layout_loading_view, mRecyclerView)
//        mAdapter.emptyView.setOnClickListener {
//            mAdapter.setEnableLoadMore(false)
//            mPresenter?.getFollowList(true)
//        }
        mAdapter.setOnItemClickListener { adapter, view, position ->

        }

        mAdapter.setOnLoadMoreListener({
            mRecyclerView.postDelayed({
                mPresenter?.getFollowList(false)
            }, 300)
        }, mRecyclerView)
        mRefreshLayout.setOnRefreshListener(this)
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        mRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        mRefreshLayout.isRefreshing = false
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun onRefresh() {
        mAdapter.setEnableLoadMore(false)
        mPresenter?.getFollowList(true)
    }
}
