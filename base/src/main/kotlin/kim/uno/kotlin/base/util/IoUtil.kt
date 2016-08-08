package kim.uno.kotlin.base.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings.Secure
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern

object IoUtil {

    private val EMAIL_PATTERN = Pattern.compile("^[a-z0-9._%+-]*@[a-z0-9.-]*\\.[a-z]{2,6}")
    private val HANGUL_BEGIN_UNICODE: Char = 44032.toChar() // 가
    private val HANGUL_END_UNICODE: Char = 55203.toChar() // 힣
    private val HANGUL_BASE_CHOSUNG_UNIT: Char = 588.toChar()
    private val HANGUL_BASE_JONGSUNG_UNIT: Char = 28.toChar()
    private val CHOSUNG_LIST = listOf('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
    private val JONGSUNG_LIST = listOf(' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')

    @JvmStatic
    fun isNull(string: String?): Boolean {
        return string == null || string.trim().length == 0 || string == "null"
    }

    @JvmStatic
    fun isNotNull(string: String?): Boolean {
        return string != null && string.trim().length > 0 && string != "null"
    }

    @JvmStatic
    fun isEmpty(list: List<*>?): Boolean {
        return list == null || list.size == 0
    }

    @JvmStatic
    fun isNotEmpty(list: List<*>?): Boolean {
        return list != null && list.size > 0
    }

    @JvmStatic
    fun isEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.size == 0
    }

    @JvmStatic
    fun isNotEmpty(map: Map<*, *>?): Boolean {
        return map != null && map.size > 0
    }

    @JvmStatic
    fun isContainsNull(vararg strings: String): Boolean {
        for (string in strings) {
            if (isNull(string)) return true
        }
        return false
    }

    @JvmStatic
    fun isContainsNotNull(vararg strings: String): Boolean {
        for (string in strings) {
            if (isNotNull(string)) return true
        }
        return false
    }

    @JvmStatic
    fun hideKeyboard(context: Context, view: View) {
        val mgr = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @JvmStatic
    fun verifyEmail(email: String): Boolean {
        return EMAIL_PATTERN.matcher(email).matches()
    }

    @JvmStatic
    fun getAndroidId(context: Context): String {
        var androidId = Secure.getString(context.contentResolver, Secure.ANDROID_ID)
        androidId = getMD5Hash(androidId)
        return androidId
    }

    @JvmStatic
    fun getMD5Hash(s: String): String? {
        var hash: String? = null

        try {
            var m: MessageDigest? = MessageDigest.getInstance("MD5")
            m?.update(s.toByteArray(), 0, s.length)
            hash = BigInteger(1, m?.digest()).toString(16)
        } catch (e: Exception) { }

        return hash
    }

    @JvmStatic
    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            if (model.startsWith(manufacturer))
                return capitalize(model)
            else
                return capitalize(manufacturer) + " " + model
        }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) return ""
        val first = s[0]
        if (Character.isUpperCase(first))
            return s
        else
            return Character.toUpperCase(first) + s.substring(1)
    }

    @JvmStatic
    val deviceVersion: String = Build.VERSION.RELEASE + ""

    @JvmStatic
    fun getAppVersion(context: Context): String? {
        var pInfo: PackageInfo? = null
        try {
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) { }

        return pInfo?.versionName
    }

    @JvmStatic
    fun makeChosung(name: String): String {
        var chosung = ""
        var cho: Char
        var afterCho: Char
        for (i in 0..name.length - 1) {
            cho = name[i]
            if (cho < HANGUL_BEGIN_UNICODE) {
                afterCho = cho
            } else {
                try {
                    afterCho = CHOSUNG_LIST[(cho - HANGUL_BEGIN_UNICODE) / HANGUL_BASE_CHOSUNG_UNIT.toInt()]
                } catch (e: Exception) {
                    afterCho = cho
                }

            }
            chosung += afterCho
        }
        return chosung
    }

    @JvmStatic
    fun makeChosungFirst(name: String): String {
        var chosung = ""
        val cho: Char
        val afterCho: Char
        if (name.length > 0) {
            cho = name[0]
            if (cho < HANGUL_BEGIN_UNICODE) {
                afterCho = cho
            } else {
                try {
                    afterCho = CHOSUNG_LIST[(cho - HANGUL_BEGIN_UNICODE) / HANGUL_BASE_CHOSUNG_UNIT.toInt()]
                } catch (e: Exception) {
                    afterCho = cho
                }

            }
            chosung += afterCho
        }
        return chosung
    }

    @JvmStatic
    fun makeJosa(word: String, none: String = "를", josa: String = "을"): String {
        var result = none
        try {
            val cBase = word[word.length - 1] - HANGUL_BEGIN_UNICODE
            val c1 = cBase / HANGUL_BASE_CHOSUNG_UNIT.toInt()                                                   // 초성
            val c2 = (cBase - HANGUL_BASE_CHOSUNG_UNIT.toInt() * c1) / HANGUL_BASE_JONGSUNG_UNIT.toInt()        // 중성
            val c3 = cBase - HANGUL_BASE_CHOSUNG_UNIT.toInt() * c1 - HANGUL_BASE_JONGSUNG_UNIT.toInt() * c2     // 종성
            result = if (JONGSUNG_LIST[c3] == ' ') none else josa
        } catch (e: Exception) {
        }

        return result
    }

}