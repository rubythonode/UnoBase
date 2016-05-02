package kim.uno.base.network;

import com.android.volley.VolleyError;

public interface ObjectResponse<T> {
    public void onResponse(T response, VolleyError error);
}