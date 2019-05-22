package com.xushiwei.kotlintest.mvp.widget

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xushiwei.kotlintest.app.utils.CommonUtils
import com.youth.banner.loader.ImageLoader

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context!!).load(path).apply(CommonUtils.getRequestions()).into(imageView!!)
    }

}