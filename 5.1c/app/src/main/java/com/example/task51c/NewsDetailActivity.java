package com.example.task51c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        ImageView imageView = findViewById(R.id.image_detail);
        TextView textView = findViewById(R.id.text_detail);

        Intent intent = getIntent();
        int imageRes = intent.getIntExtra("imageRes", 0);
        String description = intent.getStringExtra("description");

        imageView.setImageResource(imageRes);
        textView.setText(description);
    }
}
