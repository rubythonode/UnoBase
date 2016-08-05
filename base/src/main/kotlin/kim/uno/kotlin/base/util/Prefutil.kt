package kim.uno.kotlin.base.util

import android.content.Context
import android.content.SharedPreferences
import kim.uno.base.R

class PrefUtil constructor(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.base_app_name), Context.MODE_APPEND)
    }

    fun getString(key: String, defValue: String? = null): String = sharedPreferences.getString(key, defValue)

    fun getBoolean(key: String, defValue: Boolean = false): Boolean = sharedPreferences.getBoolean(key, defValue)

    fun getFloat(key: String, defValue: Float = 0f): Float = sharedPreferences.getFloat(key, defValue)

    fun getInt(key: String, defValue: Int = 0): Int = sharedPreferences.getInt(key, defValue)

    fun getLong(key: String, defValue: Long = 0): Long = sharedPreferences.getLong(key, defValue)

    fun put(key: String, value: Any): Boolean {
        val editor = sharedPreferences.edit()
        if (value is String) editor.putString(key, value)
        else if (value is Boolean) editor.putBoolean(key, value)
        else if (value is Int) editor.putInt(key, value)
        else if (value is Long) editor.putLong(key, value)
        else if (value is Float) editor.putFloat(key, value)
        return editor.commit()
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key)
        sharedPreferences.edit().commit()
    }

    fun clear() {
        sharedPreferences.edit().clear()
        sharedPreferences.edit().commit()
    }

    companion object {
        private var instance: PrefUtil? = null

        @JvmStatic
        fun getInstance(context: Context): PrefUtil? {
            if (instance == null) instance = PrefUtil(context.applicationContext)
            return instance
        }
    }

}