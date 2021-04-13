package odu.edu.loadin.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name="LPB")
public class LoadPlanBox
{
    private float xOffset,yOffset,zOffset;
    private int  loadNumber,stepNumber;

    private Inventory box;
    private MovingTruck truck;

    public LoadPlanBox(){
        box = new Inventory();
        truck = new MovingTruck();
    };

    public LoadPlanBox(int id, float length, float width, float height, float xOffset, float yOffset, float zOffset, float weight, int fragility, String description, int loadNumber, int stepNumber, int boxId)
    {
        box = new Inventory();

        box.setId(id);

        box.setLength( length);
        box.setWidth(  width);
        box.setHeight(  height);

        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;

        box.setWeight( weight);
        box.setFragility(fragility);

        box.setDescription(description);

        this.loadNumber = loadNumber;
        this.stepNumber = stepNumber;
        box.setBoxID(boxId);
    }

    public MovingTruck getTruck() {
        return truck;
    }

    public void setTruck(MovingTruck truck) {
        this.truck = truck;
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

    public Inventory getBox() {
        return box;
    }

    public void setBox(Inventory box) {
        this.box = box;
    }
}
