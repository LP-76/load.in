package com.example.loadin_app.data.services.logisticalthings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Detail {

    @SerializedName("compassDegrees")
    @Expose
    private Integer compassDegrees;
    @SerializedName("endPathIndices")
    @Expose
    private List<Integer> endPathIndices = null;
    @SerializedName("locationCodes")
    @Expose
    private List<String> locationCodes = null;
    @SerializedName("maneuverType")
    @Expose
    private String maneuverType;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("names")
    @Expose
    private List<String> names = null;
    @SerializedName("roadShieldRequestParameters")
    @Expose
    private RoadShieldRequestParameters roadShieldRequestParameters;
    @SerializedName("roadType")
    @Expose
    private String roadType;
    @SerializedName("startPathIndices")
    @Expose
    private List<Integer> startPathIndices = null;

    public Integer getCompassDegrees() {
        return compassDegrees;
    }

    public void setCompassDegrees(Integer compassDegrees) {
        this.compassDegrees = compassDegrees;
    }

    public List<Integer> getEndPathIndices() {
        return endPathIndices;
    }

    public void setEndPathIndices(List<Integer> endPathIndices) {
        this.endPathIndices = endPathIndices;
    }

    public List<String> getLocationCodes() {
        return locationCodes;
    }

    public void setLocationCodes(List<String> locationCodes) {
        this.locationCodes = locationCodes;
    }

    public String getManeuverType() {
        return maneuverType;
    }

    public void setManeuverType(String maneuverType) {
        this.maneuverType = maneuverType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public RoadShieldRequestParameters getRoadShieldRequestParameters() {
        return roadShieldRequestParameters;
    }

    public void setRoadShieldRequestParameters(RoadShieldRequestParameters roadShieldRequestParameters) {
        this.roadShieldRequestParameters = roadShieldRequestParameters;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public List<Integer> getStartPathIndices() {
        return startPathIndices;
    }

    public void setStartPathIndices(List<Integer> startPathIndices) {
        this.startPathIndices = startPathIndices;
    }

}