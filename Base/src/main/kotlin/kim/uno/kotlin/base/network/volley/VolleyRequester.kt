package kim.uno.kotlin.base.network.volley

import android.content.Context
import android.support.v4.util.ArrayMap
import android.text.TextUtils
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import kim.uno.kotlin.base.network.Params
import kim.uno.kotlin.base.util.GsonUtil
import kim.uno.kotlin.base.util.LogUtil
import java.net.URLEncoder

class VolleyRequester<T>(private val mClass: Class<*>?, method: Int, url: String, private val mListener: VolleyObjectListener<T>?) : Request<String>(method, url, null) {

    private var mCharSet = "UTF-8"

    constructor(method: Int, url: String, listener: VolleyObjectListener<T>?) : this(null, method, url, listener)

    private var mParams: ArrayMap<String, String>? = null

    fun setCharSet(charSet: String) {
        mCharSet = charSet
    }

    private var mHeaders = Params()

    fun setHeaders(headers: Params?) {
        if (headers != null) {
            mHeaders = headers
        }
    }

    fun setParams(params: ArrayMap<String, String>) {
        mParams = params
    }

    override fun getParams(): MutableMap<String, String>? {
        return mParams
    }

    override fun getBody(): ByteArray? {
        if (mParams?.size?:0 > 0) {
            return encodeParameters(mParams, mCharSet)
        }
        return null
    }

    private fun encodeParameters(params: ArrayMap<String, String>?, paramsEncoding: String): ByteArray {
        var encodedParams : String? = null

        params?.forEach {
            if (!TextUtils.isEmpty(it.key) && !TextUtils.isEmpty(it.value)) {
                encodedParams = (encodedParams?: "") + (if (encodedParams == null) "?" else "&") +
                        "${URLEncoder.encode(it.key, paramsEncoding)}=${URLEncoder.encode(it.value, paramsEncoding)}"
            }
        }
        LogUtil.simpleD(url + (encodedParams?: ""))
        return encodedParams.toString().toByteArray(charset(paramsEncoding))
    }

    override fun getHeaders(): Map<String, String>? {
        return mHeaders
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<String>? {
        val data = response.data.toString(charset(mCharSet))
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
    }

     override fun deliverResponse(response: String?) {
         try {
             mListener?.onResponse(if (mClass != null) GsonUtil.fromJson(response!!, mClass) as T else response as T, null)
         } catch (e: Exception) {
             deliverError(VolleyError(e))
         }
    }

    override fun deliverError(error: VolleyError?) {
        var errMsg : String = ""
        if (error?.networkResponse != null) {

            // statuc code
            if (error?.networkResponse?.statusCode?: 0 != 0)
                errMsg = "\nhttpStatusCode : " + error?.networkResponse?.statusCode

            // response body
            if (error?.networkResponse?.data != null)
                errMsg += "\nbody : " + error?.networkResponse?.data?.toString(charset(mCharSet))
        }

        LogUtil.e(url + errMsg)
        mListener?.onResponse(null, error)
    }

    fun apply(context: Context) {
        if (LogUtil.logEnable && method != Request.Method.POST) {
            var param : String? = null
            mParams?.forEach {
                if (!TextUtils.isEmpty(it.key) && !TextUtils.isEmpty(it.value)) {
                    param = (param?: "") + (if (param == null) "?" else "&") + "${it.key}=${it.value}"
                }
            }
            LogUtil.simpleD(url + (param?: ""))
        }

        VolleyQueue.getInstance(context)?.requestQueue?.add(this)
    }

}