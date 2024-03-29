
package com.example.fkz2.news_fkz.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


public class VolleyHttpClient {

    /**
     * 超时重连时间
     */
    public static final int TIMEOUT_MS = 10000;

    private Context context;

    private static RequestQueue requestQueue;

    private static VolleyHttpClient instance = null;

    private VolleyHttpClient(Context context) {
        this.context = context;
        init();
    }

    /**
     * 获取 VolleyHttpClient单例
     *
     * @param context 上下文
     * @return instance
     */
    public static VolleyHttpClient getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyHttpClient(context);
        }
        return instance;
    }

    /**
     * 初始化请求队列
     */
    private void init() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    /**
     * 获取请求队列
     */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            init();
        }
        return requestQueue;
    }


    /**
     * 定义post请求
     *
     * @param url           请求url
     * @param params        请求参数
     * @param listener      请求成功监听
     * @param errorListener 请求失败监听
     * @return 请求
     */
    public Request post(String url, Map<String, String> params,
                        Listener<String> listener, ErrorListener errorListener) {
        PostRequest postRequest = new PostRequest(url, params,
                listener, errorListener);
        setRequestRetryPolicy(postRequest, 0);
        return getRequestQueue().add(postRequest);
    }

    /**
     * 定义post请求
     *
     * @param timeOut       超时时间
     * @param url           请求url
     * @param params        请求参数
     * @param listener      请求成功监听
     * @param errorListener 请求失败监听
     * @return 请求
     */
    public Request post(int timeOut, String url, Map<String, String> params,
                        Listener<String> listener, ErrorListener errorListener) {
        PostRequest postRequest = new PostRequest(url, params,
                listener, errorListener);
        setRequestRetryPolicy(postRequest, 0, timeOut);
        return getRequestQueue().add(postRequest);
    }

    /**
     * 定义get请求
     *
     * @param url           请求url
     * @param params        请求参数
     * @param listener      请求成功监听
     * @param errorListener 请求失败监听
     * @return 请求
     */
    public Request get(String url, Map<String, String> params,
                       Listener<String> listener,
                       ErrorListener errorListener) {

        String endUrl = url + "?" + encodeParameters(params);

        Log.d("url", endUrl);

        GetRequest getRequest = new GetRequest(
                endUrl, listener, errorListener);
        setRequestRetryPolicy(getRequest, 1);
        return getRequestQueue().add(getRequest);
    }

    /**
     * 添加参数
     *
     * @param params 参数值
     * @return 参数字符串
     */
    public String encodeParameters(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(),
                        "UTF-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(),
                        "UTF-8"));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(
                    "Encoding not supported: " + "UTF-8", uee);
        }
    }

    /**
     * 设置重连时间和次数
     *
     * @param request request
     * @param retry   重连时间
     */
    private void setRequestRetryPolicy(Request<?> request, int retry) {
        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, retry,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * 设置重连时间和次数
     *
     * @param request request
     * @param retry   重连时间
     */
    private void setRequestRetryPolicy(Request<?> request, int retry, int timeOut) {
        request.setRetryPolicy(new DefaultRetryPolicy(timeOut, retry,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
