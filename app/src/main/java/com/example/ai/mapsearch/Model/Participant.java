package com.example.ai.mapsearch.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by A.I on 26/11/2017.
 */

public class Participant {

    @SerializedName("longitude")
    private Double participantLongitude;
    @SerializedName("latitude")
    private Double participantLatitude;
    @SerializedName("name")
    private String participantName;

    public Double getParticipantLongitude() {
        return participantLongitude;
    }

    public void setParticipantLongitude(Double participantLongitude) {
        this.participantLongitude = participantLongitude;
    }

    public Double getParticipantLatitude() {
        return participantLatitude;
    }

    public void setParticipantLatitude(Double participantLatitude) {
        this.participantLatitude = participantLatitude;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }
}
