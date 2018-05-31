package com.example.asus.cinemaxx;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.cinemaxx.Model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{

    ArrayList<Schedule> schedules;
    Context context;

    public ScheduleAdapter(ArrayList<Schedule> scheduleList){
        this.schedules = scheduleList;
    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_schedule, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {
        final Schedule schedule = schedules.get(holder.getAdapterPosition());
        holder.tickettime.setText(schedule.getStartHour());
        holder.tickettype.setText("("+schedule.getType()+")");
        holder.ticketprice.setText("Rp. "+schedule.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tickettime;
        TextView ticketprice;
        TextView tickettype;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            tickettime = (TextView)itemView.findViewById(R.id.tickettime);
            ticketprice = (TextView)itemView.findViewById(R.id.ticketprice);
            tickettype = (TextView)itemView.findViewById(R.id.tickettype);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}
