package com.example.ai.mapsearch;

import com.example.ai.mapsearch.Model.Example;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by A.I on 21/11/2017.
 */

public interface RetrofitMaps {

    @GET("json?")
    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("type") String mode);

}
