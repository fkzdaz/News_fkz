
package com.example.fkz2.news_fkz.presenter;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.app.MyApplication;
import com.example.fkz2.news_fkz.model.LoadModel;
import com.example.fkz2.news_fkz.model.entity.Image;
import com.example.fkz2.news_fkz.model.impl.VoiceModelImpl;
import com.example.fkz2.news_fkz.ui.contract.VoiceContract;
import com.example.fkz2.news_fkz.utils.ToastUtil;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class VoicePresenter implements VoiceContract.Presenter {
    private VoiceContract.View view;
    private LoadModel loadModel;

    public void init(VoiceContract.View view) {
        loadModel = new VoiceModelImpl();
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
