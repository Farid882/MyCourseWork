package com.example.mycourse;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private int page = 1;
    private static final String TAG = "MainViewModel";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoaded = new MutableLiveData<>(false);


    public MainViewModel(@NonNull Application application) {
        super(application);
         loadMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getIsLoaded() {
        return isLoaded;
    }

    public void loadMovies() {
        Boolean isLoading = isLoaded.getValue();
        if (isLoading != null && isLoading) {
            return;
        }
        Disposable disposable = ApiFactory.apiservice.loadMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoaded.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoaded.setValue(false);
                    }
                })
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Throwable {
                        List<Movie> loadMovies = movies.getValue();
                        if(loadMovies != null) {
                            loadMovies.addAll(movieResponse.getMovies());
                            movies.setValue(loadMovies);
                        }else {
                            movies.setValue(movieResponse.getMovies());
                        }
                        Log.d(TAG, "Loaded pages " + page);
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                      Log.d(TAG, throwable.toString());
                    }
                });

        compositeDisposable.add(disposable);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
