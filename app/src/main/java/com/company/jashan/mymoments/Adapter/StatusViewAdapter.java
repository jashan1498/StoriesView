package com.company.jashan.mymoments.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.company.jashan.mymoments.R;

import java.util.ArrayList;

public class StatusViewAdapter extends RecyclerView.Adapter {

    private final int threshold = 5;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROGRESS = 0;
    private LoadMoreListener loadMoreListener;
    private ArrayList<String> statusList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private boolean loading = false;
    private OnClickListener clickListener;

    public StatusViewAdapter(ArrayList<String> list, RecyclerView recyclerView, Context context) {
        this.context = context;
        this.statusList = list;
        this.recyclerView = recyclerView;

        sethLoadMoreView();
    }

   public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

   public void setLoadMoreListener(LoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return statusList.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
    }

   public void setData(ArrayList<String> list) {
        this.statusList = list;
        notifyDataSetChanged();
        loading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        RecyclerView.ViewHolder vh;
        if (i == VIEW_ITEM) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.status_view, viewGroup, false);
            vh = new StatusViewHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_view, viewGroup, false);
            vh = new ProgressViewHolder(view);
        }
        return vh;

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder vh, int i) {
        final int position = vh.getAdapterPosition();

        if (vh instanceof StatusViewHolder) {
            Glide.with(((StatusViewHolder) vh).statusView).load("https://picsum.photos/id/32" + i + "/200/300").into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    ((StatusViewHolder) vh).statusView.setBackground(resource);
                }
            });
            ((StatusViewHolder) vh).statusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) clickListener.onClick(position);
                }
            });
        }


    }

    private void sethLoadMoreView() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (totalItemCount <= lastVisibleItem + threshold && !loading) {
                        // load more
                        if (loadMoreListener != null) {
                            loadMoreListener.onLoad();
                        }
                        loading = true;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public interface OnClickListener {
        void onClick(int position);
    }


    public interface LoadMoreListener {
        void onLoad();
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {
        LinearLayout statusView;

        StatusViewHolder(@NonNull final View itemView) {
            super(itemView);
            statusView = itemView.findViewById(R.id.status_view_parent);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;

        ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.progress_view);
        }
    }
}
