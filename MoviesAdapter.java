package com.example.mycourse;

import static androidx.fragment.app.FragmentManager.TAG;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesViewHolder holder, int position) {
        Movie movie = movies.get(position);

        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster() != null ?
                        movie.getPoster().getUrl() : null)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageViewPoster);
        double rating = movie.getRating().getKp();
        int backgroundId;
        if (rating >= 7) {
            backgroundId = R.drawable.circle_green;
        } else if (rating >= 5) {
            backgroundId = R.drawable.circle_yellow;
        } else {
            backgroundId = R.drawable.circle_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
        holder.textViewRating.setBackground(background);
        holder.textViewRating.setText(String.valueOf(rating));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewRating;
        private final ImageView imageViewPoster;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
        }
    }
}
