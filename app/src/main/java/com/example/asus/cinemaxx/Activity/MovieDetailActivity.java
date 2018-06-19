package com.example.asus.cinemaxx.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.Movie;
import com.example.asus.cinemaxx.Model.ReqMovieId;
import com.example.asus.cinemaxx.R;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    Context context;
    TextView moviename;
    TextView moviecast;
    TextView moviewriter;
    TextView moviegenre;
    TextView movieproducer;
    TextView movielength;
    TextView moviesynopsis;
    Integer movieid;
    ImageView movieimg;
    Button btnBuyTicket;
    UserService userService = ApiUtils.getUserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initView();

        doGetMovieId();

        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, PlazaByMovie.class);
                intent.putExtra("movieid", movieid);
                startActivity(intent);
            }
        });
    }

    private void initView(){
        context = this;
        moviename = (TextView)findViewById(R.id.moviename);
        moviecast = (TextView)findViewById(R.id.moviecast);
        moviewriter = (TextView)findViewById(R.id.moviewriter);
        moviegenre = (TextView)findViewById(R.id.moviegenre);
        movieproducer = (TextView)findViewById(R.id.movieproducer);
        movielength = (TextView)findViewById(R.id.movielength);
        moviesynopsis = (TextView)findViewById(R.id.moviesynopsis);
        movieimg = (ImageView)findViewById(R.id.movieimg);
        btnBuyTicket = (Button)findViewById(R.id.btnBuyTicket);
    }

    private void doGetMovieId(){
        Integer id = getIntent().getIntExtra("movieid", 0);
        Call<ReqMovieId> call = userService.getMovieById(id);
        call.enqueue(new Callback<ReqMovieId>() {
            @Override
            public void onResponse(Call<ReqMovieId> call, Response<ReqMovieId> response) {
                ReqMovieId reqMovieId = response.body();
                Movie movie = reqMovieId.getMovie();
                movieid = movie.getId();
                moviename.setText(movie.getName());
                moviecast.setText(movie.getCastStar());
                moviewriter.setText(movie.getDirector());
                moviegenre.setText(movie.getGenreId());
                movieproducer.setText(movie.getProducer());
                movielength.setText(movie.getLength().toString());
                moviesynopsis.setText(movie.getDescription());
                Picasso.with(context)
                        .load("http://cinema-xxii-server.herokuapp.com/profile?id="+movie.getName())
                        .resize(150, 150)
                        .centerCrop()
                        .into(movieimg);
            }

            @Override
            public void onFailure(Call<ReqMovieId> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
