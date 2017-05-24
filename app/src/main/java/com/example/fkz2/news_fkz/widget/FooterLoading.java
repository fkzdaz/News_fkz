package com.example.fkz2.news_fkz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fkz2.news_fkz.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class FooterLoading extends RelativeLayout {
    @Bind(R.id.loading)
    CircularProgressBar loadingProgressBar;
    @Bind(R.id.load_full)
    TextView loadFull;

    public FooterLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FooterLoading(Context context) {
        super(context);
        initView(context);
    }

    public FooterLoading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_load_more, this, true);

        ButterKnife.bind(view);
    }

    public void onLoad() {
        loadingProgressBar.setVisibility(VISIBLE);
        loadFull.setVisibility(GONE);
    }

    public void onLoadFull() {
        loadingProgressBar.setVisibility(GONE);
        loadFull.setVisibility(VISIBLE);
    }
}
