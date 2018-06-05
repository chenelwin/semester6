package com.example.asus.cinemaxx;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.cinemaxx.Model.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatActivity extends AppCompatActivity {

    List<Seat> seats;
    RecyclerView RvSeat;
    SeatAdapter seatAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        context = this;

        seats = doCreateSeat();
        RvSeat = (RecyclerView)findViewById(R.id.RvSeat);
        seatAdapter = new SeatAdapter(seats);

        int col=10;
        RvSeat.setLayoutManager(new GridLayoutManager(context, col));
        RvSeat.setItemAnimator(new DefaultItemAnimator());
        RvSeat.setAdapter(seatAdapter);

    }

    private List<Seat> doCreateSeat(){
        ArrayList<Seat> items = new ArrayList<>();
        for(int i=0; i<100; i++){
            Seat seat = new Seat();
            seat.setSeat(R.drawable.seat0);
            items.add(seat);
        }
        return items;
    }
}
