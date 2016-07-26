package kim.uno.kotlin.base.network.demo

import kim.uno.kotlin.base.network.Params
import retrofit2.http.GET
import retrofit2.http.QueryMap
import rx.Observable

interface DemoApi {

    @GET("/resturl")
    fun getDemo(@QueryMap params: Params): Observable<DomeResponse>

    class DomeResponse(var demos: Demo) {
        class Demo(var value: String)
    }

}