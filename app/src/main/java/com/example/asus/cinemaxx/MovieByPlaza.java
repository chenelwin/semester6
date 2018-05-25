package com.example.asus.cinemaxx;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.Plaza;
import com.example.asus.cinemaxx.Model.ReqPlazaId;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieByPlaza extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    UserService userService = ApiUtils.getUserService();
    Integer id;
    TextView plazanamaheader;
    TextView plazanama;
    TextView plazaalamat;
    ImageView plazaimg;
    Context context;
    ImageView btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_by_plaza);
        context = this;
        plazanamaheader = (TextView)findViewById(R.id.plazanamaheader);
        plazanama = (TextView)findViewById(R.id.plazanama);
        plazaalamat = (TextView)findViewById(R.id.plazaalamat);
        plazaimg = (ImageView)findViewById(R.id.plazaimg);

        doGetPlazaId();

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
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String formatday = "" + dayOfMonth;
        String formatmonth = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        String formatyear = "" + year;
        TextView textView = (TextView)findViewById(R.id.dateText);
        //textView.setText(currentDateString);
        String displaydate = formatday+" "+formatmonth+" "+formatyear;
        textView.setText(displaydate);
    }

    private void doGetPlazaId(){
        Integer id = getIntent().getIntExtra("plazaid", 0);
        Call<ReqPlazaId> call = userService.getPlazaById(id);
        call.enqueue(new Callback<ReqPlazaId>() {
            @Override
            public void onResponse(Call<ReqPlazaId> call, Response<ReqPlazaId> response) {
                ReqPlazaId reqPlazaId = response.body();
                Plaza plaza = reqPlazaId.getPlaza();
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
}
