package kim.uno.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class GsonUtil {

    static Gson mGson;

    public static Gson getInstance() {
        if (mGson == null)
            mGson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        return mGson;
    }

    public static String toJson(Object object) {
        return getInstance().toJson(object);
    }

    public static <E> E fromJson(String json, Class<E> cls) {
        return getInstance().fromJson(json, cls);
    }
}
