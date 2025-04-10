package com.example.mycourse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.GET;
import retrofit2.http.Path;

public class Trailer {
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public Trailer(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @NonNull
    @Override
    public String toString() {
        return "Trailer{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
