package com.example.asus.cinemaxx;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.ReqLogin;
import com.example.asus.cinemaxx.Model.ResObj;
import com.example.asus.cinemaxx.Model.User;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.example.asus.cinemaxx.SharedPreferences.SharedPrefManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPassword;
    Button btnLogin;
    UserService userService = ApiUtils.getUserService();
    TextView textRegister;
    ProgressDialog progressDialog;
    Context context;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        if(sharedPrefManager.getSPSudahLogin()){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            String passingnama = sharedPrefManager.getSPNama();
            String passingpoint = sharedPrefManager.getSPBalance();
            intent.putExtra("profilenama", passingnama);
            intent.putExtra("balance", passingpoint);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if(validateLogin(email, password)){
                    progressDialog = ProgressDialog.show(context, null, "Please Wait..", true);
                    doLogin(email, password);
                }
            }
        });

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initView(){
        sharedPrefManager = new SharedPrefManager(this);
        context = this;
        loginEmail = (EditText)findViewById(R.id.loginEmail);
        loginPassword = (EditText)findViewById(R.id.loginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        textRegister = (TextView)findViewById(R.id.textRegister);
    }

    private boolean validateLogin(String email, String password){
        if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(String email, String password){
        final ReqLogin reqLogin = new ReqLogin();
        reqLogin.setEmail(email);
        reqLogin.setPassword(password);
        Call<ResObj> call = userService.loginRequest(reqLogin);
        call.enqueue(new Callback<ResObj>() {
            @Override
            public void onResponse(Call<ResObj> call, Response<ResObj> response) {
                if(response.isSuccessful()) {
                    ResObj resObj = response.body();
                    Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_LONG).show();
                    if (resObj.isStatus()) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        String passingid = resObj.getUser().getId().toString();
                        String passingnama = resObj.getUser().getName();
                        String passingemail = resObj.getUser().getEmail();
                        String passingpoint = resObj.getUser().getPoint().toString();
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, passingemail);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, passingid);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, passingnama);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_BALANCE, passingpoint);
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResObj> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}
