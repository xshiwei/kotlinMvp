package com.xushiwei.kotlintest.mvp.ui.fragment

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.xushiwei.kotlintest.di.component.DaggerCategoryComponent
import com.xushiwei.kotlintest.di.module.CategoryModule
import com.xushiwei.kotlintest.mvp.contract.CategoryContract
import com.xushiwei.kotlintest.mvp.presenter.CategoryPresenter

import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.mvp.ui.adapter.CategoryAdapter
import com.xushiwei.kotlintest.mvp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.layout_recyclerview.*
import javax.inject.Inject

class CategoryFragment : BaseFragment<CategoryPresenter>(), CategoryContract.View,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var mAdapter: CategoryAdapter

    companion object {
        fun newInstance(param: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putString("param", param)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val layoutManager by lazy {
        GridLayoutManager(requireContext(), 2)
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerCategoryComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .categoryModule(CategoryModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.layout_recyclerview, container, false);
    }

    @Suppress("DEPRECATION")
    override fun initData(savedInstanceState: Bundle?) {
        ArmsUtils.configRecyclerView(mRecyclerView, layoutManager)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildPosition(view)
                val offset = ArmsUtils.dip2px(requireContext(), 2f)
                outRect.set(
                    if (position % 2 == 0) 0 else offset, offset,
                    if (position % 2 == 0) offset else 0, offset
                )
            }
        })

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
//        mAdapter.setEmptyView(R.layout.layout_loading_view, mRecyclerView)
//        mAdapter.emptyView.setOnClickListener {
//            mPresenter?.getFollowList(true)
//        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
        }
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
        mPresenter?.getFollowList(true)
    }
}
