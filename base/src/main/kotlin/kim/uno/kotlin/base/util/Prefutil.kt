package kim.uno.kotlin.base.util

import android.content.Context
import android.content.SharedPreferences
import kim.uno.base.R

private var sharedPreferences: SharedPreferences? = null

private fun getPrefs(context: Context): SharedPreferences {
    if (sharedPreferences == null) sharedPreferences = context.getSharedPreferences(context.getString(R.string.base_app_name), Context.MODE_APPEND)
    return sharedPreferences as SharedPreferences
}

fun getPreferencesString(context: Context, key: String, defValue: String? = null): String = getPrefs(context).getString(key, defValue)
fun getPreferencesBoolean(context: Context, key: String, defValue: Boolean = false): Boolean = getPrefs(context).getBoolean(key, defValue)
fun getPreferencesFloat(context: Context, key: String, defValue: Float = 0f): Float = getPrefs(context).getFloat(key, defValue)
fun getPreferencesInt(context: Context, key: String, defValue: Int = 0): Int = getPrefs(context).getInt(key, defValue)
fun getPreferencesLong(context: Context, key: String, defValue: Long = 0): Long = getPrefs(context).getLong(key, defValue)

fun putPreferences(context: Context, key: String, value: Any): Boolean {
    val editor = getPrefs(context).edit()
    if (value is String) editor.putString(key, value)
    else if (value is Boolean) editor.putBoolean(key, value)
    else if (value is Int) editor.putInt(key, value)
    else if (value is Long) editor.putLong(key, value)
    else if (value is Float) editor.putFloat(key, value)
    return editor.commit()
}

fun removePreferences(context: Context, key: String) {
    getPrefs(context).edit().remove(key)
    getPrefs(context).edit().commit()
}

fun clearPreferences(context: Context) {
    getPrefs(context).edit().clear()
    getPrefs(context).edit().commit()
}