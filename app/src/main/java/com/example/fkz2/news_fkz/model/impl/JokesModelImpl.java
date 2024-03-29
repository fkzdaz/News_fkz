
package com.example.fkz2.news_fkz.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.fkz2.news_fkz.app.MyApplication;
import com.example.fkz2.news_fkz.model.LoadModel;
import com.example.fkz2.news_fkz.model.entity.Jokes;
import com.example.fkz2.news_fkz.presenter.OnLoadListener;
import com.example.fkz2.news_fkz.utils.ApiUtil;
import com.example.fkz2.news_fkz.utils.NetWorkUtil;
import com.example.fkz2.news_fkz.volley.VolleyHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Smile Wei
 * @since 2016/5/26.
 */
public class JokesModelImpl implements LoadModel {
    @Override
    public void load(final OnLoadListener listener) {
        if (!NetWorkUtil.isNetworkAvailable(MyApplication.getContext())) {
            listener.networkError();
            return;
        }
        VolleyHttpClient.getInstance(MyApplication.getContext()).get(ApiUtil.MOMENT_JOKE, null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray topics = jsonObject.getJSONArray("段子");
                            String book = topics.toString();
                            List<Jokes> list = new Gson().fromJson(book, new TypeToken<List<Jokes>>() {
                            }.getType());
                            listener.onSuccess(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                });
    }

}
