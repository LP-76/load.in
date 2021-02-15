package com.example.loadin_app;



import com.example.loadin_app.data.services.BoxServiceImpl;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;

import static org.junit.Assert.*;


public class WebApiTests {

    @Test
    public void TestBasicBoxSizeConnectivity() throws ExecutionException, InterruptedException, SQLException {
        BoxServiceImpl tc = new BoxServiceImpl();

        List<BoxSize> boxeSizes = tc.getBoxSizes();

        Assert.assertTrue(boxeSizes.size() > 0);

    }

    @Test
    public void TestAddBoxSize() throws ExecutionException, InterruptedException {
        BoxServiceImpl tc = new BoxServiceImpl();

        BoxSize test = new BoxSize();
        test.setDescription("Test Box Size");
        test.setDimensions("15x34x6");

        BoxSize result = tc.addBoxSize(test);

        Assert.assertEquals(result.getDescription(), test.getDescription());
        Assert.assertEquals(result.getDimensions(), test.getDimensions());

    }

}
