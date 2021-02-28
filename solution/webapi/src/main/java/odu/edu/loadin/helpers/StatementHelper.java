package odu.edu.loadin.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The statement helper class contains one or more simple helper functions to cut down on redundant code
 */
public class StatementHelper {

    /**
     * This method will map rows from the execution of a prepared statement into an arraylist of a certain object type
     * @param statement The prepared statement that is ready for execution
     * @param mapping The lambda function which takes the result set for each row and maps an object to the row
     * @param <T> The type of object that the helper function expects to read
     * @return  The collection of the object mirroring the expected rows
     * @throws SQLException  Possible exception that can occur on runtime
     */
    public static <T> ArrayList<T> getResults(PreparedStatement statement,  SQlExceptionThrowableFunction<ResultSet, T> mapping ) throws SQLException {
        ArrayList<T> results2 = new ArrayList<T>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            T b = mapping.apply(rs);
            results2.add(b);
        }
        return results2;
    }


}
