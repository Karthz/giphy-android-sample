package com.karthz.giphy.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.karthz.giphy.R;
import com.karthz.giphy.model.data.Gif;
import com.karthz.giphy.ui.util.GlideApp;

import java.util.List;

public class GifsListAdapter extends RecyclerView.Adapter<GifsListAdapter.ViewHolder> {

    private List<Gif> gifs;
    private final Listener listener;
    private Context context;

    public GifsListAdapter(Context context, List<Gif> items, Listener listener) {
        this.gifs = items;
        this.context = context;
        this.listener = listener;
    }

    public interface Listener {
        void onItemSelected(Gif item);
    }

    public void addData(@NonNull List<Gif> items) {
        this.gifs.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        this.gifs.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Gif gif = gifs.get(position);
        holder.gif = gif;

        GlideApp.with(context)
                .asGif()
                .placeholder(R.drawable.placeholder_gif)
                .load(gif.getFixedSmallUri())
                .fitCenter()
                .override(128, 128)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder.view.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemSelected(holder.gif);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public View view;
        public Gif gif;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.imageView = view.findViewById(R.id.image_view);
        }
    }
}
