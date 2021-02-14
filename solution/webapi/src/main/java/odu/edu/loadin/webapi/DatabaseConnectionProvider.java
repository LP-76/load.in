package odu.edu.loadin.webapi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Purpose of this class is to provide a single point of managing the connection
 * to the database from the webapi layer.  This class will be responsible for loading the connection
 * to any service that needs it.
 */
public  class DatabaseConnectionProvider {
    //TODO: change this to a configuration variable
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/loadin";

    /**
     * Default constructor.  As of right now, this is not used
     */
    public DatabaseConnectionProvider(){

    }

    /**
     * This method will attempt to load the connection to the load.in sql database
     * @return An active connection if this succeeds to which data can be pulled from
     */
    public static Connection getLoadInSqlConnection(){

        Connection conn = null;
        try{
            //TODO: fix this so that it's not hard coded
            //this attemps to establish the connection
            conn = DriverManager.getConnection(CONNECTION_URL, "root", "password");

        }catch(Exception ex){
            System.out.println(ex);

        }
        return conn;
    }

}
