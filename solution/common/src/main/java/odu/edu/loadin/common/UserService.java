package odu.edu.loadin.common;

import org.springframework.security.access.annotation.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users/")
public interface UserService {

    @GET
    @Path("/user/{id}")
    @Produces( "application/json" )
    @Secured({Roles.USER})
    User getUser(@PathParam("id") int id);

    @POST
    @Path("/user/")
    @Secured({Roles.ANONYMOUS})
    Response login(UserLoginRequest requestForLogin);

    @POST
    @Path("/user/add")
    @Secured({Roles.ANONYMOUS})
    public Response addUser(User user);



}
