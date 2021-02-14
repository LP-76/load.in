package odu.edu.loadin.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Clippy {
    public static <T extends IMapResultSet> ArrayList<T> getResults(PreparedStatement statement,  Supplier<T> factory) throws SQLException {
        ArrayList<T> results = new ArrayList<T>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            T b = factory.get();
            b.map(rs);
            results.add(b);
        }
        return results;
    }

    public static <T> ArrayList<T> getMappedResults(PreparedStatement statement,  Function<ResultSet, T> mapping) throws SQLException {
        ArrayList<T> results2 = new ArrayList<T>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            T b = mapping.apply(rs);
            results2.add(b);
        }
        return results2;
    }


}
