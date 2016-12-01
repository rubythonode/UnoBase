package kim.uno.sample.retrofit

import kim.uno.kotlin.base.util.GsonUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ObjectCallBack<T> : Callback<String> {

    fun parseTo() : Class<T>

    override fun onResponse(call: Call<String>?, response: Response<String>?) {
        onResponse(GsonUtil.fromJson(response?.body()?: "", parseTo()), null)
    }

    override fun onFailure(call: Call<String>?, t: Throwable?) {
        onResponse(null, t)
    }

    fun onResponse(response: T?, t: Throwable?)

}
