package com.example.asus.cinemaxx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.Plaza;
import com.example.asus.cinemaxx.Model.ReqPlaza;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlazaFragment extends Fragment {

    UserService userService = ApiUtils.getUserService();
    public View view;
    //public static List<Plaza> plazaList;
    private RecyclerView rvPlazaFragment;
    private PlazaFragmentAdapter plazaFragmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plaza, container, false);
        rvPlazaFragment = (RecyclerView)view.findViewById(R.id.RvPlazaFragment);
        doGetPlazaData();
        return view;
    }

    private void doGetPlazaData(){
        Call<ReqPlaza> call = userService.getPlazaRequest();
        call.enqueue(new Callback<ReqPlaza>() {
            @Override
            public void onResponse(Call<ReqPlaza> call, Response<ReqPlaza> response) {
                ReqPlaza reqPlaza = response.body();
                List<Plaza> plazas = reqPlaza.getPlazas();
                plazaFragmentAdapter = new PlazaFragmentAdapter(plazas);

                rvPlazaFragment.setLayoutManager(new LinearLayoutManager(view.getContext()));
                rvPlazaFragment.setItemAnimator(new DefaultItemAnimator());
                rvPlazaFragment.setAdapter(plazaFragmentAdapter);
            }

            @Override
            public void onFailure(Call<ReqPlaza> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
