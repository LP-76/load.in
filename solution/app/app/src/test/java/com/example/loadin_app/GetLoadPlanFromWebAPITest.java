package com.example.loadin_app;

import com.example.loadin_app.data.services.LoadPlanBoxServiceImpl;

import org.junit.Assert;
import org.junit.Test;
import odu.edu.loadin.common.LoadPlanBox;
import java.util.*;

public class GetLoadPlanFromWebAPITest
{
    @Test
    public void TestGetLoadPlan()
    {
        String username = "john.smith@test.net";
        String password = "BlueMango@1";

        LoadPlanBoxServiceImpl service = new LoadPlanBoxServiceImpl(username, password);

        try
        {
            ArrayList<LoadPlanBox> loadPlanList = new ArrayList<LoadPlanBox>();
            loadPlanList.add(new LoadPlanBox(1,2f,2f,2f,1f,1f,1f,13f,13,"",19,19,1));

            service.addLoadPlan(1,loadPlanList);

            int result = service.getLoadPlan(1).size();

            Assert.assertTrue(result == 1);
        }
        catch(Exception e)
        {
            System.out.println(e);
        };
    }

    @Test
    public void TestAddLoadPlan()
    {
        String username = "john.smith@test.net";
        String password = "BlueMango@1";

        LoadPlanBoxServiceImpl service = new LoadPlanBoxServiceImpl(username, password);

        try
        {
            ArrayList<LoadPlanBox> loadPlanList = new ArrayList<LoadPlanBox>();
            loadPlanList.add(new LoadPlanBox(1,2f,2f,2f,1f,1f,1f,13f,13,"",19,19,1));

            List<LoadPlanBox> returnedList = service.addLoadPlan(1,loadPlanList);

            Assert.assertTrue(returnedList.size() == loadPlanList.size());
        }
        catch(Exception e)
        {
            System.out.println(e);
        };
    }

}
