/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package odu.edu.loadin.webapi;


import com.mysql.cj.x.protobuf.MysqlxPrepare;
import odu.edu.loadin.common.*;
import odu.edu.loadin.helpers.*;

import javax.swing.plaf.nimbus.State;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is  the actual implementation of the box service interface
 * This class contains one or more methods to manipulate box sizes in the database
 */
public class BoxSizeServiceImpl implements BoxService {


    public BoxSizeServiceImpl() {

    }

    /**
     * Returns back all box sizes from the database that are currently loaded into the BOX_SIZES table
     * @return  The collection of all box sizes
     */
    @Override

    public ArrayList<BoxSize> getBoxSizes()  {

        //we get a connection here

        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){ //this is called a try with resources and with java 1.8
                                                                                    //this will auto-close the connection
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM BOX_SIZE");

            //this is more of a transparent method.  person who is performing the query can decide how it gets mapped back to
            //individual objects
            ArrayList<BoxSize> results = StatementHelper.getResults(statement, (ResultSet rs) -> {
                BoxSize s = new BoxSize();
                s.setId(rs.getInt("ID"));
                s.setDescription(rs.getString("DESCRIPTION"));
                s.setDimensions(rs.getString("DIMENSIONS"));
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


        return new ArrayList<BoxSize>();
    }

    @Override
    public Response addBoxSize(BoxSize boxSize) {
        System.out.println("----invoking addBoxSize, Box name is: " + boxSize.getDescription());

        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){
            //first we have to find the previous id
            Integer lastId = StatementHelper.getResults(conn.prepareStatement("SELECT ID FROM BOX_SIZE ORDER BY ID DESC LIMIT 1"),
                    (ResultSet rs) -> {  return rs.getInt("ID"); }).stream().findFirst().orElse(0);

            boxSize.setId(lastId + 1);  //set the new id here

            String query = "INSERT INTO BOX_SIZE (ID, DESCRIPTION, DIMENSIONS, CREATED_AT, UPDATED_AT)"
                    +" VALUES (?, ?, ?, NOW(), NOW() )";

            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.setInt(1, boxSize.getId());
            insertStatement.setString(2, boxSize.getDescription());
            insertStatement.setString(3, boxSize.getDimensions());
            System.out.println(insertStatement);
            insertStatement.executeUpdate();

        }
        catch (SQLException ex){

        }

        return Response.ok(boxSize).build();
    }


}
