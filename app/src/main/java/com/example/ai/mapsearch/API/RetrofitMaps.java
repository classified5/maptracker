package com.example.ai.mapsearch.API;

import com.example.ai.mapsearch.Model.Coordinate;
import com.example.ai.mapsearch.Model.Example;
import com.example.ai.mapsearch.Model.Response;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by A.I on 21/11/2017.
 */

public interface RetrofitMaps {

    @GET("json?")
    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("type") String mode);

    @POST("setcoordinate")
    Call<Coordinate> setCoordinate(@Body Coordinate coordinate);

    @POST("getdestination")
    @FormUrlEncoded
    Call<Response> getDestination(@Field("id") String id);
}
