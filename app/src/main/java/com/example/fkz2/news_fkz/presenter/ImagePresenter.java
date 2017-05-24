
package com.example.fkz2.news_fkz.presenter;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.app.MyApplication;
import com.example.fkz2.news_fkz.model.LoadModel;
import com.example.fkz2.news_fkz.model.entity.Image;
import com.example.fkz2.news_fkz.model.impl.ImageModelImpl;
import com.example.fkz2.news_fkz.ui.contract.ImageContract;
import com.example.fkz2.news_fkz.utils.ToastUtil;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Smile Wei
 * @since 2016/6/8.
 */
public class ImagePresenter implements ImageContract.Presenter {
    private ImageContract.View view;
    private LoadModel loadModel;

    public void init(ImageContract.View view) {
        loadModel = new ImageModelImpl();
        this.view = checkNotNull(view, "ImageContract view cannot be null");
        this.view.initView();
    }

    @Override
    public void result() {
        ToastUtil.showSortToast(MyApplication.getContext(), "result!!!!!!!!!");

    }

    @Override
    public void start() {
        view.loading();
        loadModel.load(new OnLoadListener<List<Image>>() {
            @Override
            public void onSuccess(List<Image> success) {
                view.showImages(success);
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
}
