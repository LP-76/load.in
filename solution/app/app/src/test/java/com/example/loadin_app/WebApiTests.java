package com.example.loadin_app;



import com.example.loadin_app.data.services.BoxServiceImpl;
import com.example.loadin_app.data.services.MovingTruckServiceImpl;
import com.example.loadin_app.data.services.UserServiceImpl;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import odu.edu.loadin.common.BoxSize;
import odu.edu.loadin.common.MovingTruck;
import odu.edu.loadin.common.User;

import static org.junit.Assert.*;


public class WebApiTests {

    private String username = "john.smith@test.net";
    private String password = "BlueMango@1";

    @Test
    public void TestBasicBoxSizeConnectivity() throws ExecutionException, InterruptedException, SQLException {


        BoxServiceImpl tc = new BoxServiceImpl(username, password);

        List<BoxSize> boxeSizes = tc.getBoxSizes();

        Assert.assertTrue(boxeSizes.size() > 0);

    }
    @Test
    public void TestBasicTruckSizes() throws ExecutionException, InterruptedException, SQLException {


        MovingTruckServiceImpl tc = new MovingTruckServiceImpl(username, password);

        List<MovingTruck> movingTruck = tc.getTrucks();

        Assert.assertTrue(movingTruck.size() > 0);

    }
    @Test
    public void TestAddBoxSize() throws ExecutionException, InterruptedException {
        BoxServiceImpl tc = new BoxServiceImpl(username, password);

        BoxSize test = new BoxSize();
        test.setDescription("Test Box Size");
        test.setDimensions("15x34x6");

        BoxSize result = tc.addBoxSize(test);

        Assert.assertEquals(result.getDescription(), test.getDescription());
        Assert.assertEquals(result.getDimensions(), test.getDimensions());

    }

    @Test
    public void TestLogin() throws IOException {

        UserServiceImpl sc = new UserServiceImpl();
        User profile = null;

       UserServiceImpl.LoginResult result =  sc.login("john.smith@test.net", "BlueMango@1" );

       Assert.assertTrue(result.status == UserServiceImpl.UserLoginStatus.Ok);


    }

}
