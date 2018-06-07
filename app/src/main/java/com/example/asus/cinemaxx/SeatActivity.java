package com.example.asus.cinemaxx;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.ReqTicket;
import com.example.asus.cinemaxx.Model.Seat;
import com.example.asus.cinemaxx.Model.Ticket;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatActivity extends AppCompatActivity {

    UserService userService = ApiUtils.getUserService();
    List<Seat> seats;
    RecyclerView RvSeat;
    SeatAdapter seatAdapter;
    String[] takenSeat;
    int[] convertedTexttoNumSeat;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        context = this;
        doGetTicketData();
        RvSeat = (RecyclerView)findViewById(R.id.RvSeat);

    }

    private List<Seat> doCreateSeat(){
        ArrayList<Seat> items = new ArrayList<>();
        for(int i=0; i<100; i++){
            Seat seat = new Seat();
            boolean taken = false;

            for(int j=0; j<convertedTexttoNumSeat.length; j++){
                if(i==convertedTexttoNumSeat[j]){
                    taken = true;
                    break;
                }
            }
            if(taken){
                seat.setStatus(2);
                seat.setSeatimg(R.drawable.seat2);
            }
            else{
                seat.setStatus(0);
                seat.setSeatimg(R.drawable.seat0);
            }
            items.add(seat);
        }
        return items;
    }

    private void doGetTicketData(){
        Integer scheduleid = getIntent().getIntExtra("scheduleid", 0);
        Call<ReqTicket> call = userService.getTicketData(scheduleid);
        call.enqueue(new Callback<ReqTicket>() {
            @Override
            public void onResponse(Call<ReqTicket> call, Response<ReqTicket> response) {
                ReqTicket reqTicket = response.body();
                List<Ticket> tickets = reqTicket.getTickets();
                takenSeat = new String[tickets.size()];
                convertedTexttoNumSeat = new int[takenSeat.length];
                for(int i=0; i<takenSeat.length; i++){
                    takenSeat[i] = tickets.get(i).getSeat_no();
                }
                for(int i=0; i<convertedTexttoNumSeat.length; i++){
                    String text = takenSeat[i];
                    char[] a = text.toCharArray();
                    //convert huruf jd angka depan
                    String finalHuruf = "";
                    int tempascii = (int)a[0];
                    finalHuruf+= (tempascii-65);
                    //return (B return 1, C return 2, dst)

                    //convert angka blkg jd angka blkg
                    String finalAngka = "";
                    for(int j=1; j<a.length; j++){
                        finalAngka+=a[j];
                    }
                    Integer temp = Integer.parseInt(finalAngka)-1;
                    finalAngka = temp.toString();
                    //return (10 return 9, 1 return 0, 2 return 1, dst

                    //gabung (B6 -> (1 dan 5) -> posisi 15)
                    String finalGabung = finalHuruf+finalAngka;
                    convertedTexttoNumSeat[i] = Integer.parseInt(finalGabung);

                }
                seats = doCreateSeat();
                seatAdapter = new SeatAdapter(seats);

                int col=10;
                RvSeat.setLayoutManager(new GridLayoutManager(context, col));
                RvSeat.setItemAnimator(new DefaultItemAnimator());
                RvSeat.setAdapter(seatAdapter);
            }

            @Override
            public void onFailure(Call<ReqTicket> call, Throwable t) {
                Toast.makeText(SeatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
