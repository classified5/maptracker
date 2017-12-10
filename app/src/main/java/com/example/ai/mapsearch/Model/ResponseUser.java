package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by A.I on 10/12/2017.
 */

public class ResponseUser extends Response{

    @SerializedName("data")
    private List<User> data;



    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
