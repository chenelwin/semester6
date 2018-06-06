package com.example.asus.cinemaxx.Remote;

import com.example.asus.cinemaxx.Model.Plaza;
import com.example.asus.cinemaxx.Model.ReqLogin;
import com.example.asus.cinemaxx.Model.ReqMovie;
import com.example.asus.cinemaxx.Model.ReqMovieId;
import com.example.asus.cinemaxx.Model.ReqPlaza;
import com.example.asus.cinemaxx.Model.ReqPlazaId;
import com.example.asus.cinemaxx.Model.ReqRedeem;
import com.example.asus.cinemaxx.Model.ReqUserId;
import com.example.asus.cinemaxx.Model.ResObj;
import com.example.asus.cinemaxx.Model.ResRedeem;
import com.example.asus.cinemaxx.Model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @POST("/login")
    Call<ResObj> loginRequest(@Body ReqLogin reqLogin);

    @POST("/register")
    Call<ResObj> registerRequest(@Body ReqLogin reqLogin);

    @POST("/voucher/redeem")
    Call<ResRedeem> redeemRequest(@Body ReqRedeem reqRedeem);

    @GET("/plaza")
    Call<ReqPlaza> getPlazaRequest();

    @GET("/movie")
    Call<ReqMovie> getMovieRequest();

    @GET("/movie/{id}")
    Call<ReqMovieId> getMovieById(@Path("id") Integer id);

    @GET("/plaza/{id}")
    Call<ReqPlazaId> getPlazaById(@Path("id") Integer id);

    @GET("/movie/{id}/schedule")
    Call<ReqPlaza> getPlazaByMovie(@Path("id") Integer id,
                                   @Query("date") String date);

    @GET("/plaza/{id}/schedule")
    Call<ReqMovie> getMovieByPlaza(@Path("id") Integer id,
                                   @Query("date") String date);

    @GET("/user/{id}")
    Call<ReqUserId> getUserById(@Path("id") Integer id);

    @GET("/user/{id}/history")
    Call<ReqUserId> getHistoryById(@Path("id") Integer id);

    @GET("/schedule/{id}/tickets")
    Call<ReqUserId> getTicketData(@Path("id") Integer id);


}
