package odu.edu.loadin.webapi;


import odu.edu.loadin.common.User;
import odu.edu.loadin.helpers.StatementHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Provider
public class LoadInUserDetailService {


    public Principal loadUserByUsername(String username) throws UsernameNotFoundException {
        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){ //this is called a try with resources and with java 1.8
            //this will auto-close the connection
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER where EMAIL = ?");
            statement.setString(1, username);

            //this is more of a transparent method.  person who is performing the query can decide how it gets mapped back to
            //individual objects
            User results = StatementHelper.getResults(statement, (ResultSet rs) -> {
                User u =  mapStandardUserResult(rs);
                u.setPassword(rs.getString("PASSWORD"));
                return u;
            }).stream().findFirst().orElse(null);

            //confirm that the password is correct

            //TODO: implement hashing and salting here!

            return new LoadInUserPrincipal(results);

        }
        catch (SQLException ex){
            //TODO: exception logging
            System.out.println(ex);
        }
      throw new UsernameNotFoundException(username);
    }

    private User mapStandardUserResult(ResultSet rs) throws SQLException {
        User r = new User();
        r.setId(rs.getInt("ID"));
        r.setFirstName(rs.getString("FIRST_NAME"));
        r.setLastName(rs.getString("LAST_NAME"));
        r.setCreatedAt(rs.getDate("CREATED_AT"));
        r.setUpdatedAt(rs.getDate("UPDATED_AT"));
        r.setEmail(rs.getString("EMAIL"));
        r.setPhoneNumber(rs.getString("PHONE_NUMBER"));

        return r;
    }
}
