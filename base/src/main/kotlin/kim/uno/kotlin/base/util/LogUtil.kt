package kim.uno.kotlin.base.util

import android.content.Context
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

var LOGCAT_ENABLE: Boolean = false
val LOGCAT_TAG: String = "UNO"

fun logD(log: String) {
    if (LOGCAT_ENABLE) Log.d(LOGCAT_TAG, getLogTrace(log))
}

fun logI(log: String) {
    if (LOGCAT_ENABLE) Log.i(LOGCAT_TAG, getLogTrace(log))
}

fun logE(log: String) {
    if (LOGCAT_ENABLE) Log.e(LOGCAT_TAG, getLogTrace(log))
}

fun LogE(e: Exception) {
    if (LOGCAT_ENABLE) Log.e(LOGCAT_TAG, getLogTrace(getStringFromThrowable(e)))
}

fun logE(e: Throwable) {
    if (LOGCAT_ENABLE) Log.e(LOGCAT_TAG, getLogTrace(getStringFromThrowable(e)))
}

fun simpleI(log: String) {
    if (LOGCAT_ENABLE) {
        Log.i(LOGCAT_TAG, log)
    }
}

fun simpleE(log: String) {
    if (LOGCAT_ENABLE) {
        Log.e(LOGCAT_TAG, log)
    }
}

fun simpleD(log: String) {
    if (LOGCAT_ENABLE) {
        Log.d(LOGCAT_TAG, log)
    }
}

private fun getLogTrace(log: String): String {
    return getLogTrace(Throwable(), 2) + log
}

private fun getLogTrace(stack: Throwable, from: Int): String {
    val trace = stack.stackTrace
    val index = trace[from].getClassName().lastIndexOf(".") + 1
    val className = trace[from].getClassName().substring(index)
    val returnStr = String.format("%s - %s(%d) - ",
            className,
            trace[from].getMethodName(),
            trace[from].getLineNumber())
    return returnStr
}

private fun getStringFromThrowable(e: Throwable): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    e.printStackTrace(pw)
    return sw.toString()
}

fun saveCrashlog(context: Context, ex: Throwable) {
    val title = getNow("yyyy.MM.dd HH:mm:ss") + "\n"
    var recordLog: String? = getStringPreference(context, "crash")                        // 누적된 로그
    val errorLog = title + getStringFromThrowable(ex) + "\n"                    // 현재 로그
    recordLog = if (recordLog == null) errorLog else recordLog + errorLog                // merge
    putPreference(context, "crash", recordLog)
}