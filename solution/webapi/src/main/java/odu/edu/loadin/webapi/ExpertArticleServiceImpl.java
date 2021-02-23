package odu.edu.loadin.webapi;

import odu.edu.loadin.common.ExpertArticle;
import odu.edu.loadin.common.ExpertArticleService;
import odu.edu.loadin.common.User;
import odu.edu.loadin.helpers.StatementHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpertArticleServiceImpl implements ExpertArticleService {

    @Override
    public ExpertArticle getExpertArticle(String keyword){

        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){ //this is called a try with resources and with java 1.8
            //this will auto-close the connection
            String tableToQuery = "SELECT * FROM EXPERT_TIP where KEYWORD = ";
            String keywordToSearch = keyword;
            String fullQuery = tableToQuery + keywordToSearch;

            PreparedStatement statement = conn.prepareStatement(fullQuery);

            //this is more of a transparent method.  person who is performing the query can decide how it gets mapped back to
            //individual objects
            ExpertArticle results = StatementHelper.getResults(statement, (ResultSet rs) -> {
                return mapStandardArticleResult(rs);
            }).stream().findFirst().orElse(null);
            return results;
        }
        catch (SQLException ex){
            //TODO: exception logging
            System.out.println(ex);
        }
        return null;
    }

    private ExpertArticle mapStandardArticleResult(ResultSet rs) throws SQLException {
        ExpertArticle r = new ExpertArticle();
        r.setKeyword(rs.getString("KEYWORD"));
        r.setArticleContent(rs.getString("ARTICLE_CONTENT"));
        r.setArticleTitle(rs.getString("ARTICLE_TITLE"));
        r.setCreatedAt(rs.getDate("CREATED_AT"));
        r.setUpdatedAt(rs.getDate("UPDATED_AT"));
        r.setVisualFile(rs.getString("IMAGE"));
        return r;
    }

}
