package odu.edu.loadin.common;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/feedbackservice/")
public interface FeedbackService {

    @GET
    @Path("/feedback/{userID}")
    @Produces( "application/json" )
    ArrayList<Feedback> getFeedback(@PathParam("userID") int userID) throws SQLException;

    @POST
    @Path("/feedback/")
    public Response addFeedback(Feedback feedback);
}
