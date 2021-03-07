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
    public ArrayList<Inventory> getInventory() throws SQLException {
        //we get a connection here

        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){ //this is called a try with resources and with java 1.8
            //this will auto-close the connection
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER_INVENTORY_ITEM");

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
        }
        catch (SQLException ex){
            //TODO: exception logging
            System.out.println(ex);
        }

        return new ArrayList<Inventory>();
    }


    @Override
    public Response addInventory(Inventory inventory) {

        System.out.println("----invoking addInventory");

        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){
            Integer lastId = StatementHelper.getResults(conn.prepareStatement("SELECT ID FROM USER_INVENTORY_ITEM ORDER BY ID DESC LIMIT 1"),
                    (ResultSet rs) -> {  return rs.getInt("ID"); }).stream().findFirst().orElse(0);
            inventory.setId(lastId + 1);

            PreparedStatement statement = conn.prepareStatement("SELECT BOX_ID FROM USER_INVENTORY_ITEM where ID = ? ORDER BY ID DESC LIMIT 1");
            statement.setString(1, String.valueOf(inventory.getId()));
            Integer lastBoxId = StatementHelper.getResults(statement,
                    (ResultSet rs) -> {  return rs.getInt("BOX_ID"); }).stream().findFirst().orElse(0);

             //set the new id here
            inventory.setBoxID(lastBoxId + 1);
            //inventory.setUserID(1); //TODO needs to be mapped to user's ID
            /*
            TODO BOX_ID defaults to null in database; need to set a check so that the first box in
            a user's inventory is set to 1 if the select comes back null
            */
            String query = "INSERT INTO USER_INVENTORY_ITEM ( ID ,USER_ID, BOX_ID, ITEM_DESCRIPTION, BOX_WIDTH, BOX_HEIGHT, BOX_LENGTH, FRAGILITY, WEIGHT, IMAGE, CREATED_AT, UPDATED_AT)"
                    +" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NULL ,NOW(), NOW() )";

            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.setInt(1, inventory.getId());
            insertStatement.setInt(2, inventory.getUserID());
            insertStatement.setInt(3,inventory.getBoxID());
            insertStatement.setString(4, inventory.getDescription());
            insertStatement.setFloat(5,inventory.getWidth());
            insertStatement.setFloat(6,inventory.getHeight());
            insertStatement.setFloat(7,inventory.getLength());
            insertStatement.setInt(8, inventory.getFragility());
            insertStatement.setDouble(9, inventory.getWeight());
            System.out.println(insertStatement);
            insertStatement.executeUpdate();

        }
        catch (SQLException ex){

        }

        return Response.ok(inventory).build();

    }
}
