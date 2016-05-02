package kim.uno.base.util;

import android.content.Context;
import android.content.SharedPreferences;

import kim.uno.base.R;

import static android.content.SharedPreferences.Editor;

public class PrefUtil {

    static SharedPreferences preferences;

    static SharedPreferences getPrefs(Context context) {
        if (preferences == null) preferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_APPEND);
        return preferences;
    }

    private static Editor getEditor(Context context) {
        return getPrefs(context).edit();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String defValue) {
        return getPrefs(context).getString(key, defValue);
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getPrefs(context).getBoolean(key, defValue);
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, 0);
    }

    public static float getFloat(Context context, String key, float defValue) {
        return getPrefs(context).getFloat(key, defValue);
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, int defValue) {
        return getPrefs(context).getInt(key, defValue);
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, 0);
    }

    public static long getLong(Context context, String key, long defValue) {
        return getPrefs(context).getLong(key, defValue);
    }

    public static boolean put(Context context, String key, Object object) {
        Editor editor = getPrefs(context).edit();
        if (object instanceof String) editor.putString(key, (String) object);
        else if (object instanceof Boolean) editor.putBoolean(key, (boolean) object);
        else if (object instanceof Integer) editor.putInt(key, (int) object);
        else if (object instanceof Long) editor.putLong(key, (long) object);
        else if (object instanceof Float) editor.putFloat(key, (float) object);
        return editor.commit();
    }

    public static void remove(Context context, String key) {
        Editor editor = getPrefs(context).edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clear(Context context) {
        Editor editor = getPrefs(context).edit();
        editor.clear();
        editor.commit();
    }
}

