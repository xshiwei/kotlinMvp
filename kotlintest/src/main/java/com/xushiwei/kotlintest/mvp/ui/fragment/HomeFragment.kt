package com.xushiwei.kotlintest.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.xushiwei.kotlintest.di.component.DaggerHomeComponent
import com.xushiwei.kotlintest.di.module.HomeModule
import com.xushiwei.kotlintest.mvp.contract.HomeContract
import com.xushiwei.kotlintest.mvp.presenter.HomePresenter

import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.mvp.ui.adapter.HomeListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomePresenter>(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var mList: MutableList<HomeBean.Issue.Item>
    @Inject
    lateinit var mAdapter: HomeListAdapter

    companion object {
        fun newInstance(param: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString("param", param)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        ArmsUtils.configRecyclerView(mRecyclerView, linearLayoutManager)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.adapter = mAdapter
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
//        mAdapter.setEmptyView(R.layout.layout_loading_view, mRecyclerView)
//        mAdapter.emptyView.setOnClickListener {
//            mAdapter.setEnableLoadMore(false)
//            mPresenter?.getHomeList(true, 1)
//        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position] as HomeBean.Issue.Item
            Timber.e("c==" + item.type)
        }

        mAdapter.setOnLoadMoreListener({
            mRecyclerView.postDelayed({
                mPresenter?.getHomeList(false, 1)
            }, 300)
        }, mRecyclerView)
        mRefreshLayout.setOnRefreshListener(this)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //RecyclerView滚动的时候调用
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mAdapter.data.size > 1) {
                        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mAdapter.data
                        val item = itemList[currentVisibleItemPosition + mAdapter.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })
    }

    /*
    * @param data 当不需要参数时 {@code data} 可以为 {@code null}
    */
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
        mPresenter?.getHomeList(true, 1)
    }
}
