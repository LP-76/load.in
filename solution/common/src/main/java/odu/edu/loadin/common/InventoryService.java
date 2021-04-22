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
package odu.edu.loadin.common;



import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/inventoryservice/")
public interface InventoryService
{

    @GET
    @Path("/inventory/{loginID}")
    @Produces( "application/json" )
    ArrayList<Inventory> getInventory(@PathParam("loginID") int loginID) throws SQLException;

    @POST
    @Path("/inventory/")
    public Response addInventory(Inventory inventory);

    @POST
    @Path("/inventory/addBulk")
    @Produces( "application/json" )
    public ArrayList<Inventory> addBulkInventory(ArrayList<Inventory> inventory);

    @POST
    @Path("/inventory/edit")
    public Response editInventory(Inventory inventory) throws SQLException;

    @POST
    @Path("/inventory/delete/{ID}")
    public void deleteItem(@PathParam("ID") int ID) throws SQLException;

    @POST
    @Path("/inventory/deleteAll/{USER_ID}")
    public void deleteAllItem(@PathParam("USER_ID") int USER_ID) throws SQLException;

    @POST
    @Path("/inventory/insertRandom/{USER_ID}/{numOfBoxes}")
    public void insertRandomItem(@PathParam("USER_ID") int USER_ID, @PathParam("numOfBoxes") int numOfBoxes) throws SQLException;

    @POST
    @Path("/inventory/resetStatus/{USER_ID}")
    public void setUserInventoryStatus(@PathParam("USER_ID") int USER_ID) throws SQLException;

}
