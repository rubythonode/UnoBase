package kim.uno.base.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

import kim.uno.base.util.GsonUtil;
import kim.uno.base.util.IoUtil;
import kim.uno.base.util.LogUtil;

public class Requester extends Request {

    public static final String CHARSET_EUCKR = "EUC-KR";
    public static final String CHARSET_UTF8 = "UTF-8";

    private String mCharSet = CHARSET_UTF8;
    private Class mClass;
    private ObjectResponse mListener;

    public Requester(int method, String url, ObjectResponse listener) {
        this(null, method, url, listener);
    }

    public Requester(Class cls, int method, String url, ObjectResponse listener) {
        super(method, url, null);
        this.mClass = cls;
        this.mListener = listener;
        setRetryPolicy(new RequestRetryPolicy());
    }

    private Map<String, String> mParams;

    public void setCharSet(String charSet) {
        mCharSet = charSet;
    }

    public void setParams(Map<String, String> params) {
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    private Map<String, String> mHeaders = Collections.emptyMap();

    public void setHeaders(Map<String, String> headers) {
        if (headers != null) {
            mHeaders = headers;
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                String value = entry.getValue();
                if (IoUtil.isNull(value)) value = "";
                encodedParams.append(URLEncoder.encode(value, mCharSet));
                encodedParams.append('&');
            }
            LogUtil.simpleE(getUrl() + "?" + encodedParams);
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String data = new String(response.data, mCharSet);
            LogUtil.simpleI(getUrl());
//            LogUtil.simpleE(data);
            if (mClass != null) return Response.success(GsonUtil.fromJson(data, mClass), HttpHeaderParser.parseCacheHeaders(response));
            else return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) { deliverError(new VolleyError(e)); }
        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Object response) {
        if (mListener != null) mListener.onResponse(response, null);
    }

    @Override
    public void deliverError(VolleyError error) {
        if (error != null) {
            String errMsg = error.toString();
            if (error.networkResponse != null) {
                try {
                    errMsg = "\nhttpStatusCode : " + error.networkResponse.statusCode;
                    errMsg += "\nbody : " + new String(error.networkResponse.data, mCharSet);
                } catch (Exception e) { LogUtil.e(e); }
            }
            LogUtil.e(getUrl() + errMsg);
        } else LogUtil.e(getUrl());

        if (mListener != null) mListener.onResponse(null, error);
    }

    private class RequestRetryPolicy extends DefaultRetryPolicy {

        /*
        30초 씩 두 번 Request
         */

        public static final int DEFAULT_TIMEOUT = 30000; // 30 second
        public static final int MAX_RETRIES = 0;
        public static final float BACK_OFF = 1f;

        public RequestRetryPolicy(){
            super(DEFAULT_TIMEOUT, MAX_RETRIES, BACK_OFF);
        }

        @Override
        public void retry(VolleyError error) throws VolleyError {
            super.retry(error);
            LogUtil.e("retry" + " " + getUrl());
        }
    }
}
