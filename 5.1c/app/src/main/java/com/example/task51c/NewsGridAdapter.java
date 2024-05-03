package com.example.task51c;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsGridAdapter extends RecyclerView.Adapter<NewsGridAdapter.ViewHolder> {
    private List<NewsItem> mData;
    private LayoutInflater mInflater;

    NewsGridAdapter(Context context, List<NewsItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.news_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTextView.setText(mData.get(position).getText());
        holder.myImageView.setImageResource(mData.get(position).getImageResource());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textViewGridDescription);
            myImageView = itemView.findViewById(R.id.imageViewGridItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                NewsItem item = mData.get(position);
                Intent intent = new Intent(view.getContext(), NewsDetailActivity.class);
                intent.putExtra("imageRes", item.getImageResource());
                intent.putExtra("description", item.getText());
                view.getContext().startActivity(intent);
            }
        }
    }
}
