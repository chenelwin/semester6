package com.example.asus.cinemaxx;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {


    Context context;

    public SeatAdapter(){}

    @Override
    public SeatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_seat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SeatAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView seattext;
        LinearLayout seatimg;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            seattext = (TextView)itemView.findViewById(R.id.seattext);
            seatimg = (LinearLayout)itemView.findViewById(R.id.seatimg);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}
