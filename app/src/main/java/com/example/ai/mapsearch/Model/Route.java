package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A.I on 21/11/2017.
 */

public class Route {

    @SerializedName("legs")
    @Expose
    private List<Leg> legs = new ArrayList<Leg>();
    @SerializedName("overview_polyline")
    @Expose
    private OverviewPolyline overviewPolyline;

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }
}
