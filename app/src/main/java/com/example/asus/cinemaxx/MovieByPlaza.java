package com.example.asus.cinemaxx;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.Movie;
import com.example.asus.cinemaxx.Model.Plaza;
import com.example.asus.cinemaxx.Model.ReqMovie;
import com.example.asus.cinemaxx.Model.ReqPlazaId;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieByPlaza extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, MovieByPlazaAdapter.PassingData {

    UserService userService = ApiUtils.getUserService();
    String displaydate;
    Integer plazaid;
    TextView plazanamaheader;
    TextView plazanama;
    TextView plazaalamat;
    ImageView plazaimg;
    Context context;
    ImageView btnCalendar;
    RecyclerView rvMovieByPlaza;
    MovieByPlazaAdapter movieByPlazaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_by_plaza);
        context = this;
        rvMovieByPlaza = (RecyclerView)findViewById(R.id.RvMovieByPlaza);
        plazanamaheader = (TextView)findViewById(R.id.plazanamaheader);
        plazanama = (TextView)findViewById(R.id.plazanama);
        plazaalamat = (TextView)findViewById(R.id.plazaalamat);
        plazaimg = (ImageView)findViewById(R.id.plazaimg);

        doGetPlazaId();

        MovieByPlazaAdapter.passingData = this;

        btnCalendar = (ImageView)findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
    }

    @Override
    public void passData(Integer id){
        Intent intent = new Intent(MovieByPlaza.this, ScheduleActivity.class);
        intent.putExtra("movieid", id);
        intent.putExtra("plazaid", plazaid);
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
        TextView textView = (TextView)findViewById(R.id.dateText);
        //textView.setText(currentDateString);
        displaydate = formatday+" "+formatmonth+" "+formatyear;
        textView.setText(displaydate);
        doGetMovieByPlaza(formatmonthint, formatday, formatyear);
    }

    private void doGetPlazaId(){
        plazaid = getIntent().getIntExtra("plazaid", 0);
        Call<ReqPlazaId> call = userService.getPlazaById(plazaid);
        call.enqueue(new Callback<ReqPlazaId>() {
            @Override
            public void onResponse(Call<ReqPlazaId> call, Response<ReqPlazaId> response) {
                ReqPlazaId reqPlazaId = response.body();
                Plaza plaza = reqPlazaId.getPlaza();
                plazaid = plaza.getId();
                plazanama.setText(plaza.getName());
                plazanamaheader.setText(plaza.getName());
                plazaalamat.setText(plaza.getStreet());
                Picasso.with(context)
                        .load("http://cinema-xxii-server.herokuapp.com/profile?id="+plaza.getName())
                        .resize(150, 150)
                        .centerCrop()
                        .into(plazaimg);
            }

            @Override
            public void onFailure(Call<ReqPlazaId> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doGetMovieByPlaza(String m, String d, String y){
        Integer id = getIntent().getIntExtra("plazaid", 0);
        String date = m+"-"+d+"-"+y;
        Call<ReqMovie> call = userService.getMovieByPlaza(id, date);
        call.enqueue(new Callback<ReqMovie>() {
            @Override
            public void onResponse(Call<ReqMovie> call, Response<ReqMovie> response) {
                ReqMovie reqMovie = response.body();
                List<Movie> movies = reqMovie.getMovies();
                movieByPlazaAdapter = new MovieByPlazaAdapter(movies);
                Integer col = 3;
                rvMovieByPlaza.setLayoutManager(new GridLayoutManager(context, col));
                rvMovieByPlaza.setItemAnimator(new DefaultItemAnimator());
                rvMovieByPlaza.setAdapter(movieByPlazaAdapter);
            }

            @Override
            public void onFailure(Call<ReqMovie> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
