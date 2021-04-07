package com.example.loadin_app.data.services.logisticalthings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ItineraryItem {

    @SerializedName("compassDirection")
    @Expose
    private String compassDirection;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;
    @SerializedName("exit")
    @Expose
    private String exit;
    @SerializedName("iconType")
    @Expose
    private String iconType;
    @SerializedName("instruction")
    @Expose
    private Instruction instruction;
    @SerializedName("isRealTimeTransit")
    @Expose
    private Boolean isRealTimeTransit;
    @SerializedName("maneuverPoint")
    @Expose
    private ManeuverPoint maneuverPoint;
    @SerializedName("realTimeTransitDelay")
    @Expose
    private Integer realTimeTransitDelay;
    @SerializedName("sideOfStreet")
    @Expose
    private String sideOfStreet;
    @SerializedName("tollZone")
    @Expose
    private String tollZone;
    @SerializedName("towardsRoadName")
    @Expose
    private String towardsRoadName;
    @SerializedName("transitTerminus")
    @Expose
    private String transitTerminus;
    @SerializedName("travelDistance")
    @Expose
    private Float travelDistance;
    @SerializedName("travelDuration")
    @Expose
    private Float travelDuration;
    @SerializedName("travelMode")
    @Expose
    private String travelMode;
    @SerializedName("hints")
    @Expose
    private List<Hint> hints = null;
    @SerializedName("signs")
    @Expose
    private List<String> signs = null;
    @SerializedName("warnings")
    @Expose
    private List<Warning> warnings = null;

    public String getCompassDirection() {
        return compassDirection;
    }

    public void setCompassDirection(String compassDirection) {
        this.compassDirection = compassDirection;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Boolean getIsRealTimeTransit() {
        return isRealTimeTransit;
    }

    public void setIsRealTimeTransit(Boolean isRealTimeTransit) {
        this.isRealTimeTransit = isRealTimeTransit;
    }

    public ManeuverPoint getManeuverPoint() {
        return maneuverPoint;
    }

    public void setManeuverPoint(ManeuverPoint maneuverPoint) {
        this.maneuverPoint = maneuverPoint;
    }

    public Integer getRealTimeTransitDelay() {
        return realTimeTransitDelay;
    }

    public void setRealTimeTransitDelay(Integer realTimeTransitDelay) {
        this.realTimeTransitDelay = realTimeTransitDelay;
    }

    public String getSideOfStreet() {
        return sideOfStreet;
    }

    public void setSideOfStreet(String sideOfStreet) {
        this.sideOfStreet = sideOfStreet;
    }

    public String getTollZone() {
        return tollZone;
    }

    public void setTollZone(String tollZone) {
        this.tollZone = tollZone;
    }

    public String getTowardsRoadName() {
        return towardsRoadName;
    }

    public void setTowardsRoadName(String towardsRoadName) {
        this.towardsRoadName = towardsRoadName;
    }

    public String getTransitTerminus() {
        return transitTerminus;
    }

    public void setTransitTerminus(String transitTerminus) {
        this.transitTerminus = transitTerminus;
    }

    public Float getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(Float travelDistance) {
        this.travelDistance = travelDistance;
    }

    public Float getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(Float travelDuration) {
        this.travelDuration = travelDuration;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }

    public List<String> getSigns() {
        return signs;
    }

    public void setSigns(List<String> signs) {
        this.signs = signs;
    }

    public List<Warning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

}