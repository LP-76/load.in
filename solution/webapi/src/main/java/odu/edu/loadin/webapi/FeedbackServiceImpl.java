package odu.edu.loadin.webapi;

import odu.edu.loadin.common.Feedback;
import odu.edu.loadin.common.FeedbackService;
import odu.edu.loadin.common.User;
import odu.edu.loadin.helpers.StatementHelper;
import odu.edu.loadin.webapi.DatabaseConnectionProvider;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;

public class FeedbackServiceImpl implements FeedbackService{

    @Override
    public ArrayList<Feedback> getFeedback(int userID){
        System.out.println("--invoking getFeedback");

        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER_FEEDBACK where USER_ID = ?");
            statement.setInt(1, userID);
            ArrayList<Feedback> results = StatementHelper.getResults(statement, (ResultSet rs) -> {
                Feedback s = new Feedback();
                s.setId(rs.getInt("ID"));
                s.setUserID(rs.getInt("USER_ID"));
                s.setAccountCreationComment(rs.getString("ACCOUNT_CREATION_COMMENT"));
                s.setAccountCreationRating(rs.getInt("ACCOUNT_CREATION_RATING"));
                s.setItemInputComment(rs.getString("ITEM_INPUT_COMMENT"));
                s.setItemInputRating(rs.getInt("ITEM_INPUT_RATING"));
                s.setLoadPlanComment(rs.getString("LOAD_PLAN_COMMENT"));
                s.setLoadPlanRating(rs.getInt("LOAD_PLAN_RATING"));
                s.setExpertTipsComment(rs.getString("EXPERT_TIPS_COMMENT"));
                s.setExpertTipsRating(rs.getInt("EXPERT_TIPS_RATING"));
                s.setOverallExperienceComment(rs.getString("OVERALL_EXPERIENCE_COMMENT"));
                s.setOverallExperienceRating(rs.getInt("OVERALL_EXPERIENCE_RATING"));
                s.setCreatedAt(rs.getDate("CREATED_AT"));
                s.setUpdatedAt(rs.getDate("UPDATED_AT"));
                return s;
            });
            return results;
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        return new ArrayList<Feedback>();
    }

    public Response addFeedback(Feedback feedback){
        System.out.println("----invoking addFeedback");

        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){

            String query = "INSERT INTO USER_FEEDBACK( USER_ID, ACCOUNT_CREATION_COMMENT, ACCOUNT_CREATION_RATING, " +
                    "ITEM_INPUT_COMMENT, ITEM_INPUT_RATING, LOAD_PLAN_COMMENT, LOAD_PLAN_RATING, EXPERT_TIPS_COMMENT," +
                    "EXPERT_TIPS_RATING, OVERALL_EXPERIENCE_COMMENT, OVERALL_EXPERIENCE_RATING, CREATED_AT, UPDATED_AT)"
                    +" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW() )";

            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.setInt(1, feedback.getUserID());
            insertStatement.setString(2,feedback.getAccountCreationComment());
            insertStatement.setInt(3,feedback.getAccountCreationRating());
            insertStatement.setString(4,feedback.getItemInputComment());
            insertStatement.setInt(5,feedback.getItemInputRating());
            insertStatement.setString(6,feedback.getLoadPlanComment());
            insertStatement.setInt(7,feedback.getLoadPlanRating());
            insertStatement.setString(8,feedback.getExpertTipsComment());
            insertStatement.setInt(9,feedback.getExpertTipsRating());
            insertStatement.setString(10,feedback.getOverallExperienceComment());
            insertStatement.setInt(11,feedback.getOverallExperienceRating());
            System.out.println(insertStatement);
            insertStatement.executeUpdate();

        }
        catch (SQLException ex){

        }

        return Response.ok(feedback).build();

    }
}
