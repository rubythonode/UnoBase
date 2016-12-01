package kim.uno.sample.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import kim.uno.kotlin.base.util.GsonUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SampleService {

//    @GET("{child}")
//    fun get(@Path("child") path: String, @QueryMap map: Params?) : Call<String>
//
//    @GET
//    fun get(@QueryMap map: Params?) : Call<String>
//
//    @POST("{child}")
//    fun post(@Path("child") path: String, @QueryMap map: Params?) : Call<String>
//
//    @POST
//    fun post(@QueryMap map: Params?) : Call<String>

    @GET("{child}")
    fun get(@Path("child") path: String) : Observable<Sample>

    companion object {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://coocha-48979.firebaseapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.gson))
//                .client(OkHttpClient().newBuilder().addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build())
                .build()
    }

}