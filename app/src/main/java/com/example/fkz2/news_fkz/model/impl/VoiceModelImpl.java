
package com.example.fkz2.news_fkz.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.fkz2.news_fkz.app.MyApplication;
import com.example.fkz2.news_fkz.model.LoadModel;
import com.example.fkz2.news_fkz.model.entity.Image;
import com.example.fkz2.news_fkz.presenter.OnLoadListener;
import com.example.fkz2.news_fkz.utils.ApiUtil;
import com.example.fkz2.news_fkz.utils.NetWorkUtil;
import com.example.fkz2.news_fkz.volley.VolleyHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class VoiceModelImpl implements LoadModel {
    @Override
    public void load(final OnLoadListener listener) {
        if (!NetWorkUtil.isNetworkAvailable(MyApplication.getContext())) {
            listener.networkError();
            return;
        }
        VolleyHttpClient.getInstance(MyApplication.getContext()).get(ApiUtil.MOMENT_VOICE, null
                , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Image> list = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("S1426236711448");
                            JSONArray topics = data.getJSONArray("topics");
                            JSONObject topic = topics.getJSONObject(0);
                            JSONArray docs = topic.getJSONArray("docs");
                            String book = docs.toString();
                            List<Image> imageList = new Gson().fromJson(book, new TypeToken<List<Image>>() {
                            }.getType());
                            Image image = new Image();
                            image.setType(Image.TYPE_BANNER);
                            image.setImgsrc(data.getString("banner"));
                            list.add(image);
                            list.addAll(imageList);
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
