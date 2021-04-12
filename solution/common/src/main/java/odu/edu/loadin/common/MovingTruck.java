package odu.edu.loadin.common;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Data")
public class MovingTruck {
    private int id;
    private String companyName;
    private String truckName;
    float widthInInches, heightInInches, lengthInInches; //interior dimension
    float milesPerGallon;
    float baseRentalCost;
    float costPerMile;

    public MovingTruck(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public float getWidthInInches() {
        return widthInInches;
    }

    public void setWidthInInches(float widthInInches) {
        this.widthInInches = widthInInches;
    }

    public float getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(float heightInInches) {
        this.heightInInches = heightInInches;
    }

    public float getLengthInInches() {
        return lengthInInches;
    }

    public void setLengthInInches(float lengthInInches) {
        this.lengthInInches = lengthInInches;
    }

    public float getMilesPerGallon() {
        return milesPerGallon;
    }

    public void setMilesPerGallon(float milesPerGallon) {
        this.milesPerGallon = milesPerGallon;
    }

    public float getBaseRentalCost() {
        return baseRentalCost;
    }

    public void setBaseRentalCost(float baseRentalCost) {
        this.baseRentalCost = baseRentalCost;
    }

    public float getCostPerMile() {
        return costPerMile;
    }

    public void setCostPerMile(float costPerMile) {
        this.costPerMile = costPerMile;
    }


}
