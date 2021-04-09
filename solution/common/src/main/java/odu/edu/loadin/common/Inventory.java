package odu.edu.loadin.common;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
@XmlRootElement(name="Data")
public class Inventory {
    public static final String AT_SOURCE = "At Source";
    public static final String ON_TRUCK = "On Truck";
    public static final String AT_DESTINATION = "At Destination";



    public Inventory(){
        status = AT_SOURCE; //by default always at the source on creation
    }
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
    protected String status;
    protected String itemList;
    protected String room;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }
}
