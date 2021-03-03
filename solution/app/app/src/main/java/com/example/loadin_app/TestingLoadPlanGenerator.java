package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;
import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.World;

public class TestingLoadPlanGenerator
{



    public static LoadPlan GenerateBasicSampleLoadPlan(World theWorld)
    {
        //World sampleWorld = new World();
        Truck t = new Truck(theWorld);
        t.place(new Vector(2f*12f, 0f, 2f*12f));


        LoadPlan sampleLoadPlan = new LoadPlan(t);

        for(int widthIndex = 0 ; widthIndex < sampleLoadPlan.GetTruck().getWidthInches()/12 ; widthIndex++)
        {
            for(int lengthIndex = 0 ; lengthIndex < sampleLoadPlan.GetTruck().getWidthInches()/12 ; lengthIndex++)
            {
                for(int heightIndex = 0 ; heightIndex < sampleLoadPlan.GetTruck().getHeightInches()/12 ; heightIndex++)
                {
                    Box newBox = new Box(12,12,12,theWorld);
                    newBox.setDestination( t.getOffset().add(   new Vector(lengthIndex*12, widthIndex*12, heightIndex*12)));
                    sampleLoadPlan.AddBox(newBox);
                    newBox.setVisible(false);
                }
            }
        }

        return sampleLoadPlan;
    }
}
