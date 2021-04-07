package com.example.loadin_app.data.services.logisticalthings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RoadShieldRequestParameters {

    @SerializedName("bucket")
    @Expose
    private Integer bucket;
    @SerializedName("shields")
    @Expose
    private List<Shield> shields = null;

    public Integer getBucket() {
        return bucket;
    }

    public void setBucket(Integer bucket) {
        this.bucket = bucket;
    }

    public List<Shield> getShields() {
        return shields;
    }

    public void setShields(List<Shield> shields) {
        this.shields = shields;
    }

}