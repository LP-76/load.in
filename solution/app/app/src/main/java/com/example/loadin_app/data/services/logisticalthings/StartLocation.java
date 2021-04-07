package com.example.loadin_app.data.services.logisticalthings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class StartLocation {

    @SerializedName("bbox")
    @Expose
    private List<Double> bbox = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("point")
    @Expose
    private Point_1 point;
    @SerializedName("address")
    @Expose
    private Address_1 address;
    @SerializedName("confidence")
    @Expose
    private String confidence;
    @SerializedName("entityType")
    @Expose
    private String entityType;
    @SerializedName("geocodePoints")
    @Expose
    private List<GeocodePoint_1> geocodePoints = null;
    @SerializedName("matchCodes")
    @Expose
    private List<String> matchCodes = null;

    public List<Double> getBbox() {
        return bbox;
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point_1 getPoint() {
        return point;
    }

    public void setPoint(Point_1 point) {
        this.point = point;
    }

    public Address_1 getAddress() {
        return address;
    }

    public void setAddress(Address_1 address) {
        this.address = address;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public List<GeocodePoint_1> getGeocodePoints() {
        return geocodePoints;
    }

    public void setGeocodePoints(List<GeocodePoint_1> geocodePoints) {
        this.geocodePoints = geocodePoints;
    }

    public List<String> getMatchCodes() {
        return matchCodes;
    }

    public void setMatchCodes(List<String> matchCodes) {
        this.matchCodes = matchCodes;
    }

}