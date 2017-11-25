package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by A.I on 25/11/2017.
 */

public class Destination {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("destination_latitude")
    private Double destinationLatitude;
    @SerializedName("destination_longitude")
    private Double destinationLongitude;

    public String getDestinationId() {
        return id;
    }

    public void setDestinationId(String id) {
        this.id = id;
    }

    public String getDestinationName() {
        return name;
    }

    public void setDestinationName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }
}
