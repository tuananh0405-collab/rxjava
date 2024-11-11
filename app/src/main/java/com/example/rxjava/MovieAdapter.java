package com.example.rxjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public List<Movie> movies;
    private Context context;
    private boolean isGridView;

    public MovieAdapter(Context context, List<Movie> movies, boolean isGridView) {
        this.context = context;
        this.movies = movies;
        this.isGridView = isGridView;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isGridView) {
            view = LayoutInflater.from(context).inflate(R.layout.item_movie_grid, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_movie_list, parent, false);
        }
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.get().load("https://image.tmdb.org/t/p/original/" + movie.getPosterPath()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            poster = itemView.findViewById(R.id.movie_poster);
        }
    }
}
