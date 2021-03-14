package odu.edu.loadin.webapi;

import odu.edu.loadin.common.LoadPlanBox;
import odu.edu.loadin.common.LoadPlanBoxService;

import java.sql.SQLException;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import odu.edu.loadin.common.*;
import odu.edu.loadin.helpers.*;

public class LoadPlanBoxServiceImpl implements LoadPlanBoxService
{
    @Override
    public ArrayList<LoadPlanBox> getLoadPlan(int i) throws SQLException
    {
        //do things

        ArrayList<LoadPlanBox> toReturn = new ArrayList<LoadPlanBox>();
        toReturn.add(new LoadPlanBox(1,1f,1f,1f,1f,1f,1f, 1f, 1f, "",1,1));
        return toReturn;
    }

    @Override
    public ArrayList<LoadPlanBox> addLoadPlan(int i, ArrayList<LoadPlanBox> arrayList) throws SQLException
    {
        //do things
        return arrayList;
    }
}
