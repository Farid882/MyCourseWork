package com.example.mycourse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE = "movie";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "MovieDetailActivity";
    private TrailersAdapter trailersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_detail);
        ImageView imageViewPoster = findViewById(R.id.imageViewPoster);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewYear = findViewById(R.id.textViewYear);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        RecyclerView recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);
        MovieDetailViewModel viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        assert movie != null;
        Glide.with(this)
                .load(movie.getPoster() != null ?
                        movie.getPoster().getUrl() : null)
                .error(R.drawable.ic_launcher_background)
                .into(imageViewPoster);
        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(String.valueOf(movie.getDescription()));
        viewModel.loadTrallers(movie.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {

                trailersAdapter.setTrailers(trailers);
            }
        });
    }

    public static Intent newIntent(Context packageContext, Movie movie) {
        Intent intent = new Intent(packageContext, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

}