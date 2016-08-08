package kim.uno.kotlin.base.network.volley

import com.android.volley.VolleyError

interface VolleyObjectListener<T> {
    fun onResponse(response: T?, error: VolleyError?)
}