package odu.edu.loadin.webapi;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.InventoryService;
import odu.edu.loadin.helpers.StatementHelper;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryServiceImpl implements InventoryService {


    @Override
    public ArrayList<Inventory> getInventory(int loginID) throws SQLException {
        //we get a connection here
        System.out.println("--invoking getInventory");
        try (Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()) { //this is called a try with resources and with java 1.8
            //this will auto-close the connection
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER_INVENTORY_ITEM where USER_ID = ?");
            statement.setInt(1, loginID);

            //this is more of a transparent method.  person who is performing the query can decide how it gets mapped back to
            //individual objects
            ArrayList<Inventory> results = StatementHelper.getResults(statement, (ResultSet rs) -> {
                Inventory s = new Inventory();
                s.setId(rs.getInt("ID"));
                s.setUserID(rs.getInt("USER_ID"));
                s.setBoxID(rs.getInt("BOX_ID"));
                s.setWidth(rs.getFloat("BOX_WIDTH"));
                s.setHeight(rs.getFloat("BOX_HEIGHT"));
                s.setLength(rs.getFloat("BOX_LENGTH"));
                s.setDescription(rs.getString("ITEM_DESCRIPTION"));
                s.setFragility(rs.getInt("FRAGILITY"));
                s.setWeight(rs.getDouble("WEIGHT"));
                s.setCreatedAt(rs.getDate("CREATED_AT"));
                s.setUpdatedAt(rs.getDate("UPDATED_AT"));
                return s;
            });
            return results;
        } catch (SQLException ex) {
            //TODO: exception logging
            System.out.println(ex);
        }

        return new ArrayList<Inventory>();
    }


    @Override
    public Response addInventory(Inventory inventory) {

        System.out.println("----invoking addInventory");

        try (Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()) {
            addInventoryItem(conn, inventory);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return Response.ok(inventory).build();

    }

    @Override
    public ArrayList<Inventory> addBulkInventory(ArrayList<Inventory> items) {
        System.out.println("----invoking addBulkInventory");

        try (Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()) {
            for(Inventory i: items)
                addInventoryItem(conn, i);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return items;
    }


    private void addInventoryItem(Connection conn, Inventory inventory) throws SQLException {
        Integer lastId = StatementHelper.getResults(conn.prepareStatement("SELECT ID FROM USER_INVENTORY_ITEM ORDER BY ID DESC LIMIT 1"),
                (ResultSet rs) -> {
                    return rs.getInt("ID");
                }).stream().findFirst().orElse(0);
        inventory.setId(lastId + 1);

        PreparedStatement statement = conn.prepareStatement("SELECT BOX_ID FROM USER_INVENTORY_ITEM where USER_ID = ? ORDER BY BOX_ID DESC LIMIT 1");
        statement.setString(1, String.valueOf(inventory.getUserID()));
        Integer lastBoxId = StatementHelper.getResults(statement,
                (ResultSet rs) -> {
                    return rs.getInt("BOX_ID");
                }).stream().findFirst().orElse(0);

        //set the new id here
        inventory.setBoxID(lastBoxId + 1);
        //inventory.setUserID(1); //TODO needs to be mapped to user's ID
            /*
            TODO BOX_ID defaults to null in database; need to set a check so that the first box in
            a user's inventory is set to 1 if the select comes back null
            */
        String query = "INSERT INTO USER_INVENTORY_ITEM ( ID ,USER_ID, BOX_ID, ITEM_DESCRIPTION, BOX_WIDTH, BOX_HEIGHT, BOX_LENGTH, FRAGILITY, WEIGHT, IMAGE, CREATED_AT, UPDATED_AT)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NULL ,NOW(), NOW() )";

        PreparedStatement insertStatement = conn.prepareStatement(query);
        insertStatement.setInt(1, inventory.getId());
        insertStatement.setInt(2, inventory.getUserID());
        insertStatement.setInt(3, inventory.getBoxID());
        String description = inventory.getDescription();

        description = description != null ? description.length() > 30 ? description.substring(0,30) : description : "";

        insertStatement.setString(4, description);  //TODO: FIX!!!
        insertStatement.setFloat(5, inventory.getWidth());
        insertStatement.setFloat(6, inventory.getHeight());
        insertStatement.setFloat(7, inventory.getLength());
        insertStatement.setInt(8, inventory.getFragility());
        insertStatement.setDouble(9, inventory.getWeight());
        System.out.println(insertStatement);
        insertStatement.executeUpdate();
    }


    public Response editInventory(Inventory inventory) {

        System.out.println("----invoking editInventory");

        try (Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()) {
           /* PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER_INVENTORY_ITEM where ID = ? AND BOX_ID = ?");
            statement.setInt(1,loginID);
            statement.setInt(2,boxID);
            Integer lastBoxId = StatementHelper.getResults(statement,
                    (ResultSet rs) -> {  return rs.getInt("BOX_ID"); }).stream().findFirst().orElse(0);

            //set the new id here
            inventory.setBoxID(lastBoxId + 1);*/
            //inventory.setUserID(1);
            String query = "UPDATE USER_INVENTORY_ITEM SET USER_ID=?, BOX_ID=?, ITEM_DESCRIPTION=?, BOX_WIDTH=?, BOX_HEIGHT=?," +
                    "BOX_LENGTH=?, FRAGILITY=?, WEIGHT=?, IMAGE=NULL,UPDATED_AT=NOW() WHERE ID =?;";

            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.setInt(1, inventory.getUserID());
            insertStatement.setInt(2, inventory.getBoxID());
            insertStatement.setString(3, inventory.getDescription());
            insertStatement.setFloat(4, inventory.getWidth());
            insertStatement.setFloat(5, inventory.getHeight());
            insertStatement.setFloat(6, inventory.getLength());
            insertStatement.setInt(7, inventory.getFragility());
            insertStatement.setDouble(8, inventory.getWeight());
            insertStatement.setInt(9, inventory.getId());
            System.out.println(insertStatement);
            insertStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return Response.ok(inventory).build();

    }

    public void deleteItem(int ID) {

        System.out.println("----invoking deleteItem");

        try (Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()) {
            String query = "DELETE FROM USER_INVENTORY_ITEM WHERE ID=?;";
            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.setInt(1, ID);
            System.out.println(insertStatement);
            insertStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
