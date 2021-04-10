package odu.edu.loadin.common;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/expertservice/")
public interface ExpertArticleService {

    @GET
    @Path("/expertArticles/{keyword}")
    @Produces( "application/json" )
    ArrayList<ExpertArticle> getExpertArticles(@PathParam("keyword") String keyword) throws SQLException;

}
