package com.example.mycourse;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnreachEndListener onreachEndListener;

    public void setOnreachEndListener(OnreachEndListener onreachEndListener) {
        this.onreachEndListener = onreachEndListener;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        if (this.movies == null) {
            this.movies = movies;
            notifyDataSetChanged();
        } else {
            MovieDiffCallback diffCallback = new MovieDiffCallback(this.movies, movies);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

            this.movies.clear();
            this.movies.addAll(movies);
            diffResult.dispatchUpdatesTo(this);
        }
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
        double rating = Math.round(movie.getRating().getKp() * 10.0) / 10.0;

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
        if(position == movies.size() - 1 && onreachEndListener != null) {
            onreachEndListener.onReachEnd();
        }
    }

    interface OnreachEndListener {
        void onReachEnd();
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
