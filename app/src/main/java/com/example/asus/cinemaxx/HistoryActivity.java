package com.example.asus.cinemaxx;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.cinemaxx.Model.Order;
import com.example.asus.cinemaxx.Model.ReqOrder;
import com.example.asus.cinemaxx.Remote.ApiUtils;
import com.example.asus.cinemaxx.Remote.UserService;
import com.example.asus.cinemaxx.SharedPreferences.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    UserService userService = ApiUtils.getUserService();
    SharedPrefManager sharedPrefManager;
    RecyclerView rvHistory;
    HistoryAdapter historyAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        doGetHistory();
    }

    private void initView(){
        context = this;
        sharedPrefManager = new SharedPrefManager(context);
        rvHistory = (RecyclerView)findViewById(R.id.RvHistory);
    }

    private void doGetHistory(){
        Integer id = Integer.parseInt(sharedPrefManager.getSPId());
        Call<ReqOrder> call = userService.getHistoryById(id);
        call.enqueue(new Callback<ReqOrder>() {
            @Override
            public void onResponse(Call<ReqOrder> call, Response<ReqOrder> response) {
                ReqOrder reqOrder = response.body();
                List<Order> orders = reqOrder.getOrders();
                Log.e("berhasillist","test");
                historyAdapter = new HistoryAdapter(orders);

                rvHistory.setLayoutManager(new LinearLayoutManager(context));
                rvHistory.setItemAnimator(new DefaultItemAnimator());
                rvHistory.setAdapter(historyAdapter);
            }

            @Override
            public void onFailure(Call<ReqOrder> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
