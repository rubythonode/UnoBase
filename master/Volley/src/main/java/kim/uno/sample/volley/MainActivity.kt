package kim.uno.sample.volley

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import kim.uno.kotlin.base.network.volley.VolleyObjectListener
import kim.uno.kotlin.base.network.volley.VolleyRequester
import kim.uno.kotlin.base.util.LogUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtil.logEnable = true

        VolleyRequester(Sample::class.java, Request.Method.POST, "https://coocha-48979.firebaseapp.com/test", object: VolleyObjectListener<Sample> {
            override fun onResponse(response: Sample?, error: VolleyError?) {
                LogUtil.i("sample: " + response?.message)
            }
        }).apply(this)

    }

}
