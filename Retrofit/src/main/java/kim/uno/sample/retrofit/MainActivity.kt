package kim.uno.sample.retrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.schedulers.Schedulers
import kim.uno.kotlin.base.util.LogUtil
import kim.uno.kotlin.base.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var sampleService: SampleService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtil.logEnable = true

        initRetrofit()
    }

    fun initRetrofit() {
        bt_retrofit.setOnClickListener { requestByRetrofit() }
    }

    private fun requestByRetrofit() {

        if (sampleService == null) sampleService = SampleService.retrofit.create(SampleService::class.java)

        sampleService!!.get("test")
                .subscribeOn(Schedulers.io())
                .subscribe { ToastUtil.show(this, "${it.message}") }
    }

}
