
package com.example.fkz2.news_fkz.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

public class GetRequest extends Request<String> {
    private Listener<String> listener;
    private ErrorListener errorListener;

    /**
     * 构造函数，初始化
     *
     * @param url           请求url
     * @param listener      请求成功监听
     * @param errorListener 请求失败监听
     */
    public GetRequest(String url, Listener<String> listener,
                      ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.errorListener = errorListener;
        this.listener = listener;
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
}
