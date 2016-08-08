package kim.uno.kotlin.base.network

import android.support.v4.util.ArrayMap

open class Params : ArrayMap<String, String>() {

    open fun add(key: String, value: String): Params {
        super.put(key, value)
        return this
    }

}
