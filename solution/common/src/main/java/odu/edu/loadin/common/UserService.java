package odu.edu.loadin.common;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/users/")
public interface UserService {

    @GET
    @Path("/user/{id}")
    @Produces( "application/json" )
    User getUser(@PathParam("id") int id);

    @POST
    @Path("/user/")
    Response login(UserLoginRequest requestForLogin);



}
