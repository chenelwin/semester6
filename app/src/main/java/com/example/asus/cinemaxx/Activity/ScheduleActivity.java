package com.example.asus.cinemaxx.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.Movie;
import com.example.asus.cinemaxx.Model.Plaza;
import com.example.asus.cinemaxx.Model.ReqMovieId;
import com.example.asus.cinemaxx.Model.ReqPlazaId;
import com.example.asus.cinemaxx.Model.Schedule;
import com.example.asus.cinemaxx.R;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.example.asus.cinemaxx.Adapter.ScheduleAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {

    UserService userService = ApiUtils.getUserService();
    TextView plazaname;
    TextView moviename;
    TextView moviecast;
    TextView moviewriter;
    TextView moviegenre;
    TextView movieproducer;
    TextView movielength;
    ImageView movieimg;
    Integer movieid;
    ArrayList<Schedule> schedules;
    //List<Schedule> schedules;
    TextView datetext;
    Context context;
    RecyclerView rvSchedule;
    ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initView();

        schedules = getIntent().getParcelableArrayListExtra("schedulelist");
        datetext.setText(getIntent().getStringExtra("displaydate"));
        doGetPlazaId();
        doGetMovieId();

        scheduleAdapter = new ScheduleAdapter(schedules);
        Integer col = 2;
        rvSchedule.setLayoutManager(new GridLayoutManager(context, col));
        rvSchedule.setItemAnimator(new DefaultItemAnimator());
        rvSchedule.setAdapter(scheduleAdapter);
    }

    private void initView(){
        context = this;
        datetext = (TextView)findViewById(R.id.dateText);
        plazaname = (TextView)findViewById(R.id.plazanama);
        moviename = (TextView)findViewById(R.id.moviename);
        moviecast = (TextView)findViewById(R.id.moviecast);
        moviewriter = (TextView)findViewById(R.id.moviewriter);
        moviegenre = (TextView)findViewById(R.id.moviegenre);
        movieproducer = (TextView)findViewById(R.id.movieproducer);
        movielength = (TextView)findViewById(R.id.movielength);
        movieimg = (ImageView)findViewById(R.id.movieimg);
        rvSchedule = (RecyclerView)findViewById(R.id.RvSchedule);
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

    private void doGetPlazaId(){
        Integer id = getIntent().getIntExtra("plazaid", 0);
        Call<ReqPlazaId> call = userService.getPlazaById(id);
        call.enqueue(new Callback<ReqPlazaId>() {
            @Override
            public void onResponse(Call<ReqPlazaId> call, Response<ReqPlazaId> response) {
                ReqPlazaId reqPlazaId = response.body();
                Plaza plaza = reqPlazaId.getPlaza();
                plazaname.setText(plaza.getName());
            }

            @Override
            public void onFailure(Call<ReqPlazaId> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
