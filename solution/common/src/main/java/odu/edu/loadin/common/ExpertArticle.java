package odu.edu.loadin.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
@XmlRootElement(name="Data")
public class ExpertArticle {

    public ExpertArticle(){

    }

    private String keyword;
    private String visualFile;
    private String ArticleTitle;
    private String ArticleContent;
    private Date createdAt;
    private Date updatedAt;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getVisualFile() {
        return visualFile;
    }

    public void setVisualFile(String visualFile) {
        this.visualFile = visualFile;
    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        ArticleTitle = articleTitle;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
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
