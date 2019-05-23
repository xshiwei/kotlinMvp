package com.xushiwei.kotlintest.mvp.ui.adapter

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.http.imageloader.glide.GlideOptions
import com.jess.arms.utils.ArmsUtils
import com.xushiwei.kotlintest.R

class FollowAdapter : BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {

    constructor(data: MutableList<HomeBean.Issue.Item>?) : super(R.layout.item_follow, data)

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {
        val headerData = item?.data?.header
        GlideArms.with(mContext).load(headerData?.icon!!).apply(GlideOptions()).into(helper?.getView(R.id.iv_avatar)!!)
        helper?.setText(R.id.tv_title, headerData.title)
        helper?.setText(R.id.tv_desc, headerData.description)
        val recyclerView = helper?.getView<RecyclerView>(R.id.fl_recyclerView)
        /**
         * 设置嵌套水平的 RecyclerView
         */
        val layoutManager = LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL, false)
        ArmsUtils.configRecyclerView(recyclerView, layoutManager)
        val mAdapter = FollowHorizontalAdapter(item.data.itemList)
        mAdapter.bindToRecyclerView(recyclerView)
        recyclerView?.adapter = mAdapter
    }

}