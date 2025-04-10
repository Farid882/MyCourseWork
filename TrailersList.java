package com.example.mycourse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailersList {
    @SerializedName("trailers")
    private List<Trailer> trailers = new ArrayList<>();

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public TrailersList(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "TrailersList{" +
                "trailers=" + trailers +
                '}';
    }
}
