package com.example.asus.cinemaxx.Remote;

import com.example.asus.cinemaxx.Model.Plaza;
import com.example.asus.cinemaxx.Model.ReqLogin;
import com.example.asus.cinemaxx.Model.ReqMovie;
import com.example.asus.cinemaxx.Model.ReqPlaza;
import com.example.asus.cinemaxx.Model.ResObj;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/login")
    Call<ResObj> loginRequest(@Body ReqLogin reqLogin);

    @POST("/register")
    Call<ResObj> registerRequest(@Body ReqLogin reqLogin);

    @GET("/plaza")
    Call<ReqPlaza> getPlazaRequest();

    @GET("/movie")
    Call<ReqMovie> getMovieRequest();

}
