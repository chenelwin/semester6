package com.example.asus.cinemaxx.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.cinemaxx.Model.Order;
import com.example.asus.cinemaxx.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<Order> orders;
    Context context;

    public HistoryAdapter(List<Order> orderList){ this.orders = orderList;}

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        final Order order = orders.get(holder.getAdapterPosition());
        holder.orderdate.setText(order.getOrder_date().substring(0,10));
        holder.ordermovie.setText(order.getMovie_name());
        String temp = "";
        for(int i=0; i<order.getSeats().length; i++){
            temp+= order.getSeats()[i]+" ";
        }
        holder.orderseat.setText(temp);
        holder.orderstarthour.setText(order.getStart_hour());
        holder.orderticketdate.setText(order.getDate());
        holder.ordercode.setText(order.getOrder_id());
        holder.orderprice.setText(order.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderdate;
        TextView ordermovie;
        TextView orderseat;
        TextView orderstarthour;
        TextView orderticketdate;
        TextView ordercode;
        TextView orderprice;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            orderdate = (TextView)itemView.findViewById(R.id.orderdate);
            ordermovie = (TextView)itemView.findViewById(R.id.ordermovie);
            orderseat = (TextView)itemView.findViewById(R.id.orderseat);
            orderstarthour = (TextView)itemView.findViewById(R.id.orderstarthour);
            orderticketdate = (TextView)itemView.findViewById(R.id.orderticketdate);
            ordercode = (TextView)itemView.findViewById(R.id.ordercode);
            orderprice = (TextView)itemView.findViewById(R.id.orderprice);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}
