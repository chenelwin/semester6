package com.example.asus.cinemaxx.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.cinemaxx.Model.Movie;
import com.example.asus.cinemaxx.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieByPlazaAdapter extends RecyclerView.Adapter<MovieByPlazaAdapter.ViewHolder> {

    public interface PassingData{
        void passData(Integer id, int position);
    }

    public static PassingData passingData;

    List<Movie> movies;
    Context context;

    public MovieByPlazaAdapter(List<Movie> movieList){
        this.movies = movieList;
    }

    @Override
    public MovieByPlazaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_moviebyplaza, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieByPlazaAdapter.ViewHolder holder, final int position) {
        final Movie movie = movies.get(holder.getAdapterPosition());
        holder.movieName.setText(movie.getName());

        Picasso.with(context)
                .load("http://cinema-xxii-server.herokuapp.com/profile?id=" + movie.getName())
                .resize(200, 200)
                .centerCrop()
                .into(holder.movieImg);
        /*
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScheduleActivity.class);
                intent.putExtra("movieid", movie.getId());
                context.startActivity(intent);
            }
        });*/
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passingData.passData(movie.getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImg;
        TextView movieName;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            movieImg = (ImageView)itemView.findViewById(R.id.movieimg);
            movieName = (TextView)itemView.findViewById(R.id.moviename);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}
