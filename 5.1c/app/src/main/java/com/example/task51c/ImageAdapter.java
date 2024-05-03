package com.example.task51c;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<NewsItem> mData;
    private LayoutInflater mInflater;
    private Context context;

    ImageAdapter(Context context, List<NewsItem> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.news_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        int imageResId = mData.get(position).getImageResource();
        Log.d("ImageAdapter", "Setting image resource ID: " + imageResId);
        holder.myImageView.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.imgNews);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                NewsItem item = mData.get(position);
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("imageRes", item.getImageResource());
                intent.putExtra("description", item.getText());
                context.startActivity(intent);
            }
        }
    }
}
