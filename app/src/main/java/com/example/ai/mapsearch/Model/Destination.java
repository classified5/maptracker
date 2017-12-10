package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by A.I on 25/11/2017.
 */

public class Destination implements Serializable {

    @SerializedName("destination_id")
    private int id;
    @SerializedName("destination_name")
    private String name;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("destination_latitude")
    private Double destinationLatitude;
    @SerializedName("destination_longitude")
    private Double destinationLongitude;
    @SerializedName("name")
    private String destinationBy;

    public String getDestinationBy() {
        return destinationBy;
    }

    public void setDestinationBy(String destinationBy) {
        this.destinationBy = destinationBy;
    }

    public int getDestinationId() {
        return id;
    }

    public void setDestinationId(int id) {
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
