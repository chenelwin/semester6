package com.example.asus.cinemaxx.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asus.cinemaxx.Model.Seat;
import com.example.asus.cinemaxx.R;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {

    List<Seat> seats;
    Context context;

    public SeatAdapter(List<Seat> seatList){ this.seats = seatList;}

    @Override
    public SeatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_seat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SeatAdapter.ViewHolder holder, final int position) {
        final Seat seat = seats.get(holder.getAdapterPosition());
        holder.imgSeat.setImageResource(seat.getSeatimg());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seat.getStatus()==0) {
                    holder.imgSeat.setImageResource(R.drawable.seat1);
                    seat.setStatus(1);
                }
                else if(seat.getStatus()==1) {
                    holder.imgSeat.setImageResource(R.drawable.seat0);
                    seat.setStatus(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSeat;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            imgSeat = (ImageView) itemView.findViewById(R.id.imgSeat);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}
