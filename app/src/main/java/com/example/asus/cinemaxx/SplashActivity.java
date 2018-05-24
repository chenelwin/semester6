package com.example.asus.cinemaxx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(1000);
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
