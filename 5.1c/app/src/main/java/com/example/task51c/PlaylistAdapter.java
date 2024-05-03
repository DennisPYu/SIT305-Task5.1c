package com.example.task51c;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private List<String> videoUrls;

    public PlaylistAdapter(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(videoUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return videoUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView urlTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            urlTextView = itemView.findViewById(android.R.id.text1);
        }

        void bind(String url) {
            urlTextView.setText(url);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), VideoPlayActivity.class);
                intent.putExtra("videoUrl", url);
                v.getContext().startActivity(intent);
            });
        }
    }
}
