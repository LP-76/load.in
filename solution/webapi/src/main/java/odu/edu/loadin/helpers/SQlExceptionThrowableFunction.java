package odu.edu.loadin.helpers;

import java.sql.SQLException;
import java.util.function.Function;

public interface SQlExceptionThrowableFunction<T, R> extends Function<T,R> {


    @Override
    default R apply(T t){
        R result = null;
        try{
            result = applyThrowable(t);

        }
        catch(SQLException ex){
            //TODO: global logging
            System.out.println(ex);
        }
        return  result;
    }

    R applyThrowable(T element) throws SQLException;
}
