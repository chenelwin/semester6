package com.example.asus.cinemaxx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.ReqLogin;
import com.example.asus.cinemaxx.Model.ResObj;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText regisEmail;
    EditText regisName;
    EditText regisPassword;
    EditText regisRePassword;
    TextView textLogin;
    Button btnRegister;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regisEmail = (EditText) findViewById(R.id.regisEmail);
        regisName = (EditText) findViewById(R.id.regisName);
        regisPassword = (EditText) findViewById(R.id.regisPassword);
        regisRePassword = (EditText) findViewById(R.id.regisRePassword);
        textLogin = (TextView)findViewById(R.id.textLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        userService = ApiUtils.getUserService();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regisEmail.getText().toString();
                String name = regisName.getText().toString();
                String password = regisPassword.getText().toString();
                String repassword = regisRePassword.getText().toString();

                if(validateRegister(email, name, password, repassword)){
                    doRegister(email, name, password);
                }
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean validateRegister(String email, String name, String password, String repassword){
        if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name == null || name.trim().length() == 0){
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(repassword == null || repassword.trim().length() == 0){
            Toast.makeText(this, "Repeat Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(repassword)){
            Toast.makeText(this, "Repeat Password must be the same as Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doRegister(String email, String name, String password){
        ReqLogin reqLogin = new ReqLogin();
        reqLogin.setEmail(email);
        reqLogin.setName(name);
        reqLogin.setPassword(password);
        Call<ResObj> call = userService.registerRequest(reqLogin);
        call.enqueue(new Callback<ResObj>() {
            @Override
            public void onResponse(Call<ResObj> call, Response<ResObj> response) {
                if(response.isSuccessful()) {
                    ResObj resObj = response.body();
                    Toast.makeText(RegisterActivity.this, "Register ID "+resObj.getUser().getId(), Toast.LENGTH_LONG).show();
                    if (resObj.isStatus()) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResObj> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
