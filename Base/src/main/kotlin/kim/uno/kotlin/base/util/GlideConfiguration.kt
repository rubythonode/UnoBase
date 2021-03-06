package kim.uno.kotlin.base.util

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.GlideModule

class GlideConfiguration : GlideModule {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, 80 * 1024 * 1024))
    }

    override fun registerComponents(context: Context, glide: Glide) {
//        glide.register(GlideUrl::class.java, InputStream::class.java, GlideUrlLoader.Factory())
    }

}