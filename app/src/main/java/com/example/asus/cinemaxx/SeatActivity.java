package com.example.asus.cinemaxx;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.ReqBuySeat;
import com.example.asus.cinemaxx.Model.ReqTicket;
import com.example.asus.cinemaxx.Model.ResBuySeat;
import com.example.asus.cinemaxx.Model.Seat;
import com.example.asus.cinemaxx.Model.Ticket;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.example.asus.cinemaxx.SharedPreferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatActivity extends AppCompatActivity {

    UserService userService = ApiUtils.getUserService();
    SharedPrefManager sharedPrefManager;
    List<Seat> seats;
    List<Integer> selected;
    RecyclerView RvSeat;
    SeatAdapter seatAdapter;
    Button btnBuySeat;
    String[] takenSeat;
    String[] sendSeat;
    int[] convertedTexttoNumSeat;
    ProgressDialog progressDialog;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        context = this;
        sharedPrefManager = new SharedPrefManager(context);
        doGetTicketData();
        RvSeat = (RecyclerView)findViewById(R.id.RvSeat);

        btnBuySeat = (Button)findViewById(R.id.btnBuySeat);
        btnBuySeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = new ArrayList<Integer>();
                for(int i=0; i<100; i++){
                    if(seats.get(i).getStatus()==1){
                        selected.add(i);
                    }
                }
                progressDialog = ProgressDialog.show(SeatActivity.this, null, "Please Wait..", true);
                if(selected.size()==0){
                    Toast.makeText(context, "PILIH KURSI", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {
                    Integer price = getIntent().getIntExtra("scheduleprice", 0);
                    Integer balance = Integer.parseInt(sharedPrefManager.getSPBalance());
                    if(price*selected.size() > balance){
                        Toast.makeText(SeatActivity.this, "SALDO TIDAK CUKUP", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else{
                        sendSeat = new String[selected.size()];
                        for (int i = 0; i < sendSeat.length; i++) {
                            doConvertPosToSeat(i);
                        }
                        doBuySeat();
                    }
                }
            }
        });

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
                    doConvertSeatToPos(i);
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

    private void doConvertSeatToPos(int index){
        String text = takenSeat[index];
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
        convertedTexttoNumSeat[index] = Integer.parseInt(finalGabung);
    }

    private void doConvertPosToSeat(int index){
        String tempdepan = "";
        String tempbelakang = "";
        tempdepan = "" + (char)((selected.get(index)/10)+65);
        tempbelakang = "" + ((selected.get(index)%10)+1);
        String gabung = tempdepan+tempbelakang;
        sendSeat[index] = gabung;
    }

    private void doBuySeat(){
        final ReqBuySeat reqBuySeat = new ReqBuySeat();
        Integer id = getIntent().getIntExtra("scheduleid", 0);
        reqBuySeat.setSchedule_id(id);
        reqBuySeat.setUser_id(Integer.parseInt(sharedPrefManager.getSPId()));
        reqBuySeat.setSeats(sendSeat);
        Call<ResBuySeat> call = userService.buySeatRequest(reqBuySeat);
        call.enqueue(new Callback<ResBuySeat>() {
            @Override
            public void onResponse(Call<ResBuySeat> call, Response<ResBuySeat> response) {
                if(response.isSuccessful()){
                    ResBuySeat resBuySeat = response.body();
                    if(resBuySeat.isStatus()){
                        Intent intent = new Intent(SeatActivity.this, HomeActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Toast.makeText(SeatActivity.this, ""+resBuySeat.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SeatActivity.this, "incorrect", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else {
                    Toast.makeText(SeatActivity.this, "salah", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResBuySeat> call, Throwable t) {
                Toast.makeText(SeatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
