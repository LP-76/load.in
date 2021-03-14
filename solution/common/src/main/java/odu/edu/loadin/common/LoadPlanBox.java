package odu.edu.loadin.common;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Data")
public class LoadPlanBox extends Inventory
{
    private float xOffset,yOffset,zOffset;
    private int  loadNumber,stepNumber;


    public LoadPlanBox(){};

    public LoadPlanBox(int id, float length, float width, float height, float xOffset, float yOffset, float zOffset, float weight, int fragility, String description, int loadNumber, int stepNumber, int boxId)
    {
        this.id = id;

        this.length = length;
        this.width = width;
        this.height = height;

        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;

        this.weight = weight;
        this.fragility = fragility;

        this.description = description;

        this.loadNumber = loadNumber;
        this.stepNumber = stepNumber;
        this.boxID = boxId;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public float getzOffset() {
        return zOffset;
    }

    public void setzOffset(float zOffset) {
        this.zOffset = zOffset;
    }

    public int getLoadNumber() {
        return loadNumber;
    }

    public void setLoadNumber(int loadNumber) {
        this.loadNumber = loadNumber;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }
}
