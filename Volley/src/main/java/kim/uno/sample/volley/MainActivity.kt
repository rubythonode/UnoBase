package kim.uno.sample.volley

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import kim.uno.kotlin.base.network.Params
import kim.uno.kotlin.base.network.volley.VolleyObjectListener
import kim.uno.kotlin.base.network.volley.VolleyRequester
import kim.uno.kotlin.base.util.LogUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtil.logEnable = true

        var requester = VolleyRequester(Request.Method.GET, "http://google.com", object : VolleyObjectListener<String> {
            override fun onResponse(response: String?, error: VolleyError?) {
                tv_response.text = "" + response
            }
        })

        requester.setParams(Params().add("key1", "value").add("key2", "value"))
        requester.setHeaders(Params().add("key1", "value").add("key2", "value"))
        requester.apply(this)
    }

}
