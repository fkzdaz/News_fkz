
package com.example.fkz2.news_fkz.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.fkz2.news_fkz.R;
import com.example.fkz2.news_fkz.utils.ApiUtil;
import com.example.fkz2.news_fkz.utils.Constants;
import com.example.fkz2.news_fkz.widget.LoadingWebView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ImageActivity extends BaseSwipeActivity {
    @Bind(R.id.web_view)
    LoadingWebView webView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url = ApiUtil.MOMENT_CONTENT + getIntent().getStringExtra(Constants.EXTRA_DOCS_ID);

        webView.getSettings().setDefaultTextEncodingName("utf-8");

        Log.d("url", url);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
