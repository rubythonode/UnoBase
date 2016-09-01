package kim.uno.kotlin.base.util

import android.content.Context
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

object LogUtil {

    @JvmStatic
    var logEnable: Boolean = false

    @JvmStatic
    var logTag: String = "UNO"

    @JvmStatic
    fun d(log: String) {
        if (logEnable) Log.d(logTag, getLogTrace(log))
    }

    @JvmStatic
    fun i(log: String) {
        if (logEnable) Log.i(logTag, getLogTrace(log))
    }

    @JvmStatic
    fun e(log: String) {
        if (logEnable) Log.e(logTag, getLogTrace(log))
    }

    @JvmStatic
    fun e(e: Exception) {
        if (logEnable) Log.e(logTag, getLogTrace(getStringFromThrowable(e)))
    }

    @JvmStatic
    fun e(e: Throwable) {
        if (logEnable) Log.e(logTag, getLogTrace(getStringFromThrowable(e)))
    }

    @JvmStatic
    fun simpleI(log: String) {
        if (logEnable) {
            Log.i(logTag, log)
        }
    }

    @JvmStatic
    fun simpleE(log: String) {
        if (logEnable) {
            Log.e(logTag, log)
        }
    }

    @JvmStatic
    fun simpleD(log: String) {
        if (logEnable) {
            Log.d(logTag, log)
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

    @JvmStatic
    fun saveCrashlog(context: Context, ex: Throwable) {
        val title = DateUtil.getNow("yyyy.MM.dd HH:mm:ss") + "\n"
        var recordLog: String = PrefUtil.getInstance(context)?.getString("crash")?: ""             // 누적된 로그
        val errorLog = title + getStringFromThrowable(ex) + "\n"                                    // 현재 로그
        recordLog += errorLog                                                                       // merge
        PrefUtil.getInstance(context)?.put("crash", recordLog)
    }
}