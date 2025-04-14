package com.example.mycourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ProgressBar progressBarLoading = findViewById(R.id.progressBarLoading);
        RecyclerView recyclerViewMovies = findViewById(R.id.recyclerViewMovies);


        MoviesAdapter moviesAdapter = new MoviesAdapter();
        recyclerViewMovies.setAdapter(moviesAdapter);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });

        viewModel.getIsLoaded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBarLoading.setVisibility(ProgressBar.VISIBLE);
                } else {
                    progressBarLoading.setVisibility(ProgressBar.GONE);
                }
            }
        });
        moviesAdapter.setOnreachEndListener(new MoviesAdapter.OnreachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadMovies();
            }
        });
        moviesAdapter.setOnMoviesClickListener(new MoviesAdapter.OnMoviesClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(MainActivity.this, movie);
                startActivity(intent);
            }
        });

    }
}