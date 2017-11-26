package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by A.I on 26/11/2017.
 */

public class ResponseParticipant extends Response {
    @SerializedName("data")
    private List<Participant> data;

    public List<Participant> getData() {
        return data;
    }

    public void setData(List<Participant> data) {
        this.data = data;
    }
}
