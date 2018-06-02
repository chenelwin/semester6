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

import com.example.asus.cinemaxx.Model.User;
import com.example.asus.cinemaxx.SharedPreferences.SharedPrefManager;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

public class ProfileFragment extends Fragment {

    View view;
    TextView profilenama;
    TextView balance;
    LinearLayout btnLogout;
    LinearLayout btnRedeem;
    SharedPrefManager sharedPrefManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPrefManager = new SharedPrefManager(view.getContext());

        String tempprofilenama = sharedPrefManager.getSPNama();
        profilenama = (TextView)view.findViewById(R.id.profilenama);
        profilenama.setText(tempprofilenama);

        String tempbalance = sharedPrefManager.getSPBalance();
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

        btnRedeem = (LinearLayout)view.findViewById(R.id.btnRedeem);
        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RedeemActivity.class);
                Integer profileid = getActivity().getIntent().getIntExtra("profileid", 0);
                intent.putExtra("profileid", profileid);
                startActivity(intent);
            }
        });

        return view;

    }
}
