package com.example.asus.cinemaxx.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asus.cinemaxx.Activity.MovieDetailActivity;
import com.example.asus.cinemaxx.Model.Movie;
import com.example.asus.cinemaxx.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieFragmentAdapter extends RecyclerView.Adapter<MovieFragmentAdapter.ViewHolder> {

    List<Movie> movies;
    Context context;

    public MovieFragmentAdapter(List<Movie> movieList){
        this.movies = movieList;
    }

    @Override
    public MovieFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_moviefragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieFragmentAdapter.ViewHolder holder, int position) {
        final Movie movie = movies.get(holder.getAdapterPosition());
        Picasso.with(context)
                .load("http://cinema-xxii-server.herokuapp.com/profile?id=" + movie.getName())
                .resize(200, 200)
                .centerCrop()
                .into(holder.movieImg);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                intent.putExtra("movieid", movie.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImg;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            movieImg = (ImageView)itemView.findViewById(R.id.movieimg);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}
