package kim.uno.kotlin.base.network

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import kim.uno.kotlin.base.util.LogUtil
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL

class AsyncHttpConnector(val context: Context, val url: String, val listener: OnResponseListener?) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg Void: Void): Void? {
        try {
            val url = URL(this.url)
            LogUtil.i("" + this.url)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.doOutput = true
            conn.doInput = true
            conn.useCaches = false
            conn.defaultUseCaches = false

//            String cookie = conn.getHeaderField("Set-Cookie");
            val `is` = conn.inputStream
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))

            var line: String? = reader.readLine()
            while (line != null) {
                builder.append(line + "\n")
                line = reader.readLine()
            }

            if (listener != null) {
                if (context is Activity && !context.isFinishing) {
                    (context).runOnUiThread {
                        listener!!.onResponse(builder.toString())
                    }
                } else {
                    listener!!.onResponse(builder.toString())
                }
            }
        } catch (exception: MalformedURLException) {
            exception.printStackTrace()
        } catch (exception: ProtocolException) {
            exception.printStackTrace()
        } catch (io: IOException) {
            io.printStackTrace()
        }

        return null
    }

    interface OnResponseListener {
        fun onResponse(response: String)
    }

}
