package com.example.asus.cinemaxx.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.cinemaxx.Activity.MovieByPlaza;
import com.example.asus.cinemaxx.Model.Plaza;
import com.example.asus.cinemaxx.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlazaFragmentAdapter extends RecyclerView.Adapter<PlazaFragmentAdapter.ViewHolder>{

    List<Plaza> plazas;
    Context context;

    public PlazaFragmentAdapter(List<Plaza> plazaList){ this.plazas = plazaList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_plazafragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Plaza plaza = plazas.get(holder.getAdapterPosition());
        holder.plazaNama.setText(plaza.getName());
        holder.plazaAlamat.setText(plaza.getStreet());

        Picasso.with(context)
                .load("http://cinema-xxii-server.herokuapp.com/profile?id=" + plaza.getName())
                .resize(150, 150)
                .centerCrop()
                .into(holder.plazaImg);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MovieByPlaza.class);
                intent.putExtra("plazaid", plaza.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plazas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView plazaImg;
        TextView plazaNama;
        TextView plazaAlamat;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            plazaImg = (ImageView)itemView.findViewById(R.id.plazaimg);
            plazaNama = (TextView)itemView.findViewById(R.id.plazanama);
            plazaAlamat = (TextView)itemView.findViewById(R.id.plazaalamat);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }

}
