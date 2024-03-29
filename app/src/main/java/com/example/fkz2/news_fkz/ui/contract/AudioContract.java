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
package com.example.fkz2.news_fkz.ui.contract;

import com.android.volley.VolleyError;
import com.example.fkz2.news_fkz.BasePresenter;
import com.example.fkz2.news_fkz.BaseView;
import com.example.fkz2.news_fkz.model.entity.Voice;

import java.util.List;


public interface AudioContract {

    interface View extends BaseView {
        void loading();

        void networkError();

        void error(VolleyError error);

        void showVoices(List<Voice> list, String text);

    }

    interface Presenter extends BasePresenter {
        void start(String text);
        void result();
    }
}
