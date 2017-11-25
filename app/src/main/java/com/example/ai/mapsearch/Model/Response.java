package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by A.I on 25/11/2017.
 */

public class Response {

    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private List<Destination> data;

    public List<Destination> getData() {
        return data;
    }

    public void setData(List<Destination> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
