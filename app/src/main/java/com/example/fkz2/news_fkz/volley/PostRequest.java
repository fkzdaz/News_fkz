
package com.example.fkz2.news_fkz.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class PostRequest extends Request<String> {
    private Listener<String> listener;
    private ErrorListener errorListener;
    private Map<String, String> params;


    /**
     * 构造函数，初始化
     *
     * @param url           请求url
     * @param listener      请求成功监听
     * @param errorListener 请求失败监听
     */
    public PostRequest(String url, Map<String, String> params,
                       Listener<String> listener, ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.params = params;
        this.listener = listener;
        this.errorListener = errorListener;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, "utf-8");
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    /**
     * 失败时的回调方法
     *
     * @param error 错误信息
     */
    @Override
    public void deliverError(VolleyError error) {
        try {
            errorListener.onErrorResponse(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 成功时的回调方法
     *
     * @param response 成功响应字符串
     */
    @Override
    protected void deliverResponse(String response) {
        try {
            listener.onResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
