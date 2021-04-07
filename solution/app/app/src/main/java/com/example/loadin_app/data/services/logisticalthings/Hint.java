package com.example.loadin_app.data.services.logisticalthings;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Hint {

    @SerializedName("hintType")
    @Expose
    private String hintType;
    @SerializedName("text")
    @Expose
    private String text;

    public String getHintType() {
        return hintType;
    }

    public void setHintType(String hintType) {
        this.hintType = hintType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}