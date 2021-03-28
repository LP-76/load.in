//package odu.edu.loadin.webapi;
//
//
//import org.apache.cxf.common.util.Base64Exception;
//import org.apache.cxf.common.util.Base64Utility;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.PreMatching;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
///*
//The main objective here is to determine if we have a user, who that user is and whether or not they are
//anonymous
// */
//@Provider
//
//public class AuthenticationHandler implements ContainerRequestFilter {
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        String authorization = requestContext.getHeaderString("Authorization");
//
//        if (isRequestAnonymous(authorization)) {
//            //requestContext.abortWith(createFaultResponse());
//            //we want to establish a anonymous user principal
//            LoadInUserPrincipal anon = new LoadInUserPrincipal(null);  //no user
//            establishPrincipal(anon, requestContext);
//            return;
//        }
//
//        AuthenticationRequestModel model = getRequestModel(authorization);
//
//        if(model == null){
//            //there was an issue with reading the credentials
//            LoadInUserPrincipal anon = new LoadInUserPrincipal(null);  //no user
//            establishPrincipal(anon, requestContext);
//            return; //return anonymous
//        }
//
//        //we now need to establish the user principal
//        LoadInUserDetailService service = new LoadInUserDetailService();
//        LoadInUserPrincipal principal = (LoadInUserPrincipal) service.loadUserByUsername(model.username);
//
//
//
//        if (principal.getPassword() == model.password) {
//            // let request to continue
//            establishPrincipal(principal, requestContext);
//        } else {
//            // authentication failed, request the authetication, add the realm name if needed to the value of WWW-Authenticate
//            requestContext.abortWith(Response.status(401).header("WWW-Authenticate", "Basic").build());
//        }
//    }
//
//    private void establishPrincipal(LoadInUserPrincipal principal, ContainerRequestContext context){
//        LoadInSecurityContext secContext = new LoadInSecurityContext(principal);
//        context.setSecurityContext(secContext);
//    }
//
//    private AuthenticationRequestModel getRequestModel(String authorization){
//        String[] parts = authorization.split(" ");
//        String decodedValue = null;
//        try {
//            decodedValue = new String(Base64Utility.decode(parts[1]));
//        } catch (Base64Exception ex) {
//
//            return null;
//        }
//        String[] namePassword = decodedValue.split(":");
//        AuthenticationRequestModel model = new AuthenticationRequestModel();
//        model.username = namePassword[0];
//        model.password = namePassword[1];
//        return model;
//    }
//
//    private boolean isRequestAnonymous(String authorization){
//        String[] parts = authorization.split(" ");
//        return parts.length != 2 || !"Basic".equals(parts[0]);  //we only understand basic auth
//    }
//
//    private Response createFaultResponse() {
//        return Response.status(401).header("WWW-Authenticate", "Basic realm=\"service.com\"").build();
//    }
//
//    private class AuthenticationRequestModel{
//        String username;
//        String password;
//    }
//}
