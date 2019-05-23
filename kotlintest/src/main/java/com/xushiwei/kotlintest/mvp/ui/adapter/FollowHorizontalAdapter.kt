package com.xushiwei.kotlintest.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.http.imageloader.glide.GlideOptions
import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.app.utils.durationFormat
import timber.log.Timber

class FollowHorizontalAdapter : BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {

    constructor(data: MutableList<HomeBean.Issue.Item>?) : super(R.layout.item_follow_horizontal, data)

    override fun convert(helper: BaseViewHolder?, data: HomeBean.Issue.Item?) {
        val horizontalItemData = data?.data
        GlideArms.with(mContext).load(data?.data?.cover?.feed).apply(GlideOptions())
            .into(helper?.getView(R.id.iv_cover_feed)!!)

        //横向 RecyclerView 封页图下面标题
        helper?.setText(R.id.tv_title, horizontalItemData?.title ?: "")
        // 格式化时间
        val timeFormat = durationFormat(horizontalItemData?.duration)
        //标签
        with(helper) {
            Timber.d("horizontalItemData===title:${horizontalItemData?.title}tag:${horizontalItemData?.tags?.size}")

            if (horizontalItemData?.tags != null && horizontalItemData.tags.size > 0) {
                this!!.setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            } else {
                this!!.setText(R.id.tv_tag, "#$timeFormat")
            }
        }
    }

}