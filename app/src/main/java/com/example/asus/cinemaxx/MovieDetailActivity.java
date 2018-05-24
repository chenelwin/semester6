package com.example.asus.cinemaxx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    TextView moviename;
    TextView moviecast;
    TextView moviewriter;
    TextView moviegenre;
    TextView movieproducer;
    TextView movielength;
    TextView moviesynopsis;
    Button btnBuyTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        moviename = (TextView)findViewById(R.id.moviename);
        moviename.setText(getIntent().getStringExtra("moviename"));
        moviecast = (TextView)findViewById(R.id.moviecast);
        moviecast.setText(getIntent().getStringExtra("moviecast"));
        moviewriter = (TextView)findViewById(R.id.moviewriter);
        moviewriter.setText(getIntent().getStringExtra("moviewriter"));
        moviegenre = (TextView)findViewById(R.id.moviegenre);
        moviegenre.setText(getIntent().getStringExtra("moviegenre"));
        movieproducer = (TextView)findViewById(R.id.movieproducer);
        movieproducer.setText(getIntent().getStringExtra("movieproducer"));
        movielength = (TextView)findViewById(R.id.movielength);
        movielength.setText(getIntent().getStringExtra("movielength"));
        moviesynopsis = (TextView)findViewById(R.id.moviesynopsis);
        moviesynopsis.setText(getIntent().getStringExtra("moviesynopsis"));

        btnBuyTicket = (Button)findViewById(R.id.btnBuyTicket);
        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, PlazaByMovie.class);
                startActivity(intent);
            }
        });
    }
}
