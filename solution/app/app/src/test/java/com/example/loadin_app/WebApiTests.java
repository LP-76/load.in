package com.example.loadin_app;


import com.example.loadin_app.data.TestClass;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;

import static org.junit.Assert.*;


public class WebApiTests {

    @Test
    public void TestBasicBoxSizeConnectivity() throws ExecutionException, InterruptedException {
        TestClass tc = new TestClass();

        List<BoxSize> boxeSizes = tc.TestSpace();

        Assert.assertTrue(boxeSizes.size() > 0);






    }

}
