package odu.edu.loadin.webapi;

import java.sql.Connection;
import java.sql.DriverManager;

public  class DatabaseConnectionProvider {
    //TODO: change this to a configuration variable
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/loadin";
    public DatabaseConnectionProvider(){

    }

    public static Connection getLoadInSqlConnection(){
        //load a driver

        Connection conn = null;
        try{
            //TODO: fix this so that it's not hard coded
            conn = DriverManager.getConnection(CONNECTION_URL, "root", "password");

        }catch(Exception ex){
            System.out.println(ex);

        }
        return conn;
    }

}
