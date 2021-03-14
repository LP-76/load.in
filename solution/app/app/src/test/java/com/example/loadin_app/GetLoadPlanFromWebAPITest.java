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
        LoadPlanBoxServiceImpl service = new LoadPlanBoxServiceImpl();

        try
        {
            List<LoadPlanBox> loadPlanList = service.getLoadPlan(1);
            Assert.assertTrue(loadPlanList.size() >= 1);
        }
        catch(Exception e)
        {
            System.out.println(e);
        };
    }

    @Test
    public void TestaddLoadPlan()
    {
        LoadPlanBoxServiceImpl service = new LoadPlanBoxServiceImpl();

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
