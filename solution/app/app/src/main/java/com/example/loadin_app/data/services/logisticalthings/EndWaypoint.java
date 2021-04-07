package com.example.loadin_app.data.services.logisticalthings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class EndWaypoint {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("isVia")
    @Expose
    private Boolean isVia;
    @SerializedName("locationIdentifier")
    @Expose
    private String locationIdentifier;
    @SerializedName("routePathIndex")
    @Expose
    private Integer routePathIndex;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsVia() {
        return isVia;
    }

    public void setIsVia(Boolean isVia) {
        this.isVia = isVia;
    }

    public String getLocationIdentifier() {
        return locationIdentifier;
    }

    public void setLocationIdentifier(String locationIdentifier) {
        this.locationIdentifier = locationIdentifier;
    }

    public Integer getRoutePathIndex() {
        return routePathIndex;
    }

    public void setRoutePathIndex(Integer routePathIndex) {
        this.routePathIndex = routePathIndex;
    }

}