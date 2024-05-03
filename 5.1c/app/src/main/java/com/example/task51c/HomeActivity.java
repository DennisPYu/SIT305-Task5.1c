package com.example.task51c;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private EditText youtubeUrlEditText;
    private Button playButton, addToPlaylistButton, myPlaylistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        youtubeUrlEditText = findViewById(R.id.youtubeUrl);
        playButton = findViewById(R.id.playButton);
        addToPlaylistButton = findViewById(R.id.addToPlaylistButton);
        myPlaylistButton = findViewById(R.id.myPlaylistButton);

        playButton.setOnClickListener(v -> playVideo());
        addToPlaylistButton.setOnClickListener(v -> addToPlaylist());
        myPlaylistButton.setOnClickListener(v -> viewPlaylist());
    }

    private void playVideo() {
        String url = youtubeUrlEditText.getText().toString();
        Intent intent = new Intent(this, VideoPlayActivity.class);
        intent.putExtra("videoUrl", url);
        startActivity(intent);
    }

    private void addToPlaylist() {
        String url = youtubeUrlEditText.getText().toString();
        if (url.isEmpty()) {
            Toast.makeText(this, "URL is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences prefs = getSharedPreferences("YouTubePlaylist", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> urlSet = prefs.getStringSet("videoUrls", new HashSet<>());
        urlSet.add(url);
        editor.putStringSet("videoUrls", urlSet);
        editor.apply();
        Toast.makeText(this, "Added to playlist", Toast.LENGTH_SHORT).show();
    }

    private void viewPlaylist() {
        Intent intent = new Intent(this, PlaylistActivity.class);
        startActivity(intent);
    }
}