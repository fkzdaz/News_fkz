
package com.example.fkz2.news_fkz.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.R;
import com.example.fkz2.news_fkz.model.entity.Jokes;
import com.example.fkz2.news_fkz.presenter.JokesPresenter;
import com.example.fkz2.news_fkz.ui.adapter.JokesAdapter;
import com.example.fkz2.news_fkz.ui.contract.JokesContract;
import com.example.fkz2.news_fkz.utils.ToastUtil;
import com.example.fkz2.news_fkz.widget.LoadingView;
import com.example.fkz2.news_fkz.widget.PullToLoadMoreRecyclerView;
import com.example.fkz2.news_fkz.widget.recyclerviewhelper.OnStartDragListener;
import com.example.fkz2.news_fkz.widget.recyclerviewhelper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JokeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnStartDragListener, JokesContract.View {

    @Bind(R.id.recycler_view)
    PullToLoadMoreRecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.loading_view)
    LoadingView loadingView;

    private List<Jokes> list;
    private Activity activity;
    private Context context;
    private JokesAdapter adapter;
    private ItemTouchHelper helper;
    private JokesPresenter presenter;
    private boolean isPullRefresh = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);
        ButterKnife.bind(this, view);
        presenter = new JokesPresenter();
        presenter.init(this);
        return view;
    }

    @Override
    public void initView() {
        activity = getActivity();
        context = activity.getApplicationContext();
        list = new ArrayList<>();
        refreshLayout.setColorSchemeResources(R.color.loading_color);
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new JokesAdapter(activity, list);
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter, ItemTouchHelper.START | ItemTouchHelper.END));
        helper.attachToRecyclerView(recyclerView);

        loadingView.setOnReLoadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start(false);
            }
        });

        recyclerView.setOnLoadMoreListener(new PullToLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                Log.d("onScrolled", "true");
                presenter.start(true);
            }
        });
        presenter.start(false);
    }

    public void backToTop() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onRefresh() {
        isPullRefresh = true;
        presenter.start(false);
        presenter.result();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        helper.startDrag(viewHolder);
    }

    @Override
    public void loading(boolean isLoad) {
        if (!isPullRefresh && !isLoad)
            loadingView.setLoading();
        isPullRefresh = false;
    }

    @Override
    public void networkError() {
        ToastUtil.showSortToast(context, "哎呀，网络开小差啦～～～");
        refreshLayout.setRefreshing(false);
        loadingView.setLoadError();
    }

    @Override
    public void error(VolleyError error) {
        loadingView.setLoadError();
        refreshLayout.setRefreshing(false);
        ToastUtil.showSortToast(context, "服务器出错。。。");
    }

    @Override
    public void showJokes(List<Jokes> jokesList, boolean isLoad) {
        if (!isLoad) {
            list.clear();
            Jokes jokes = new Jokes();
            jokes.setType(Jokes.TYPE_FOOTER_LOAD);
            list.add(jokes);
        }
        list.addAll(list.size() - 1, jokesList);
        refreshLayout.setRefreshing(false);
        if (jokesList.size() <= 1) {
            loadingView.setNoData();
        } else {
            loadingView.setLoaded();
        }
        adapter.notifyDataSetChanged();
    }

}
