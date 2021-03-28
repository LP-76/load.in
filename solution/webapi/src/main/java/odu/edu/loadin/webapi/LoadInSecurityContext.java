package odu.edu.loadin.webapi;



import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class LoadInSecurityContext implements SecurityContext {
    private  LoadInUserPrincipal currentUser;

    public LoadInSecurityContext(LoadInUserPrincipal principal){
        currentUser = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return currentUser;
    }

    @Override
    public boolean isUserInRole(String role) {
        return currentUser.getRoles().contains(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "BASIC";
    }
}
