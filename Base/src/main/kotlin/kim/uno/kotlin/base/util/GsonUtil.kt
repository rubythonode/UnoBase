package kim.uno.kotlin.base.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

object GsonUtil {

    @JvmStatic
    val gson: Gson = GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create()

    @JvmStatic
    fun toJson(obj: Any?): String? {
        obj?.let { return gson.toJson(obj) }
        return null
    }

    @JvmStatic
    fun <E> fromJson(json: String, cls: Class<E>?) : E {
        return gson.fromJson(json, cls)
    }
}