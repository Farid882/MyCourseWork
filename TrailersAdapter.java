package com.example.mycourse;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private List<Trailer> trailers = new ArrayList<>();

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class TrailersViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTrailerName;

        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrailerName = itemView.findViewById(R.id.textViewTrailerName);
        }
    }
}
