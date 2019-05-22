package com.xushiwei.kotlintest.app.utils

import com.bumptech.glide.request.RequestOptions
import com.xushiwei.kotlintest.R

object CommonUtils {

    fun getRequestions(): RequestOptions {
        return RequestOptions().placeholder(R.mipmap.moren_square)
            .fallback(R.mipmap.moren_square)
            .error(R.mipmap.moren_square)
    }

}