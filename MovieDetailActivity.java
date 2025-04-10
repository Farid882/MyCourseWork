package com.example.mycourse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE = "movie";
    private static final String TAG = "MovieDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_detail);
        ImageView imageViewPoster = findViewById(R.id.imageViewPoster);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewYear = findViewById(R.id.textViewYear);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
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

        ApiFactory.apiservice.loadMovies(movie.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<MovieResponse>() {
                                    @Override
                                    public void accept(MovieResponse movieResponse) throws Throwable {
                                        Log.d(TAG, movieResponse.toString());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Throwable {
                                       Log.d(TAG, throwable.toString());
                                    }
                                });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static Intent newIntent(Context packageContext, Movie movie) {
        Intent intent = new Intent(packageContext, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}