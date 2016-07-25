package kim.uno.kotlin.base.network.sample

import kim.uno.kotlin.base.network.BaseRetrofit
import kim.uno.kotlin.base.network.Params

class PhotoRetrofit(api: PhotoApi) : BaseRetrofit<PhotoApi>(api, PhotoApi::class.java) {

    override fun getBaseUrl(): String = "http://uno.kim"

    fun getPhotos(page : Int) = api.getPhotos(Params().add("page", page.toString()))

}
