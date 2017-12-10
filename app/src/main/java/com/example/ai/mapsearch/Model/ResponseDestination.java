package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by A.I on 10/12/2017.
 */

public class ResponseDestination extends Response{

    @SerializedName("data")
    private List<Destination> data;

    public List<Destination> getData() {
        return data;
    }

    public void setData(List<Destination> data) {
        this.data = data;
    }
}
