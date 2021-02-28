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
                s.setMovePlanId(rs.getInt("MOVE_PLAN_ID"));
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

            inventory.setId(lastId + 1);  //set the new id here
            inventory.setMovePlanId(1); //TODO needs to be mapped to user's ID
            String query = "INSERT INTO USER_INVENTORY_ITEM ( ID ,MOVE_PLAN_ID, ITEM_DESCRIPTION, FRAGILITY, WEIGHT, IMAGE, CREATED_AT, UPDATED_AT)"
                    +" VALUES (?, ?, ?, ?, ?, NULL ,NOW(), NOW() )";

            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.setInt(1, inventory.getId());
            insertStatement.setInt(2, inventory.getMovePlanId());
            insertStatement.setString(3, inventory.getDescription());
            insertStatement.setInt(4, inventory.getFragility());
            insertStatement.setDouble(5, inventory.getWeight());
            System.out.println(insertStatement);
            insertStatement.executeUpdate();

        }
        catch (SQLException ex){

        }

        return Response.ok(inventory).build();

    }
}
