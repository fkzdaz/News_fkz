
package com.example.fkz2.news_fkz.ui.contract;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.BasePresenter;
import com.example.fkz2.news_fkz.BaseView;
import com.example.fkz2.news_fkz.model.entity.Jokes;

import java.util.List;


public interface JokesContract {

    interface View extends BaseView {
        void loading(boolean isLoad);

        void networkError();

        void error(VolleyError error);

        void showJokes(List<Jokes> list, boolean isLoad);

    }

    interface Presenter extends BasePresenter {
        void start(boolean isLoad);
        void result();
    }
}
