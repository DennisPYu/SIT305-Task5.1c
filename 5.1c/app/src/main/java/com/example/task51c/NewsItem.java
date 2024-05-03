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
