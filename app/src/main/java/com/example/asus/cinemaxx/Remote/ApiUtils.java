package com.example.asus.cinemaxx.Remote;

public class ApiUtils {

    public static final String BASE_URL = "https://cinema-xxii-server.herokuapp.com";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

}
