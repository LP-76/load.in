package com.example.loadin_app.data.services.logisticalthings;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RouteSubLeg {

    @SerializedName("endWaypoint")
    @Expose
    private EndWaypoint endWaypoint;
    @SerializedName("startWaypoint")
    @Expose
    private StartWaypoint startWaypoint;
    @SerializedName("travelDistance")
    @Expose
    private Double travelDistance;
    @SerializedName("travelDuration")
    @Expose
    private Integer travelDuration;

    public EndWaypoint getEndWaypoint() {
        return endWaypoint;
    }

    public void setEndWaypoint(EndWaypoint endWaypoint) {
        this.endWaypoint = endWaypoint;
    }

    public StartWaypoint getStartWaypoint() {
        return startWaypoint;
    }

    public void setStartWaypoint(StartWaypoint startWaypoint) {
        this.startWaypoint = startWaypoint;
    }

    public Double getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(Double travelDistance) {
        this.travelDistance = travelDistance;
    }

    public Integer getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(Integer travelDuration) {
        this.travelDuration = travelDuration;
    }

}