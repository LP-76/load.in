package com.example.loadin_app.data.services.logisticalthings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RouteLeg {

    @SerializedName("actualEnd")
    @Expose
    private ActualEnd actualEnd;
    @SerializedName("actualStart")
    @Expose
    private ActualStart actualStart;
    @SerializedName("alternateVias")
    @Expose
    private List<Object> alternateVias = null;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("endLocation")
    @Expose
    private EndLocation endLocation;
    @SerializedName("itineraryItems")
    @Expose
    private List<ItineraryItem> itineraryItems = null;
    @SerializedName("routeRegion")
    @Expose
    private String routeRegion;
    @SerializedName("routeSubLegs")
    @Expose
    private List<RouteSubLeg> routeSubLegs = null;
    @SerializedName("startLocation")
    @Expose
    private StartLocation startLocation;
    @SerializedName("travelDistance")
    @Expose
    private Double travelDistance;
    @SerializedName("travelDuration")
    @Expose
    private Integer travelDuration;
    @SerializedName("travelMode")
    @Expose
    private String travelMode;

    public ActualEnd getActualEnd() {
        return actualEnd;
    }

    public void setActualEnd(ActualEnd actualEnd) {
        this.actualEnd = actualEnd;
    }

    public ActualStart getActualStart() {
        return actualStart;
    }

    public void setActualStart(ActualStart actualStart) {
        this.actualStart = actualStart;
    }

    public List<Object> getAlternateVias() {
        return alternateVias;
    }

    public void setAlternateVias(List<Object> alternateVias) {
        this.alternateVias = alternateVias;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public List<ItineraryItem> getItineraryItems() {
        return itineraryItems;
    }

    public void setItineraryItems(List<ItineraryItem> itineraryItems) {
        this.itineraryItems = itineraryItems;
    }

    public String getRouteRegion() {
        return routeRegion;
    }

    public void setRouteRegion(String routeRegion) {
        this.routeRegion = routeRegion;
    }

    public List<RouteSubLeg> getRouteSubLegs() {
        return routeSubLegs;
    }

    public void setRouteSubLegs(List<RouteSubLeg> routeSubLegs) {
        this.routeSubLegs = routeSubLegs;
    }

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
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

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

}