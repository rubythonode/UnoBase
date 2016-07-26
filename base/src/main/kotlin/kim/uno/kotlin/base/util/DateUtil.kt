package kim.uno.kotlin.base.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private val DEFAULT_FORMAT = "yyyyMMddHHmmssSSS"

fun parse(date: String?, fromFormat: String = DEFAULT_FORMAT): Date? {
    if (date == null || date.trim { it <= ' ' }.length == 0) return null

    try {
        val simpleDateFormat = SimpleDateFormat(fromFormat)
        return simpleDateFormat.parse(date)
    } catch (e: ParseException) {
        logE(e)
    }

    return null
}

fun format(date: String, toFormat: String): String? {
    return format(date, DEFAULT_FORMAT, toFormat)
}

fun format(date: String, fromFormat: String, toFormat: String): String? {
    return format(parse(date, fromFormat), toFormat)
}

fun format(date: Date?, toFormat: String): String? {
    if (date == null) return null

    try {
        val simpleDateFormat = SimpleDateFormat(toFormat, Locale.KOREA)
        return simpleDateFormat.format(date)
    } catch (e: ParseException) {
        logE(e)
    }

    return null
}

val now: String?
    get() = getNow(DEFAULT_FORMAT)

fun getNow(toFormat: String): String? {
    return format(Date(), toFormat)
}