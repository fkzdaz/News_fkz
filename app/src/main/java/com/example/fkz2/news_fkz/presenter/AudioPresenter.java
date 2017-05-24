
package com.example.fkz2.news_fkz.presenter;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.app.MyApplication;
import com.example.fkz2.news_fkz.model.AudioLoadModel;
import com.example.fkz2.news_fkz.model.LoadModel;
import com.example.fkz2.news_fkz.model.entity.Image;
import com.example.fkz2.news_fkz.model.entity.Voice;
import com.example.fkz2.news_fkz.model.impl.AudioModelImpl;
import com.example.fkz2.news_fkz.model.impl.VoiceModelImpl;
import com.example.fkz2.news_fkz.ui.contract.AudioContract;
import com.example.fkz2.news_fkz.ui.contract.VoiceContract;
import com.example.fkz2.news_fkz.utils.ToastUtil;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class AudioPresenter implements AudioContract.Presenter {
    private AudioContract.View view;
    private AudioLoadModel loadModel;

    public void init(AudioContract.View view) {
        loadModel = new AudioModelImpl();
        this.view = checkNotNull(view, "AudioContract view cannot be null");
        this.view.initView();
    }

    @Override
    public void result() {
        ToastUtil.showSortToast(MyApplication.getContext(), "result!!!!!!!!!");

    }

    @Override
    public void start(String text) {
        view.loading();
        loadModel.load(new OnAudioLoadListener<List<Voice>>() {

            @Override
            public void onSuccess(List<Voice> success, String text) {
                view.showVoices(success, text);
            }

            @Override
            public void onSuccess(List<Voice> success) {

            }

            @Override
            public void onError(VolleyError error) {
                view.error(error);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        }, text);

    }

    @Override
    public void start() {

    }
}
