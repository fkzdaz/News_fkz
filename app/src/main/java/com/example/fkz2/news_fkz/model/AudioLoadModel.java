
package com.example.fkz2.news_fkz.model;

import com.example.fkz2.news_fkz.presenter.OnAudioLoadListener;

public interface AudioLoadModel extends LoadModel {
    void load(OnAudioLoadListener listener, String text);
}
