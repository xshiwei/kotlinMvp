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

import com.xushiwei.kotlintest.di.component.DaggerRankComponent
import com.xushiwei.kotlintest.di.module.RankModule
import com.xushiwei.kotlintest.mvp.contract.RankContract
import com.xushiwei.kotlintest.mvp.presenter.RankPresenter

import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.mvp.ui.adapter.RankAdapter
import com.xushiwei.kotlintest.mvp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.layout_recyclerview.*
import javax.inject.Inject


class RankFragment : BaseFragment<RankPresenter>(), RankContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var mAdapter: RankAdapter

    private var url: String? = null


    private val layoutManager by lazy {
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        fun newInstance(param: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            bundle.putString("param", param)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerRankComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .rankModule(RankModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.layout_recyclerview, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        ArmsUtils.configRecyclerView(mRecyclerView, layoutManager)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.adapter = mAdapter
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
//        mAdapter.setEmptyView(R.layout.layout_loading_view, mRecyclerView)
//        mAdapter.emptyView.setOnClickListener {
//            mPresenter?.getFollowList(true)
//        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
        }
        mRefreshLayout.setOnRefreshListener(this)

        url = arguments?.getString("param")
        mPresenter?.getRankList(true, url!!)
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
        mPresenter?.getRankList(true, url!!)
    }
}
