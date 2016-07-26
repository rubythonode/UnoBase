package kim.uno.kotlin.base.network.demo

import kim.uno.kotlin.base.network.BaseRetrofit
import kim.uno.kotlin.base.network.Params

class DemoRetrofit(api: DemoApi) : BaseRetrofit<DemoApi>(api, DemoApi::class.java) {

    override fun getBaseUrl(): String = "http://domain"

    fun getDemo(page : Int) = api.getDemo(Params().add("page", page.toString()))

}
