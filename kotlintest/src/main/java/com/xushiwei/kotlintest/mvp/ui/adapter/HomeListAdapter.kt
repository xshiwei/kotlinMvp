package com.xushiwei.kotlintest.mvp.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jess.arms.http.imageloader.glide.GlideOptions
import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.app.utils.durationFormat
import com.xushiwei.kotlintest.mvp.widget.GlideImageLoader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import io.reactivex.Observable
import timber.log.Timber

class HomeListAdapter : BaseMultiItemQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {

    var bannerItemSize = 0

    fun setBannerSize(count: Int) {
        this.bannerItemSize = count
    }

    constructor(data: MutableList<HomeBean.Issue.Item?>) : super(data) {
        addItemType(HomeBean.Issue.Item.ONE, R.layout.item_home_banner)
        addItemType(HomeBean.Issue.Item.TWO, R.layout.item_home_header)
        addItemType(HomeBean.Issue.Item.THREE, R.layout.item_home_content)
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> {
                HomeBean.Issue.Item.ONE
            }
            mData[position + bannerItemSize - 1].type == "textHeader" -> {
                Timber.d("444444 position" + position)
                HomeBean.Issue.Item.TWO
            }
            else -> {
                Timber.d("555555 position" + position)
                HomeBean.Issue.Item.THREE
            }
        }
    }

    override fun getItemCount(): Int {
        return when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
    }

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {
        when (helper?.itemViewType) {
            HomeBean.Issue.Item.ONE -> {
                Timber.d("111111")
                val bannerItemData = mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                Observable.fromIterable(bannerItemData)
                    .subscribe { list ->
                        bannerFeedList.add(list?.data?.cover?.feed ?: "")
                        bannerTitleList.add(list?.data?.title ?: "")
                    }
                val banner = helper.getView<Banner>(R.id.banner)
                banner.run {
                    setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    setImageLoader(GlideImageLoader())
                    setImages(bannerFeedList)
                    setBannerAnimation(Transformer.DepthPage)
                    setBannerTitles(bannerTitleList)
                    isAutoPlay(true)
                    setDelayTime(2500)
                    setIndicatorGravity(BannerConfig.RIGHT)
                    start()
                }
            }
            HomeBean.Issue.Item.TWO -> {
                Timber.d("22222")
                helper.setText(R.id.tvHeader, mData[helper.layoutPosition + bannerItemSize - 1].data?.text ?: "")
            }
            HomeBean.Issue.Item.THREE -> {
                Timber.d("33333")
                val itemData =  mData[helper.layoutPosition + bannerItemSize - 1]?.data
                val defAvatar = R.mipmap.default_avatar
                val cover = itemData?.cover?.feed
                var avatar = itemData?.author?.icon
                var tagText: String? = "#"
                Glide.with(mContext).load(cover).apply(GlideOptions())
                    .into(helper.getView(R.id.iv_cover_feed))
                if (avatar.isNullOrEmpty()) {
                    Glide.with(mContext).load(defAvatar).apply(GlideOptions()).into(helper.getView(R.id.iv_avatar))
                } else {
                    Glide.with(mContext).load(avatar).apply(GlideOptions()).into(helper.getView(R.id.iv_avatar))
                }
                helper.setText(R.id.tv_title, itemData?.title ?: "")
                //遍历标签
                itemData?.tags?.take(4)?.forEach {
                    tagText += (it.name + "/")
                }
                // 格式化时间
                val timeFormat = durationFormat(itemData?.duration)
                tagText += timeFormat
                helper.setText(R.id.tv_tag, tagText!!)
                helper.setText(R.id.tv_category, "#" + itemData?.category)
            }
        }
    }

}