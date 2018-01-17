package com.congdy.cscrollmenu

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget

/**
 * 图片加载器
 * Created by jack on 2017/2/17.
 */
class ImageLoader private constructor() {

    companion object {
        private var instance = ImageLoader()

        fun get(): ImageLoader {
            return instance
        }
    }

    fun loadImageByUri(context: Context?, uri: String?,l: SimpleTarget<Bitmap>) {
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .into(l)
    }
}
