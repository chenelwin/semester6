package com.example.asus.cinemaxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

public class ProfileFragment extends Fragment {

    public View view;
    public TextView profilenama;
    LinearLayout btnLogout;
    LinearLayout btnRedeem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilenama = (TextView)view.findViewById(R.id.profilenama);
        profilenama.setText(getActivity().getIntent().getStringExtra("profilenama"));

        btnLogout = (LinearLayout)view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
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
}
