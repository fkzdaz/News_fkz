
package com.example.fkz2.news_fkz.ui.contract;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.BasePresenter;
import com.example.fkz2.news_fkz.BaseView;
import com.example.fkz2.news_fkz.model.entity.Image;

import java.util.List;

public interface ImageContract {

    interface View extends BaseView {
        void loading();

        void networkError();

        void error(VolleyError error);

        void showImages(List<Image> list);

    }

    interface Presenter extends BasePresenter {
        void result();
    }
}
