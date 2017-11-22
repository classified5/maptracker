package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by A.I on 21/11/2017.
 */

public class OverviewPolyline {


    @SerializedName("points")
    @Expose
    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
