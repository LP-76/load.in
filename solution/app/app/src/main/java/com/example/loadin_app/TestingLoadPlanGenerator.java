package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;
import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.World;

public class TestingLoadPlanGenerator
{
    public LoadPlan GenerateBasicSampleLoadPlan()
    {
        World sampleWorld = new World();
        LoadPlan sampleLoadPlan = new LoadPlan(new Truck(sampleWorld));

        for(int widthIndex = 0 ; widthIndex < sampleLoadPlan.GetTruck().getWidthInches()/12 ; widthIndex++)
        {
            for(int lengthIndex = 0 ; lengthIndex < sampleLoadPlan.GetTruck().getWidthInches()/12 ; lengthIndex++)
            {
                for(int heightIndex = 0 ; heightIndex < sampleLoadPlan.GetTruck().getHeightInches()/12 ; heightIndex++)
                {
                    Box newBox = new Box(12,12,12,sampleWorld);
                    newBox.setDestination(new Vector(lengthIndex*12, widthIndex*12, heightIndex*12));
                    sampleLoadPlan.AddBox(newBox);
                }
            }
        }

        return sampleLoadPlan;
    }
}
