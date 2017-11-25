package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by A.I on 25/11/2017.
 */

public class Coordinate {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("destination_id")
    @Expose
    private String destination_id;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;

    public Coordinate(String user_id, String destination_id, Double longitude, Double latitude) {
        this.user_id = user_id;
        this.destination_id = destination_id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
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
