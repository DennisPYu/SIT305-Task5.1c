package com.example.task51c;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTopStories;
    private ImageAdapter adapterTopStories;

    private RecyclerView recyclerViewNews;
    private NewsGridAdapter adapterNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerViewTopStories = findViewById(R.id.recyclerViewTopStories);
        recyclerViewTopStories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        recyclerViewNews.setLayoutManager(new GridLayoutManager(this, 2));

        TypedArray images = getResources().obtainTypedArray(R.array.image_array);
        List<NewsItem> newsItems = new ArrayList<>();

        for (int i = 0; i < ((TypedArray) images).length(); i++) {
            int imageId = images.getResourceId(i, -1);
            if (imageId != -1) {
                newsItems.add(new NewsItem(imageId, "Description for Image " + (i + 1)));
            }
        }

        images.recycle();

        adapterTopStories = new ImageAdapter(this, newsItems);
        recyclerViewTopStories.setAdapter(adapterTopStories);

        adapterNews = new NewsGridAdapter(this, newsItems);
        recyclerViewNews.setAdapter(adapterNews);
    }
}
