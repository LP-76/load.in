package odu.edu.loadin.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name="Data")
public class LoadPlanBox
{
    protected int id;
    protected int userID;
    protected int boxID;
    protected String description;
    protected float width;
    protected float height;
    protected float length;
    protected int fragility;
    protected double weight;
    protected Date createdAt;
    protected Date updatedAt;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getFragility() {
        return fragility;
    }

    public void setFragility(int fragility) {
        this.fragility = fragility;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
