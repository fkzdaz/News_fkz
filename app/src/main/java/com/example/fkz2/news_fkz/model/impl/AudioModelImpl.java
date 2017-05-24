
package com.example.fkz2.news_fkz.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.fkz2.news_fkz.app.MyApplication;
import com.example.fkz2.news_fkz.model.AudioLoadModel;
import com.example.fkz2.news_fkz.model.entity.Voice;
import com.example.fkz2.news_fkz.presenter.OnAudioLoadListener;
import com.example.fkz2.news_fkz.presenter.OnLoadListener;
import com.example.fkz2.news_fkz.utils.ApiUtil;
import com.example.fkz2.news_fkz.utils.DateTimeFormatUtil;
import com.example.fkz2.news_fkz.utils.NetWorkUtil;
import com.example.fkz2.news_fkz.volley.VolleyHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class AudioModelImpl implements AudioLoadModel {
    @Override
    public void load(final OnAudioLoadListener listener, final String text) {
        if (!NetWorkUtil.isNetworkAvailable(MyApplication.getContext())) {
            listener.networkError();
            return;
        }
        VolleyHttpClient.getInstance(MyApplication.getContext()).get(ApiUtil.MOMENT_VOICE_CONTENT.replace("docId", text), null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(text);
                            String time = jsonObject1.getString("ptime");
                            JSONArray video = jsonObject1.getJSONArray("video");
                            String book = video.toString();
                            List<Voice> list = new Gson().fromJson(book, new TypeToken<List<Voice>>() {
                            }.getType());
                            list.remove(list.size() - 1);
                            listener.onSuccess(list, "音频版(" + DateTimeFormatUtil.long2stringByFormatForZh(DateTimeFormatUtil.string2longSecondByFormat(time)) + ")");
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

    @Override
    public void load(OnLoadListener listener) {

    }
}
