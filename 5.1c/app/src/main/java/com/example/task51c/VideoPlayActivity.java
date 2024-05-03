package com.example.task51c;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayActivity extends AppCompatActivity {

    private WebView youtubeWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player_view);

        youtubeWebView = findViewById(R.id.youtube_web_view);
        setupWebView();

        String videoUrl = getIntent().getStringExtra("videoUrl");
        loadVideo(videoUrl);
    }

    private void setupWebView() {
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    private void loadVideo(String videoUrl) {
        String htmlData = getHTML(videoUrl);
        youtubeWebView.loadData(htmlData, "text/html", "UTF-8");
        Log.d("VideoPlayActivity", "Loading video URL: " + videoUrl);
    }

    private String getHTML(String videoUrl) {
        String videoId = videoUrl.split("v=")[1];
        String html = "<!DOCTYPE html><html><body>" +
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId +
                "\" frameborder=\"0\" allowfullscreen></iframe>" +
                "</body></html>";
        return html;
    }
}