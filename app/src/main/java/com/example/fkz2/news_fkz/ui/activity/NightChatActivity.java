/*
 * Copyright (c) 2016 [zhiye.wei@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.fkz2.news_fkz.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.R;
import com.example.fkz2.news_fkz.model.entity.Image;
import com.example.fkz2.news_fkz.ui.adapter.BooksAdapter;
import com.example.fkz2.news_fkz.utils.ApiUtil;
import com.example.fkz2.news_fkz.utils.Constants;
import com.example.fkz2.news_fkz.utils.NetWorkUtil;
import com.example.fkz2.news_fkz.utils.ToastUtil;
import com.example.fkz2.news_fkz.volley.VolleyHttpClient;
import com.example.fkz2.news_fkz.widget.DividerDecoration;
import com.example.fkz2.news_fkz.widget.LoadingView;
import com.example.fkz2.news_fkz.widget.recyclerviewhelper.OnStartDragListener;
import com.example.fkz2.news_fkz.widget.recyclerviewhelper.SimpleItemTouchHelperCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;



public class NightChatActivity extends BaseSwipeActivity implements OnStartDragListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.loading_view)
    LoadingView loadingView;

    private List<Image> list;
    private Activity activity;
    private Context context;
    private BooksAdapter adapter;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_chat);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        int source = getIntent().getIntExtra(Constants.EXTRA_SOURCE_ID, 1);
        String title = "深夜畅聊";
        String url = ApiUtil.MOMENT_NIGHT_CHAT;
        String docsId = "S1426235992757";
        switch (source) {
            case Constants.SOURCE_FOR_NIGHT_CHAT:
                title = "深夜畅聊";
                url = ApiUtil.MOMENT_NIGHT_CHAT;
                docsId = "S1426235992757";
                break;
            case Constants.SOURCE_FOR_DRIVER_STUDY:
                title = "老司机学堂";
                url = ApiUtil.MOMENT_DRIVER_STUDY;
                docsId = "S1460094273198";
                break;
            case Constants.SOURCE_FOR_GAME:
                title = "小游戏";
                url = ApiUtil.MOMENT_GAME;
                docsId = "S1426237757194";
                break;
            case Constants.SOURCE_FOR_TODAY_VOICE:
                title = "今日之声";
                url = ApiUtil.MOMENT_TODAY_VOICE;
                docsId = "S1426233520789";
                break;
            case Constants.SOURCE_FOR_SEVEN_NEWS:
                title = "新闻7点整";
                url = ApiUtil.MOMENT_SEVEN_NEWS;
                docsId = "S1426238687771";
                break;
            case Constants.SOURCE_FOR_FAT_CHAT:
                title = "胖编怪谈";
                url = ApiUtil.MOMENT_FAT_CHAT;
                docsId = "S1426235566308";
                break;
            case Constants.SOURCE_FOR_UP:
                title = "小编推贱";
                url = ApiUtil.MOMENT_UP;
                docsId = "S1449617711090";
                break;

        }
        setToolBar(toolbar, title);
        loadingView.setHideBottom();

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(0);
                    }
                });
            }
        });

        activity = this;
        context = activity.getApplicationContext();
        list = new ArrayList<>();
        refreshLayout.setColorSchemeResources(R.color.loading_color);
        final String finalUrl = url;
        final String finalDocsId = docsId;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBooks(null, false, finalUrl, finalDocsId);
            }
        });

        DividerDecoration.Builder builder = new DividerDecoration.Builder(context);
        builder.setColorResource(R.color.divider_color)
                .setHeight(1f)
                .setLeftPadding(R.dimen.divider_padding_left);
        recyclerView.addItemDecoration(builder.build());

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new BooksAdapter(activity, list);
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter, ItemTouchHelper.START));
        helper.attachToRecyclerView(recyclerView);

        loadingView.setLoading();
        loadingView.setOnReLoadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.setLoading();
                getBooks(loadingView, false, finalUrl, finalDocsId);
            }
        });

        adapter.setOnItemClickListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent();
                intent.setClass(activity, ImageActivity.class);
                intent.putExtra(Constants.EXTRA_DOCS_ID, list.get(position).getDocid());
                startActivity(intent);
            }
        });
        getBooks(loadingView, false, url, docsId);
    }

    private void getBooks(final LoadingView loadingView, final boolean isLoad, final String url, final String docsId) {
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            ToastUtil.showSortToast(context, "哎呀，网络开小差啦～～～");
            refreshLayout.setRefreshing(false);
            if (loadingView != null)
                loadingView.setLoadError();
            return;
        }
        VolleyHttpClient.getInstance(context).get(url, null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (!isLoad) {
                                list.clear();
                            }
                            refreshLayout.setRefreshing(false);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject(docsId);
                            JSONArray topics = data.getJSONArray("topics");
                            JSONObject topic = topics.getJSONObject(0);
                            JSONArray docs = topic.getJSONArray("docs");
                            String book = docs.toString();
                            List<Image> imageList = new Gson().fromJson(book, new TypeToken<List<Image>>() {
                            }.getType());
                            if (imageList.size() == 0) {
                                if (loadingView != null)
                                    loadingView.setNoData();
                            }
                            Image image = new Image();
                            image.setType(Image.TYPE_BANNER);
                            image.setImgsrc(data.getString("banner"));
                            list.add(image);
                            list.addAll(imageList);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (loadingView != null)
                            loadingView.setLoadError();
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        helper.startDrag(viewHolder);
    }
}
