package com.example.loadin_app.data.services.logisticalthings;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Instruction {

    @SerializedName("formattedText")
    @Expose
    private Object formattedText;
    @SerializedName("maneuverType")
    @Expose
    private String maneuverType;
    @SerializedName("text")
    @Expose
    private String text;

    public Object getFormattedText() {
        return formattedText;
    }

    public void setFormattedText(Object formattedText) {
        this.formattedText = formattedText;
    }

    public String getManeuverType() {
        return maneuverType;
    }

    public void setManeuverType(String maneuverType) {
        this.maneuverType = maneuverType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}