package odu.edu.loadin.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
@XmlRootElement(name="Data")
/**
 * This class represents a pre-configured box size from the database
 */
public class BoxSize  {
    private int id;
    private String description;
    private String dimensions;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Accessor for id
     * @return id for the record
     */
    public int getId() {
        return id;
    }

    /**
     * Mutator for the id
     * @param id the id for the record
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Accessor for the description
     * @return the string description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
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
