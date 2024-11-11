package com.example.rxjava;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("movie/popular?api_key=e7631ffcb8e766993e5ec0c1f4245f93")
    Single<MovieResponse> getPopularMovies();
}
