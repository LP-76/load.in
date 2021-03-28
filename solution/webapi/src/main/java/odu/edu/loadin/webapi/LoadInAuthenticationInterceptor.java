package odu.edu.loadin.webapi;

import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxrs.interceptor.JAXRSInInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.addressing.v200408.EndpointReferenceType;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class LoadInAuthenticationInterceptor extends JAXRSInInterceptor {
    @Override
    public void handleMessage(Message message) {


        String authorization = getAuthorizationHeader(message);
//
        if (isRequestAnonymous(authorization)) {
            //requestContext.abortWith(createFaultResponse());
            //we want to establish a anonymous user principal
            LoadInUserPrincipal anon = new LoadInUserPrincipal(null);  //no user
            establishPrincipal(anon, message);
            return;
        }

        AuthenticationRequestModel model = getRequestModel(authorization);

        if(model == null){
            //there was an issue with reading the credentials
            LoadInUserPrincipal anon = new LoadInUserPrincipal(null);  //no user
            establishPrincipal(anon, message);
            return; //return anonymous
        }

        //we now need to establish the user principal
        LoadInUserDetailService service = new LoadInUserDetailService();
        LoadInUserPrincipal principal = (LoadInUserPrincipal) service.loadUserByUsername(model.username);



        if (principal.getPassword().equals(model.password)) {
            // let request to continue
            establishPrincipal(principal, message);
        } else {
            // authentication failed, request the authetication, add the realm name if needed to the value of WWW-Authenticate
            //requestContext.abortWith(Response.status(401).header("WWW-Authenticate", "Basic").build());
            sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);
        }


    }

    private String getAuthorizationHeader(Message message){
        Map<String, Object> headers = (Map<String, Object>) message.get(Message.PROTOCOL_HEADERS);
        String authorization = "";
        if(headers.containsKey("Authorization")){
            authorization = ((ArrayList<String>)headers.get("Authorization")).stream().findFirst().orElse("");

        }
        return authorization;
    }

    private void sendErrorResponse(Message message, int responseCode) {
        Message outMessage = getOutMessage(message);
        outMessage.put(Message.RESPONSE_CODE, responseCode);
        // Set the response headers
        Map responseHeaders = (Map) message.get(Message.PROTOCOL_HEADERS);
        if (responseHeaders != null) {
            responseHeaders.put("WWW-Authenticate", Arrays.asList(new String[]{"Basic realm=realm"}));
            responseHeaders.put("Content-length", Arrays.asList(new String[]{"0"}));
        }
        message.getInterceptorChain().abort();
        try {
            getConduit(message).prepare(outMessage);
            close(outMessage);
        } catch (IOException e) {
         System.out.println(e);
        }
    }

    private Message getOutMessage(Message inMessage) {
        Exchange exchange = inMessage.getExchange();
        Message outMessage = exchange.getOutMessage();
        if (outMessage == null) {
            Endpoint endpoint = exchange.get(Endpoint.class);
            outMessage = endpoint.getBinding().createMessage();
            exchange.setOutMessage(outMessage);
        }
        outMessage.putAll(inMessage);
        return outMessage;
    }
    private Conduit getConduit(Message inMessage) throws IOException {
        Exchange exchange = inMessage.getExchange();
        EndpointReferenceType target = exchange.get(EndpointReferenceType.class);
        //Conduit conduit = exchange.getDestination().getBackChannel(inMessage, null, target);
        Conduit conduit = exchange.getDestination().getBackChannel(inMessage);
        exchange.setConduit(conduit);
        return conduit;
    }

    private void establishPrincipal(LoadInUserPrincipal principal, Message message){
        //TODO: finish
        LoadInSecurityContext secContext = new LoadInSecurityContext(principal);
       message.getExchange().put(LoadInUserPrincipal.LOAD_IN_PRINCIPAL, secContext); //put the context for consumption
    }

    private AuthenticationRequestModel getRequestModel(String authorization){
        String[] parts = authorization.split(" ");
        String decodedValue = null;
        try {
            decodedValue = new String(Base64Utility.decode(parts[1]));
        } catch (Base64Exception ex) {

            return null;
        }
        String[] namePassword = decodedValue.split(":");
        AuthenticationRequestModel model = new AuthenticationRequestModel();
        model.username = namePassword[0];
        model.password = namePassword[1];
        return model;
    }

    private boolean isRequestAnonymous(String authorization){
        String[] parts = authorization.split(" ");
        return parts.length != 2 || !"Basic".equals(parts[0]);  //we only understand basic auth
    }

//    private Response createFaultResponse() {
//        return Response.status(401).header("WWW-Authenticate", "Basic realm=\"service.com\"").build();
//    }

    private void close(Message outMessage) throws IOException {
        OutputStream os = outMessage.getContent(OutputStream.class);
        os.flush();
        os.close();
    }

    private class AuthenticationRequestModel{
        String username;
        String password;
    }
}
