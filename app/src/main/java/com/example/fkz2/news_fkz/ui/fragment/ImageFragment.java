
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.R;
import com.example.fkz2.news_fkz.ui.adapter.BooksAdapter;
import com.example.fkz2.news_fkz.model.entity.Image;
import com.example.fkz2.news_fkz.presenter.ImagePresenter;
import com.example.fkz2.news_fkz.ui.activity.ImageActivity;
import com.example.fkz2.news_fkz.ui.contract.ImageContract;
import com.example.fkz2.news_fkz.utils.Constants;
import com.example.fkz2.news_fkz.utils.StartActivityUtil;
import com.example.fkz2.news_fkz.utils.ToastUtil;
import com.example.fkz2.news_fkz.widget.DividerDecoration;
import com.example.fkz2.news_fkz.widget.LoadingView;
import com.example.fkz2.news_fkz.widget.recyclerviewhelper.OnStartDragListener;
import com.example.fkz2.news_fkz.widget.recyclerviewhelper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageFragment extends Fragment implements BooksAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, OnStartDragListener, ImageContract.View {
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
    private boolean isPullRefresh = false;
    private ImagePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.bind(this, view);
        presenter = new ImagePresenter();
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

        DividerDecoration.Builder builder = new DividerDecoration.Builder(context);
        builder.setColorResource(R.color.divider_color)
                .setHeight(1f)
                .setLeftPadding(R.dimen.divider_padding_left);
        recyclerView.addItemDecoration(builder.build());

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new BooksAdapter(activity, list);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter, ItemTouchHelper.START | ItemTouchHelper.END));
        helper.attachToRecyclerView(recyclerView);

        loadingView.setOnReLoadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start();
            }
        });
        presenter.start();
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_DOCS_ID, list.get(position).getDocid());
        StartActivityUtil.start(activity, ImageActivity.class, bundle);
    }

    @Override
    public void onRefresh() {
        isPullRefresh = true;
        presenter.start();
        presenter.result();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        helper.startDrag(viewHolder);
    }

    @Override
    public void loading() {
        if (!isPullRefresh)
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
    public void showImages(List<Image> imageList) {
        list.clear();
        list.addAll(imageList);
        refreshLayout.setRefreshing(false);
        if (imageList.size() <= 1) {
            loadingView.setNoData();
        } else {
            loadingView.setLoaded();
        }
        adapter.notifyDataSetChanged();
    }
}
