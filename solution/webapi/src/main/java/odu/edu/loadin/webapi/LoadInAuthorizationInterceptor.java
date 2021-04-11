package odu.edu.loadin.webapi;


import odu.edu.loadin.common.Roles;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxrs.interceptor.JAXRSInInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.addressing.v200408.EndpointReferenceType;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadInAuthorizationInterceptor extends JAXRSInInterceptor {
    @Override
    public void handleMessage(Message message) {


        LoadInSecurityContext context = (LoadInSecurityContext) message.getExchange().get(LoadInUserPrincipal.LOAD_IN_PRINCIPAL);

        String url = getRequestURL(message);

        LoadInUserPrincipal principal = (LoadInUserPrincipal) context.getUserPrincipal();
        boolean anonymous = context.isUserInRole(Roles.ANONYMOUS);
        boolean bypassSecurity = anonymous && allowedAnonymous(url);

        if(!bypassSecurity && anonymous){
            sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);

        }



    }

    private boolean allowedAnonymous(String requestUrl){
        String pattern = "(.*\\/users\\/user\\/add\\/?)|(.*\\/users\\/user\\/?)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(requestUrl);
        return m.matches();
    }

    private String getRequestURL(Message message){
        String requestUrl = (String)message.get(Message.REQUEST_URL);
        return requestUrl;
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
