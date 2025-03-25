package com.example.mycourse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Poster {
    @SerializedName("url")
    private String url;

    public Poster(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    @NonNull
    @Override
    public String toString() {
        return "Poster{" +
                "url='" + url + '\'' +
                '}';
    }
}
