
package com.example.fkz2.news_fkz.presenter;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.app.MyApplication;
import com.example.fkz2.news_fkz.model.LoadModel;
import com.example.fkz2.news_fkz.model.entity.Jokes;
import com.example.fkz2.news_fkz.model.impl.JokesModelImpl;
import com.example.fkz2.news_fkz.ui.contract.JokesContract;
import com.example.fkz2.news_fkz.utils.ToastUtil;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Smile Wei
 * @since 2016/6/8.
 */
public class JokesPresenter implements JokesContract.Presenter {
    private JokesContract.View view;
    private LoadModel loadModel;

    public void init(JokesContract.View view) {
        loadModel = new JokesModelImpl();
        this.view = checkNotNull(view, "JokesContract view cannot be null");
        this.view.initView();
    }

    @Override
    public void result() {
        ToastUtil.showSortToast(MyApplication.getContext(), "result!!!!!!!!!");
    }

    @Override
    public void start(final boolean isLoad) {
        view.loading(isLoad);
        loadModel.load(new OnLoadListener<List<Jokes>>() {
            @Override
            public void onSuccess(List<Jokes> success) {
                view.showJokes(success, isLoad);
            }

            @Override
            public void onError(VolleyError error) {
                view.error(error);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        });

    }

    @Override
    public void start() {

    }
}
