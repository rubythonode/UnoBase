package kim.uno.network;

import android.support.v4.util.ArrayMap;

public class Params extends ArrayMap<String, String> {

    public Params add(String key, String value) {
        super.put(key, value);
        return this;
    }

}
