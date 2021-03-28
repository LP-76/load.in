package odu.edu.loadin.webapi;
import odu.edu.loadin.common.Roles;
import odu.edu.loadin.common.User;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//public class LoadInUserPrincipal implements UserDetails {
    public class LoadInUserPrincipal implements Principal {
    private  User user;

    public static final String LOAD_IN_PRINCIPAL = "LOAD_IN_PRINCIPAL";



    public LoadInUserPrincipal(User user){
        this.user = user;
    }


    public Collection<String > getRoles() {
        //TODO: if we ever wanted to get further, we could figure out what these users were actually
        //supposed to access
        //final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));
        final List<String> roles = new ArrayList<String>();

        if(user != null){
            roles.add(Roles.USER);
        }else{
            roles.add(Roles.ANONYMOUS);
        }

        //for now everyone is a user
        return roles;
    }
//    public Collection<? extends GrantedAuthority > getAuthorities() {
//        //TODO: if we ever wanted to get further, we could figure out what these users were actually
//        //supposed to access
//        //final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));
//        final List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
//
//        if(user != null){
//            roles.add(new SimpleGrantedAuthority(Roles.USER));
//        }else{
//            roles.add(new SimpleGrantedAuthority(Roles.ANONYMOUS));
//        }
//
//        //for now everyone is a user
//        return roles;
//    }


    public String getPassword() {
        if(user == null)
            return "";
        return user.getPassword();
    }


    public String getUsername() {
        if(user == null)
            return "ANONYMOUS";
        return user.getEmail();
    }


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return user != null ? user.getEmail() : "ANONYMOUS";
    }
}
