package com.example.task51c;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistActivity extends AppCompatActivity {

    private RecyclerView playlistRecyclerView;
    private PlaylistAdapter playlistAdapter;
    private List<String> videoUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        playlistRecyclerView = findViewById(R.id.playlistRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        playlistRecyclerView.setLayoutManager(layoutManager);

        videoUrls = new ArrayList<>();
        loadVideoUrls();

        playlistAdapter = new PlaylistAdapter(videoUrls);
        playlistRecyclerView.setAdapter(playlistAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(playlistRecyclerView.getContext(),
                layoutManager.getOrientation());
        playlistRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void loadVideoUrls() {
        SharedPreferences prefs = getSharedPreferences("YouTubePlaylist", MODE_PRIVATE);
        Set<String> urlSet = prefs.getStringSet("videoUrls", new HashSet<>());
        videoUrls = new ArrayList<>(urlSet);
        if (playlistAdapter != null) {
            playlistAdapter.notifyDataSetChanged();
        }
    }
}