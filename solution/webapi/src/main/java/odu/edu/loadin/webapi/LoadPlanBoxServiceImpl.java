package odu.edu.loadin.webapi;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.LoadPlanBox;
import odu.edu.loadin.common.LoadPlanBoxService;

import java.sql.SQLException;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import odu.edu.loadin.common.MovingTruck;
import odu.edu.loadin.helpers.*;

public class LoadPlanBoxServiceImpl implements LoadPlanBoxService
{
    @Override
    public ArrayList<LoadPlanBox> getLoadPlan(int userId) throws SQLException
    {
        System.out.println("--invoking getLoadPlan");
        try (Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()) { //this is called a try with resources and with java 1.8
            //this will auto-close the connection
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER_INVENTORY_ITEM as i\n" +
                    "                    JOIN LOAD_PLAN_BOX LPB on i.ID = LPB.ID\n" +
                    "                    JOIN TRUCK as t on t.ID = LPB.TRUCK_ID\n" +
                    "                    WHERE USER_ID = ?");
            statement.setInt(1, userId);

            //this is more of a transparent method.  person who is performing the query can decide how it gets mapped back to
            //individual objects
            ArrayList<LoadPlanBox> results = StatementHelper.getResults(statement, (ResultSet rs) -> {
                LoadPlanBox s = new LoadPlanBox();
                Inventory b = s.getBox();
                MovingTruck truck = s.getTruck();
                b.setId(rs.getInt("ID"));
                b.setUserID(rs.getInt("USER_ID"));
                b.setBoxID(rs.getInt("BOX_ID"));
                b.setWidth(rs.getFloat("BOX_WIDTH"));
                b.setHeight(rs.getFloat("BOX_HEIGHT"));
                b.setLength(rs.getFloat("BOX_LENGTH"));
                b.setDescription(rs.getString("ITEM_DESCRIPTION"));
                b.setFragility(rs.getInt("FRAGILITY"));
                b.setWeight(rs.getDouble("WEIGHT"));
                b.setCreatedAt(rs.getDate("CREATED_AT"));
                b.setUpdatedAt(rs.getDate("UPDATED_AT"));
                b.setStatus(rs.getString("STATUS"));
                b.setItemList(rs.getString("ITEM_LIST"));
                b.setRoom(rs.getString("ROOM"));
                //additional properties
                s.setxOffset(rs.getFloat("X_OFFSET"));
                s.setyOffset(rs.getFloat("Y_OFFSET"));
                s.setzOffset(rs.getFloat("Z_OFFSET"));

                s.setStepNumber(rs.getInt("BOX_STEP"));
                s.setLoadNumber(rs.getInt("LOAD_NUMBER"));

                truck.setWidthInInches(rs.getFloat("TRUCK_WIDTH"));
                truck.setHeightInInches(rs.getFloat("TRUCK_HEIGHT"));
                truck.setLengthInInches(rs.getFloat("TRUCK_LENGTH"));


                return s;
            });
            return results;
        } catch (SQLException ex) {
            //TODO: exception logging
            System.out.println(ex);
        }

        return new ArrayList<LoadPlanBox>();


    }

    @Override
    public ArrayList<LoadPlanBox> addLoadPlan(int userId, ArrayList<LoadPlanBox> boxes) throws SQLException
    {

        System.out.println("----invoking addLoadPlan");

        try (Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()) {
//
            clearLoadPlan(conn, userId);
            saveLoadPlan(conn, boxes);
            resetStatusForInventoryItemsFromCurrentUserLoadPlan(conn, userId);

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return boxes;
    }

    private void clearLoadPlan(Connection conn, int userId) throws SQLException {
        String query = "\n" +
                "DELETE LPB FROM\n" +
                "              USER_INVENTORY_ITEM i\n" +
                "    JOIN LOAD_PLAN_BOX LPB on i.ID = LPB.ID\n" +
                "    WHERE USER_ID = ?";
        PreparedStatement deleteStatement = conn.prepareStatement(query);
        deleteStatement.setInt(1, userId);
        deleteStatement.execute();
    }
    private void saveLoadPlan(Connection con, ArrayList<LoadPlanBox> boxes) throws SQLException {
        for(LoadPlanBox box: boxes){
            saveLoadPlanBox(con, box);
        }
    }
    private void saveLoadPlanBox(Connection conn, LoadPlanBox box) throws SQLException {
        String query = "INSERT INTO LOAD_PLAN_BOX(ID, X_OFFSET, Y_OFFSET, Z_OFFSET, BOX_STEP, LOAD_NUMBER, TRUCK_ID)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement insertStatement = conn.prepareStatement(query);
        insertStatement.setInt(1, box.getBox().getId());
        insertStatement.setFloat(2, box.getxOffset());
        insertStatement.setFloat(3, box.getyOffset());
        insertStatement.setFloat(4, box.getzOffset());
        insertStatement.setInt(5, box.getStepNumber());
        insertStatement.setInt(6, box.getLoadNumber());
        insertStatement.setInt(7, box.getTruck().getId());
        System.out.println(insertStatement);
        insertStatement.executeUpdate();
    }

    private void resetStatusForInventoryItemsFromCurrentUserLoadPlan(Connection conn, int userId) throws SQLException {
        String query = "UPDATE USER_INVENTORY_ITEM\n" +
                "JOIN LOAD_PLAN_BOX LPB on USER_INVENTORY_ITEM.ID = LPB.ID\n" +
                "SET STATUS = 'At Source'\n" +
                "where USER_ID = ?";

        PreparedStatement updateStatement = conn.prepareStatement(query);
        updateStatement.setInt(1, userId);
        System.out.println(updateStatement);
        updateStatement.executeUpdate();
    }

}
