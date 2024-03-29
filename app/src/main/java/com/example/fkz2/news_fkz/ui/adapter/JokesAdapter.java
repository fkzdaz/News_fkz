
package com.example.fkz2.news_fkz.ui.adapter;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fkz2.news_fkz.R;
import com.example.fkz2.news_fkz.model.entity.Jokes;
import com.example.fkz2.news_fkz.widget.FooterLoading;
import com.example.fkz2.news_fkz.widget.recyclerviewhelper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class JokesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    private Activity activity;
    private List<Jokes> list;

    public JokesAdapter(Activity activity, List<Jokes> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Jokes.TYPE_FOOTER_LOAD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_jokes, parent, false);
                return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            Jokes jokes = list.get(position);
            viewHolder.tvDigest.setText(jokes.getDigest());
            viewHolder.tvUp.setText(String.valueOf(jokes.getUpTimes()));
            viewHolder.tvDown.setText(String.valueOf(jokes.getDownTimes()));
        } else if (holder instanceof FooterHolder) {
            FooterHolder footerHolder = (FooterHolder) holder;
            footerHolder.footerLoading.setVisibility(View.VISIBLE);
            footerHolder.footerLoading.onLoad();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_digest)
        TextView tvDigest;
        @Bind(R.id.iv_share)
        ImageView ivShare;
        @Bind(R.id.tv_up)
        TextView tvUp;
        @Bind(R.id.tv_down)
        TextView tvDown;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.iv_share, R.id.tv_up, R.id.tv_down})
        void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_share:
                    AnimatedVectorDrawable shareDrawable = (AnimatedVectorDrawable) ivShare.getDrawable();
                    shareDrawable.start();
                    break;
                case R.id.tv_up:
                    AnimatedVectorDrawable upDrawable = (AnimatedVectorDrawable) tvUp.getCompoundDrawables()[0];
                    upDrawable.start();
                    break;
                case R.id.tv_down:
                    break;
            }
        }

    }

    class FooterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.footer)
        FooterLoading footerLoading;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
