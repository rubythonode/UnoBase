package kim.uno.kotlin.base.network.retrofit.demo

import kim.uno.kotlin.base.network.Params
import kim.uno.kotlin.base.network.retrofit.BaseRetrofit

class DemoRetrofit(api: DemoApi) : BaseRetrofit<DemoApi>(api, DemoApi::class.java) {

    override fun getBaseUrl(): String = "http://domain"

    fun getDemo(page : Int) = api.getDemo(Params().add("page", page.toString()))

}
