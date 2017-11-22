package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A.I on 21/11/2017.
 */

public class Example {

    @SerializedName("routes")
    @Expose
    private List<com.example.ai.mapsearch.Model.Route> routes = new ArrayList<>();

    public List<com.example.ai.mapsearch.Model.Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<com.example.ai.mapsearch.Model.Route> routes) {
        this.routes = routes;
    }
}
