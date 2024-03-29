
package com.example.fkz2.news_fkz.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fkz2.news_fkz.R;
import com.example.fkz2.news_fkz.model.entity.Image;
import com.example.fkz2.news_fkz.widget.recyclerviewhelper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    private Activity activity;
    private List<Image> list;

    public BooksAdapter(Activity activity, List<Image> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Image.TYPE_CONTENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_books, parent, false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
                return new BannerHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            Image bean = list.get(position);
            viewHolder.tvTitle.setText(bean.getTitle());
            viewHolder.tvDigest.setText(bean.getDigest());
            Glide.with(activity).load(bean.getImgsrc())
                    .placeholder(R.color.place_holder_color)
                    .error(R.color.place_holder_color)
                    .into(viewHolder.ivThumb);
        } else if (holder instanceof BannerHolder) {
            BannerHolder bannerHolder = (BannerHolder) holder;
            Glide.with(activity).load(list.get(position).getImgsrc())
                    .placeholder(R.color.place_holder_color)
                    .error(R.color.place_holder_color)
                    .into(bannerHolder.ivBanner);
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_thumb)
        ImageView ivThumb;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_digest)
        TextView tvDigest;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(getLayoutPosition());
                }
            });
            ButterKnife.bind(this, itemView);
        }
    }

    class BannerHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_banner)
        ImageView ivBanner;

        public BannerHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
