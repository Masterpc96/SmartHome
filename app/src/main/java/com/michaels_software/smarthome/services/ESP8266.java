package com.michaels_software.smarthome.services;

import com.michaels_software.smarthome.ownClasses.Day;
import com.michaels_software.smarthome.ownClasses.Level;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ESP8266 {
    @GET("getProgress")
    public Call<ResponseBody> getLevel();

    @GET("calibrated")
    public Call<ResponseBody> getCalibrated();

    @GET("getPlan")
    public Call<ArrayList<Day>> getPlan();

    @POST("moveBlind")
    public Call<Level> sendLevel(@Body Level level);

    @POST("setWeekPlan")
    public Call<ArrayList<Day>> sendDays(@Body ArrayList<Day> days);

}
