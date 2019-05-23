package com.xushiwei.kotlintest.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.http.imageloader.glide.GlideOptions
import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.app.utils.durationFormat

class RankAdapter : BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {

    constructor(data: MutableList<HomeBean.Issue.Item>?) : super(R.layout.item_category_detail, data)

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {
        val itemData = item?.data
        val cover = itemData?.cover?.feed ?: ""
        GlideArms.with(mContext).load(cover).apply(GlideOptions()).into(helper?.getView(R.id.iv_image)!!)
        helper?.setText(R.id.tv_title, itemData?.title ?: "")
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)
        helper?.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")
    }

}