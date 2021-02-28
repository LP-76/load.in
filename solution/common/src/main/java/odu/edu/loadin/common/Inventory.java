package odu.edu.loadin.common;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
@XmlRootElement(name="Data")
public class Inventory {
    public Inventory(){

    }
    private int id;
    private int movePlanId;
    private String description;
    private int fragility;
    private double weight;
    private Date createdAt;
    private Date updatedAt;

    public int getMovePlanId() {
        return movePlanId;
    }

    public void setMovePlanId(int movePlanId) {
        this.movePlanId = movePlanId;
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
}
