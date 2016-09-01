package kim.uno.kotlin.base.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val DEFAULT_FORMAT = "yyyyMMddHHmmssSSS"

    @JvmStatic
    fun parse(date: String?, fromFormat: String = DEFAULT_FORMAT): Date? {
        if (date == null || date.trim { it <= ' ' }.length == 0) return null

        try {
            val simpleDateFormat = SimpleDateFormat(fromFormat)
            return simpleDateFormat.parse(date)
        } catch (e: ParseException) { }

        return null
    }

    @JvmStatic
    fun format(date: String, toFormat: String): String? {
        return format(date, DEFAULT_FORMAT, toFormat)
    }

    @JvmStatic
    fun format(date: String, fromFormat: String, toFormat: String): String? {
        return format(parse(date, fromFormat), toFormat)
    }

    @JvmStatic
    fun format(date: Date?, toFormat: String): String? {
        if (date == null) return null

        try {
            val simpleDateFormat = SimpleDateFormat(toFormat, Locale.KOREA)
            return simpleDateFormat.format(date)
        } catch (e: ParseException) { }

        return null
    }

    @JvmStatic
    val now: String?
        get() = getNow(DEFAULT_FORMAT)

    @JvmStatic
    fun getNow(toFormat: String): String? {
        return format(Date(), toFormat)
    }

}