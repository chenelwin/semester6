package com.example.asus.cinemaxx;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.ReqRedeem;
import com.example.asus.cinemaxx.Model.ResRedeem;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.example.asus.cinemaxx.SharedPreferences.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedeemActivity extends AppCompatActivity {

    UserService userService = ApiUtils.getUserService();
    SharedPrefManager sharedPrefManager;
    Integer profileid;
    EditText vouchercode;
    Button btnRedeem;
    String voucher_code;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        context = this;
        sharedPrefManager = new SharedPrefManager(context);

        vouchercode = (EditText)findViewById(R.id.voucher_code);

        //profileid = getIntent().getIntExtra("profileid", 0);
        profileid = Integer.parseInt(sharedPrefManager.getSPId());

        btnRedeem = (Button)findViewById(R.id.btnRedeem);
        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voucher_code = vouchercode.getText().toString();
                if(validateRedeem(voucher_code)) {
                    progressDialog = ProgressDialog.show(RedeemActivity.this, null, "Please Wait..", true);
                    doRedeemCode();
                }
            }
        });
    }

    private boolean validateRedeem(String code){
        if(code == null || code.trim().length() == 0){
            Toast.makeText(this, "Voucher code is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doRedeemCode(){
        final ReqRedeem reqRedeem = new ReqRedeem();
        reqRedeem.setUser_id(profileid);
        reqRedeem.setVoucher_code(voucher_code);
        Call<ResRedeem> call = userService.redeemRequest(reqRedeem);
        call.enqueue(new Callback<ResRedeem>() {
            @Override
            public void onResponse(Call<ResRedeem> call, Response<ResRedeem> response) {
                if(response.isSuccessful()){
                    ResRedeem resRedeem = response.body();
                    if(resRedeem.isStatus()){
                        Toast.makeText(RedeemActivity.this, resRedeem.getMessage(), Toast.LENGTH_SHORT).show();
                        vouchercode.setText("");
                        progressDialog.dismiss();
                        //Intent intent = new Intent(RedeemActivity.this, RedeemActivity.class);
                        //startActivity(intent);
                        //finish();
                    }
                    else {
                        Toast.makeText(RedeemActivity.this, "incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RedeemActivity.this, "salah/sudah terpakai", Toast.LENGTH_SHORT).show();
                    vouchercode.setText("");
                    progressDialog.dismiss();
                }
            }


            @Override
            public void onFailure(Call<ResRedeem> call, Throwable t) {
                Toast.makeText(RedeemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
