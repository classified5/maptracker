package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by A.I on 25/11/2017.
 */

public class Coordinate {

    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("destination_id")
    @Expose
    private int destination_id;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;

    public Coordinate(int user_id, int destination_id, Double longitude, Double latitude) {
        this.user_id = user_id;
        this.destination_id = destination_id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(int destination_id) {
        this.destination_id = destination_id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
