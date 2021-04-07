package com.example.loadin_app.data.services.logisticalthings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Resource {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("bbox")
    @Expose
    private List<Double> bbox = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("distanceUnit")
    @Expose
    private String distanceUnit;
    @SerializedName("durationUnit")
    @Expose
    private String durationUnit;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("routeLegs")
    @Expose
    private List<RouteLeg> routeLegs = null;
    @SerializedName("trafficCongestion")
    @Expose
    private String trafficCongestion;
    @SerializedName("trafficDataUsed")
    @Expose
    private String trafficDataUsed;
    @SerializedName("travelDistance")
    @Expose
    private Double travelDistance;
    @SerializedName("travelDuration")
    @Expose
    private Integer travelDuration;
    @SerializedName("travelDurationTraffic")
    @Expose
    private Integer travelDurationTraffic;
    @SerializedName("travelMode")
    @Expose
    private String travelMode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getBbox() {
        return bbox;
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<RouteLeg> getRouteLegs() {
        return routeLegs;
    }

    public void setRouteLegs(List<RouteLeg> routeLegs) {
        this.routeLegs = routeLegs;
    }

    public String getTrafficCongestion() {
        return trafficCongestion;
    }

    public void setTrafficCongestion(String trafficCongestion) {
        this.trafficCongestion = trafficCongestion;
    }

    public String getTrafficDataUsed() {
        return trafficDataUsed;
    }

    public void setTrafficDataUsed(String trafficDataUsed) {
        this.trafficDataUsed = trafficDataUsed;
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

    public Integer getTravelDurationTraffic() {
        return travelDurationTraffic;
    }

    public void setTravelDurationTraffic(Integer travelDurationTraffic) {
        this.travelDurationTraffic = travelDurationTraffic;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

}