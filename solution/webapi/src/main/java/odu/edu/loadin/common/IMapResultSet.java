package odu.edu.loadin.common;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IMapResultSet {
    void map(ResultSet rs) throws SQLException;
}
