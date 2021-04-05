package odu.edu.loadin.common;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
@XmlRootElement(name="Data")
public class Feedback {
    public Feedback(){

    }

    protected int id;
    protected int userID;
    protected String accountCreationComment;
    protected int accountCreationRating;
    protected String itemInputComment;
    protected int itemInputRating;
    protected String loadPlanComment;
    protected int loadPlanRating;
    protected String expertTipsComment;
    protected int expertTipsRating;
    protected String overallExperienceComment;
    protected int overallExperienceRating;
    protected Date createdAt;
    protected Date updatedAt;

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

    public String getAccountCreationComment() {
        return accountCreationComment;
    }

    public void setAccountCreationComment(String accountCreationComment) {
        this.accountCreationComment = accountCreationComment;
    }

    public int getAccountCreationRating() {
        return accountCreationRating;
    }

    public void setAccountCreationRating(int accountCreationRating) {
        this.accountCreationRating = accountCreationRating;
    }

    public String getItemInputComment() {
        return itemInputComment;
    }

    public void setItemInputComment(String itemInputComment) {
        this.itemInputComment = itemInputComment;
    }

    public int getItemInputRating() {
        return itemInputRating;
    }

    public void setItemInputRating(int itemInputRating) {
        this.itemInputRating = itemInputRating;
    }

    public String getLoadPlanComment() {
        return loadPlanComment;
    }

    public void setLoadPlanComment(String loadPlanComment) {
        this.loadPlanComment = loadPlanComment;
    }

    public int getLoadPlanRating() {
        return loadPlanRating;
    }

    public void setLoadPlanRating(int loadPlanRating) {
        this.loadPlanRating = loadPlanRating;
    }

    public String getExpertTipsComment() {
        return expertTipsComment;
    }

    public void setExpertTipsComment(String expertTipsComment) {
        this.expertTipsComment = expertTipsComment;
    }

    public int getExpertTipsRating() {
        return expertTipsRating;
    }

    public void setExpertTipsRating(int expertTipsRating) {
        this.expertTipsRating = expertTipsRating;
    }

    public String getOverallExperienceComment() {
        return overallExperienceComment;
    }

    public void setOverallExperienceComment(String overallExperienceComment) {
        this.overallExperienceComment = overallExperienceComment;
    }

    public int getOverallExperienceRating() {
        return overallExperienceRating;
    }

    public void setOverallExperienceRating(int overallExperienceRating) {
        this.overallExperienceRating = overallExperienceRating;
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
