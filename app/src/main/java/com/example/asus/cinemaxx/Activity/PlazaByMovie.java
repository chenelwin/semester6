package com.example.asus.cinemaxx.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cinemaxx.Fragment.DatePickerFragment;
import com.example.asus.cinemaxx.Model.Movie;
import com.example.asus.cinemaxx.Model.Plaza;
import com.example.asus.cinemaxx.Model.ReqMovieId;
import com.example.asus.cinemaxx.Model.ReqPlaza;
import com.example.asus.cinemaxx.Model.Schedule;
import com.example.asus.cinemaxx.Adapter.PlazaByMovieAdapter;
import com.example.asus.cinemaxx.R;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlazaByMovie extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, PlazaByMovieAdapter.PassingData {

    Context context;
    UserService userService = ApiUtils.getUserService();
    String displaydate;
    ImageView btnCalendar;
    TextView moviename;
    TextView moviecast;
    TextView moviewriter;
    TextView moviegenre;
    TextView movieproducer;
    TextView movielength;
    TextView textdate;
    ImageView movieimg;
    List<Plaza> plazas;
    ArrayList<Schedule> schedules;
    Integer movieid;
    RecyclerView rvPlazaByMovie;
    PlazaByMovieAdapter plazaByMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_by_movie);
        initView();

        doGetMovieId();

        PlazaByMovieAdapter.passingData = this;

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

    }

    @Override
    public void passData(Integer id, int position){
        Intent intent = new Intent(PlazaByMovie.this, ScheduleActivity.class);
        schedules = plazas.get(position).getSchedules();
        intent.putParcelableArrayListExtra("schedulelist", schedules);
        intent.putExtra("movieid", movieid);
        intent.putExtra("plazaid", id);
        intent.putExtra("displaydate", displaydate);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String formatday = "" + dayOfMonth;
        if(Integer.parseInt(formatday)<10){
            formatday = "0"+ formatday;
        }
        String formatmonthint = "" + (month+1);
        if(Integer.parseInt(formatmonthint)<10){
            formatmonthint = "0"+formatmonthint;
        }
        String formatmonth = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        String formatyear = "" + year;
        //textView.setText(currentDateString);
        displaydate = formatday+" "+formatmonth+" "+formatyear;
        textdate.setText(displaydate);
        doGetPlazaByMovie(formatmonthint, formatday, formatyear);
    }

    private void initView(){
        context = this;
        rvPlazaByMovie = (RecyclerView)findViewById(R.id.RvPlazaByMovie);
        initTextDate();
        moviename = (TextView)findViewById(R.id.moviename);
        moviecast = (TextView)findViewById(R.id.moviecast);
        moviewriter = (TextView)findViewById(R.id.moviewriter);
        moviegenre = (TextView)findViewById(R.id.moviegenre);
        movieproducer = (TextView)findViewById(R.id.movieproducer);
        movielength = (TextView)findViewById(R.id.movielength);
        movieimg = (ImageView)findViewById(R.id.movieimg);
        btnCalendar = (ImageView)findViewById(R.id.btnCalendar);
    }

    private void initTextDate(){
        DateFormat dateday = new SimpleDateFormat("dd");
        DateFormat datemonth = new SimpleDateFormat("MMM");
        DateFormat dateyear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String dateinit = ""+dateday.format(date)+" "+datemonth.format(date)+" "+dateyear.format(date);
        textdate = (TextView)findViewById(R.id.dateText);
        textdate.setText(dateinit);
    }

    private void doGetMovieId(){
        movieid = getIntent().getIntExtra("movieid", 0);
        Call<ReqMovieId> call = userService.getMovieById(movieid);
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

    private void doGetPlazaByMovie(String m, String d, String y){
        Integer id = getIntent().getIntExtra("movieid", 0);
        String date = m+"-"+d+"-"+y;
        Call<ReqPlaza> call = userService.getPlazaByMovie(id, date);
        call.enqueue(new Callback<ReqPlaza>() {
            @Override
            public void onResponse(Call<ReqPlaza> call, Response<ReqPlaza> response) {
                ReqPlaza reqPlaza = response.body();
                plazas = reqPlaza.getPlazas();
                plazaByMovieAdapter = new PlazaByMovieAdapter(plazas);

                rvPlazaByMovie.setLayoutManager(new LinearLayoutManager(context));
                rvPlazaByMovie.setItemAnimator(new DefaultItemAnimator());
                rvPlazaByMovie.setAdapter(plazaByMovieAdapter);
            }

            @Override
            public void onFailure(Call<ReqPlaza> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
