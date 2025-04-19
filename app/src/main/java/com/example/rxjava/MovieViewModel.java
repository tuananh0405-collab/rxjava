package com.example.rxjava;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieViewModel extends ViewModel {
    private final MovieRepository movieRepository = new MovieRepository();
    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private CompositeDisposable disposable = new CompositeDisposable();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void fetchMovies() {
        isLoading.setValue(true);

        handler.postDelayed(() -> {
            if (isLoading.getValue() != null && isLoading.getValue()) {
                isLoading.setValue(false);
                disposable.add(movieRepository.getPopularMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                movieResponse -> {
                                    if (movieResponse != null && movieResponse.getResults() != null) {
                                        List<Movie> movieList = movieResponse.getResults();
                                        movies.postValue(movieList);
                                    } else {
                                    }
                                    isLoading.setValue(false);
                                },
                                throwable -> {
                                    isLoading.setValue(false);
                                    throwable.printStackTrace();
                                }
                        ));
            }
        }, 1000);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
