package kim.uno.kotlin.base.util

import android.content.Context
import android.content.SharedPreferences
import kim.uno.base.R

private var sharedPreferences: SharedPreferences? = null

private fun getPreferences(context: Context): SharedPreferences {
    if (sharedPreferences == null) sharedPreferences = context.getSharedPreferences(context.getString(R.string.base_app_name), Context.MODE_APPEND)
    return sharedPreferences as SharedPreferences
}

fun getStringPreference(context: Context, key: String, defValue: String? = null): String = getPreferences(context).getString(key, defValue)
fun getBooleanPreference(context: Context, key: String, defValue: Boolean = false): Boolean = getPreferences(context).getBoolean(key, defValue)
fun getFloatPreference(context: Context, key: String, defValue: Float = 0f): Float = getPreferences(context).getFloat(key, defValue)
fun getIntPreference(context: Context, key: String, defValue: Int = 0): Int = getPreferences(context).getInt(key, defValue)
fun getLongPreference(context: Context, key: String, defValue: Long = 0): Long = getPreferences(context).getLong(key, defValue)

fun putPreference(context: Context, key: String, value: Any): Boolean {
    val editor = getPreferences(context).edit()
    if (value is String) editor.putString(key, value)
    else if (value is Boolean) editor.putBoolean(key, value)
    else if (value is Int) editor.putInt(key, value)
    else if (value is Long) editor.putLong(key, value)
    else if (value is Float) editor.putFloat(key, value)
    return editor.commit()
}

fun removePreference(context: Context, key: String) {
    getPreferences(context).edit().remove(key)
    getPreferences(context).edit().commit()
}

fun clearPreferences(context: Context) {
    getPreferences(context).edit().clear()
    getPreferences(context).edit().commit()
}