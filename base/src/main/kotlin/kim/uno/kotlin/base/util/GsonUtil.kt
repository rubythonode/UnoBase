package kim.uno.kotlin.base.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

var gson: Gson = GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create()

fun toJson(obj: Any): String {
    return gson.toJson(obj)
}

fun <E> fromJson(json: String, cls: Class<E>) : E {
    return gson.fromJson(json, cls)
}