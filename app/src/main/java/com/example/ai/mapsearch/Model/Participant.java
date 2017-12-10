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

    private String distance, eta, time;

    public Participant() {
    }

    public Participant(String participantName, String distance, String eta, String time) {
        this.participantName = participantName;
        this.distance = distance;
        this.eta = eta;
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
