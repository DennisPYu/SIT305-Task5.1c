/* 
//HomeActivity.java
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

//ImageAdapter.java
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

//LoginActivity.java
package com.example.task51c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        signUpButton = findViewById(R.id.sign_up);

        loginButton.setOnClickListener(v -> checkFieldsAndProceed());
        signUpButton.setOnClickListener(v -> navigateToSignUp());
    }

    private void checkFieldsAndProceed() {
        if (!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            usernameEditText.setError("Required");
            passwordEditText.setError("Required");
        }
    }

    private void navigateToSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}


//MainActivity.java
package com.example.task51c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNews = findViewById(R.id.btnNewsApp);
        Button btnITube = findViewById(R.id.btnITubeApp);

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        btnITube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}



//NewsActivity.java
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


//NewsDetailActivity.java
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


//NewsGridAdapter.java
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



//NewsItem.java
package com.example.task51c;


public class NewsItem {
    private int imageResource;
    private String text;

    public NewsItem(int imageResource, String text) {
        this.imageResource = imageResource;
        this.text = text;
    }

    public NewsItem(int imageResource) {
        this(imageResource, "");
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText() {
        return text;
    }
}



// PlaylistActivity.java
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


//PlaylistAdapter.java

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



//SignUpActivity.java


package com.example.task51c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullNameEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameEditText = findViewById(R.id.fullName);
        usernameEditText = findViewById(R.id.newUsername);
        passwordEditText = findViewById(R.id.newPassword);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        createAccountButton = findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        if (fieldsAreValid()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean fieldsAreValid() {
        if (fullNameEditText.getText().toString().isEmpty() ||
                usernameEditText.getText().toString().isEmpty() ||
                passwordEditText.getText().toString().isEmpty() ||
                confirmPasswordEditText.getText().toString().isEmpty()) {

            if (fullNameEditText.getText().toString().isEmpty())
                fullNameEditText.setError("Required");
            if (usernameEditText.getText().toString().isEmpty())
                usernameEditText.setError("Required");
            if (passwordEditText.getText().toString().isEmpty())
                passwordEditText.setError("Required");
            if (confirmPasswordEditText.getText().toString().isEmpty())
                confirmPasswordEditText.setError("Required");

            return false;
        }

        if (!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}


//VideoPlayActivity.java

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











*/
