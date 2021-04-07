package com.example.loadin_app.data.services.logisticalthings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Shield {

    @SerializedName("labels")
    @Expose
    private List<String> labels = null;
    @SerializedName("roadShieldType")
    @Expose
    private Integer roadShieldType;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Integer getRoadShieldType() {
        return roadShieldType;
    }

    public void setRoadShieldType(Integer roadShieldType) {
        this.roadShieldType = roadShieldType;
    }

}