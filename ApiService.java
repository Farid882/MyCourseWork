package com.example.mycourse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie?token=X8JW0K7-TQN4H6D-PEW9KE8-T9FPCXX&rating.kp=1-10&limit=5")
    Single<MovieResponse> loadMovies(@Query("page") int page); // RxJava Single
}
