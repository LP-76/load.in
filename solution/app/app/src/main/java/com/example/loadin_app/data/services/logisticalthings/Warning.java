package com.example.loadin_app.data.services.logisticalthings;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Warning {

    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("warningType")
    @Expose
    private String warningType;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

}