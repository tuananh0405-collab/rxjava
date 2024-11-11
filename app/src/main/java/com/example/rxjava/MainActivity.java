package com.example.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private MovieViewModel movieViewModel;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private boolean isGridView = true;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            isGridView = !isGridView;
            setUpRecyclerView();
        });

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getMovies().observe(this, this::updateMovieList);

        movieViewModel.isLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        movieViewModel.fetchMovies();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateMovieList(List<Movie> movies) {
        if (movieAdapter == null) {
            Log.d(TAG, "updateMovieList: size in main" + movies.size());
            movieAdapter = new MovieAdapter(this, movies, isGridView);
            recyclerView.setAdapter(movieAdapter);
            recyclerView.setLayoutManager(isGridView ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this));

        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private void setUpRecyclerView() {
        if (isGridView) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        if (movieAdapter != null) {
            movieAdapter = new MovieAdapter(this, movieAdapter.movies, isGridView);
            recyclerView.setAdapter(movieAdapter);
        }
    }
}