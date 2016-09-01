package kim.uno.kotlin.base.network.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyQueue private constructor(context: Context) {

    val requestQueue: RequestQueue

    init {
        requestQueue = Volley.newRequestQueue(context)
    }

    companion object {
        private var instance: VolleyQueue? = null

        @JvmStatic
        fun getInstance(context: Context): VolleyQueue? {
            instance = instance?: VolleyQueue(context)
            return instance
        }
    }
}
