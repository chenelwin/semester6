package com.example.asus.cinemaxx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.Movie;
import com.example.asus.cinemaxx.Model.ReqMovie;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    UserService userService = ApiUtils.getUserService();
    public View view;
    private RecyclerView rvMovieFragment;
    private MovieFragmentAdapter movieFragmentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        rvMovieFragment = (RecyclerView) view.findViewById(R.id.RvMovieFragment);
        doGetMovieData();
        return view;
    }

    private void doGetMovieData(){
        Call<ReqMovie> call = userService.getMovieRequest();
        call.enqueue(new Callback<ReqMovie>() {
            @Override
            public void onResponse(Call<ReqMovie> call, Response<ReqMovie> response) {
                ReqMovie reqMovie = response.body();
                List<Movie> movies = reqMovie.getMovies();
                movieFragmentAdapter = new MovieFragmentAdapter(movies);
                int col = 3;
                rvMovieFragment.setLayoutManager(new GridLayoutManager(view.getContext(), col));
                rvMovieFragment.setItemAnimator(new DefaultItemAnimator());
                rvMovieFragment.setAdapter(movieFragmentAdapter);
            }

            @Override
            public void onFailure(Call<ReqMovie> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
