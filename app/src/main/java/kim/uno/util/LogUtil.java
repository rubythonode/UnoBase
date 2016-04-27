package kim.uno.util;

import android.content.Context;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import kim.uno.BuildConfig;

public class LogUtil {

	static boolean outputLog = BuildConfig.DEBUG;
	static String TAG = "UNO";

	public static void d(String log) {
		if (outputLog) Log.d(TAG, getLogTrace(log));
	}

    public static void i(String log) {
        if (outputLog) Log.i(TAG, getLogTrace(log));
    }

	public static void e(String log) {
		if (outputLog) Log.e(TAG, getLogTrace(log));
	}

	public static void e(Exception e) {
		if (outputLog) Log.e(TAG, getLogTrace(getStringFromThrowable(e)));
	}

    public static void e(Throwable e) {
        if (outputLog) Log.e(TAG, getLogTrace(getStringFromThrowable(e)));
    }

    public static void simple(String log) {
        simpleI(log);
    }

    public static void simpleI(String log) {
        if (outputLog) {
            Log.i(TAG, log);
        }
    }

    public static void simpleE(String log) {
        if (outputLog) {
            Log.e(TAG, log);
        }
    }

    public static void simpleD(String log) {
        if (outputLog) {
            Log.d(TAG, log);
        }
    }

    public static String getLogTrace(String log) {
        return getLogTrace(new Throwable().fillInStackTrace(), 2) + log;
    }

    public static String getLogTrace(Throwable stack, int from) {
        StackTraceElement[] trace = stack.getStackTrace();
        int index = trace[from].getClassName().lastIndexOf(".") + 1;
        String className = trace[from].getClassName().substring(index);
        String returnStr = String.format("%s - %s(%d) - ",
                className,
                trace[from].getMethodName(),
                trace[from].getLineNumber());
        return returnStr;
    }

    public static String getStringFromThrowable(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static void saveCrashlog(Context context, Throwable ex) {
        String title = DateUtil.getNow("yyyy.MM.dd HH:mm:ss") + "\n";
        String recordLog = PrefUtil.getString(context, "crash");				    	// 누적된 로그
        String errorLog = title + getStringFromThrowable(ex) + "\n";					// 현재 로그
        recordLog = recordLog == null ? errorLog : recordLog + errorLog;				// merge
        PrefUtil.put(context, "crash", recordLog);
    }

}
