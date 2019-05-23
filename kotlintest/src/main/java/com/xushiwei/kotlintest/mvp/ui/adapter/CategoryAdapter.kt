package com.xushiwei.kotlintest.mvp.ui.adapter

import android.graphics.Typeface
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.http.imageloader.glide.GlideOptions
import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.app.application.MyApplication

class CategoryAdapter : BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    private var textTypeface: Typeface? = null

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.instance.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    constructor(data: MutableList<CategoryBean>?) : super(R.layout.item_category, data)


    override fun convert(helper: BaseViewHolder?, item: CategoryBean?) {
        helper?.setText(R.id.tv_category_name, "#${item?.name}")
        //设置方正兰亭细黑简体
        helper?.getView<TextView>(R.id.tv_category_name)?.typeface = textTypeface

        GlideArms.with(mContext).load(item?.bgPicture).apply(GlideOptions()).into(helper?.getView(R.id.iv_category)!!)

    }

}