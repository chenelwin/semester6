package com.example.asus.cinemaxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.ReqUserId;
import com.example.asus.cinemaxx.Model.User;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.example.asus.cinemaxx.SharedPreferences.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

public class ProfileFragment extends Fragment {

    UserService userService = ApiUtils.getUserService();
    View view;
    TextView profilenama;
    TextView balance;
    LinearLayout btnLogout;
    LinearLayout btnRedeem;
    LinearLayout btnHistory;
    SharedPrefManager sharedPrefManager;
    String tempprofilenama;
    String tempbalance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPrefManager = new SharedPrefManager(view.getContext());

        tempprofilenama = sharedPrefManager.getSPNama();
        profilenama = (TextView)view.findViewById(R.id.profilenama);
        profilenama.setText(tempprofilenama);
        tempbalance = sharedPrefManager.getSPBalance();
        balance = (TextView)view.findViewById(R.id.balance);
        balance.setText(tempbalance);

        btnLogout = (LinearLayout)view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, "");
                sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, "");
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, "");
                sharedPrefManager.saveSPString(SharedPrefManager.SP_BALANCE, "");
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnHistory = (LinearLayout)view.findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        btnRedeem = (LinearLayout)view.findViewById(R.id.btnRedeem);
        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RedeemActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        doGetUserById();
    }

    private void doGetUserById(){
        Integer id = Integer.parseInt(sharedPrefManager.getSPId());
        Call<ReqUserId> call = userService.getUserById(id);
        call.enqueue(new Callback<ReqUserId>() {
            @Override
            public void onResponse(Call<ReqUserId> call, Response<ReqUserId> response) {
                ReqUserId reqUserId = response.body();
                User user = reqUserId.getUser();
                String newbalance = user.getPoint().toString();
                sharedPrefManager.saveSPString(SharedPrefManager.SP_BALANCE, newbalance);
                balance = (TextView)view.findViewById(R.id.balance);
                balance.setText(newbalance);
                //Log.e("resumenewbalance", sharedPrefManager.getSPBalance().toString());
            }

            @Override
            public void onFailure(Call<ReqUserId> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
