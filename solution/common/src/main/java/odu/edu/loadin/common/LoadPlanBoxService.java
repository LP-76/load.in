package odu.edu.loadin.common;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/loadplan/")
public interface LoadPlanBoxService
{
    @Produces( "application/json" )
    @GET
    @Path("/{loginID}")
    public ArrayList<LoadPlanBox> getLoadPlan(@PathParam("loginID") int loginId)throws SQLException;

    @POST
    @Path("/{loginID}")
    public ArrayList<LoadPlanBox> addLoadPlan(@PathParam("loginID") int loginId, ArrayList<LoadPlanBox> input) throws SQLException;
}
