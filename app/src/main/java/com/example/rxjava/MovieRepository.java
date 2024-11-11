package com.example.rxjava;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private MovieApiService movieApiService;

    public MovieRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        movieApiService = retrofit.create(MovieApiService.class);
    }

    public Single<MovieResponse> getPopularMovies() {
        return movieApiService.getPopularMovies();
    }
}
