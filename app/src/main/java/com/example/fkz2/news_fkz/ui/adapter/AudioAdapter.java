
package com.example.fkz2.news_fkz.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fkz2.news_fkz.R;
import com.example.fkz2.news_fkz.model.entity.Voice;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {
    private Activity activity;
    private List<Voice> list;

    public AudioAdapter(Activity activity, List<Voice> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voice_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(activity).load(list.get(position).getCover())
                .placeholder(R.color.place_holder_color)
                .error(R.color.place_holder_color)
                .into(holder.image);
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
        @Bind(R.id.image)
        ImageView image;

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
}
