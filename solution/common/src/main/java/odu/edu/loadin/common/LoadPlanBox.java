package odu.edu.loadin.common;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Data")
public class LoadPlanBox
{
    private float length,width,height,xOffset,yOffset,zOffset, weight, fragility;
    private int id, loadNumber,boxNumber;
    private String description;

    public LoadPlanBox(){};

    public LoadPlanBox(int id, float length, float width, float height, float xOffset, float yOffset, float zOffset, float weight, float fragility, String description, int loadNumber, int boxNumber)
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
        this.boxNumber = boxNumber;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getFragility() {
        return fragility;
    }

    public void setFragility(float fragility) {
        this.fragility = fragility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoadNumber() {
        return loadNumber;
    }

    public void setLoadNumber(int loadNumber) {
        this.loadNumber = loadNumber;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
