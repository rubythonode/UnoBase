package kim.uno.base.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueManager {
    private static RequestQueueManager sInstance;

    private RequestQueue mRequestQueue;

    private RequestQueueManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueueManager getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new RequestQueueManager(context.getApplicationContext());
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
