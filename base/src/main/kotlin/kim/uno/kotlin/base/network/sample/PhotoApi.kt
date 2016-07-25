package kim.uno.kotlin.base.network.sample

import kim.uno.kotlin.base.network.Params
import retrofit2.http.GET
import retrofit2.http.QueryMap
import rx.Observable

interface PhotoApi {

    @GET("somthing url")
    fun getPhotos(@QueryMap params: Params): Observable<PhotoResponse>

    class PhotoResponse(var photos: Photo) {
        class Photo(var image: String)
    }

}