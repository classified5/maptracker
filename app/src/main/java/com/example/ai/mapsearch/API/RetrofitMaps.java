package com.example.ai.mapsearch.API;

import com.example.ai.mapsearch.Model.Coordinate;
import com.example.ai.mapsearch.Model.Example;
import com.example.ai.mapsearch.Model.Response;
import com.example.ai.mapsearch.Model.ResponseDestination;
import com.example.ai.mapsearch.Model.ResponseParticipant;
import com.example.ai.mapsearch.Model.ResponseUser;

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
    Call<ResponseDestination> getDestination(@Field("id") int id);

    @POST("getparticipant")
    @FormUrlEncoded
    Call<ResponseParticipant> getParticipant(@Field("user_id") int userId,
                                             @Field("destination_id") int destinationId);

    @POST("login")
    @FormUrlEncoded
    Call<ResponseUser> getLogin(@Field("username") String username,
                                @Field("password") String password);

    @GET("getalldestination")
    Call<ResponseDestination> getAllDestination();

    @POST("setdestination")
    @FormUrlEncoded
    Call<Response> setDestination(@Field("user_id") int userId,
                                  @Field("name") String name,
                                  @Field("longitude") String longitude,
                                  @Field("latitude") String latitude);

    @POST("registeruser")
    @FormUrlEncoded
    Call<Response> setUser(@Field("username") String username,
                           @Field("name") String name,
                           @Field("password") String password);
}
