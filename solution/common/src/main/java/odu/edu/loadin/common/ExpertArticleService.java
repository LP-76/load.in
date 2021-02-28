package odu.edu.loadin.common;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/expertarticle/")
public interface ExpertArticleService {

    @GET
    @Path("/articles/{keyword}")
    @Produces("application/json")
    ExpertArticle getExpertArticle(@PathParam("keyword") String keyword);

}
